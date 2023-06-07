package com.hexamigos.aispaceserver.action.task

import com.google.gson.Gson
import com.hexamigos.aispaceserver.action.*
import com.hexamigos.aispaceserver.action.OperationType.*
import com.hexamigos.aispaceserver.integration.ai.llm.LLMClient
import com.hexamigos.aispaceserver.resource.ResourceCenter
import com.hexamigos.aispaceserver.resource.ResourceManagerType
import com.hexamigos.aispaceserver.resource.task.TaskManager
import com.hexamigos.aispaceserver.util.toJson
import com.hexamigos.aispaceserver.util.toTaskDetail
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.UUID
import javax.annotation.PostConstruct

@Component
class TaskAction(val resourceCenter: ResourceCenter,
                 llmClient: LLMClient,
                 prompt: String = "") : Action<Task>(prompt, llmClient) {
    private val logger = LoggerFactory.getLogger(TaskAction::class.java)
    private val basePrompt = """
            You are a task manager. You can query, create, update, and delete tasks. When replaying for create, update, and delete operations on tasks you will respond in the JSON format provided here. Add ``` at starting and ending of the JSON
            ```{
            actionType: TASK ,
            operationType: OPERATION_TYPE,
            id: "@id",
            title: "@title",
            description: "@description",
            assignedTo: [@assigned],
            state: STATUS_TYPE
            }
            ```
            `OPERATION_TYPE` can be CREATE, UPDATE, DELETE
            `STATUS_TYPE` can be COMPLETED, PENDING, ONGOING
            @id, @title, @description, @assigned are placeholder that needs to be replaced with the proper value from @task. If the there's no value in placeholder then keep empty types.
            When replaying for query on tasks you will respond in the table format in markdown with header No, Title, State, AssignedTo
            
            ---
            output: 
            ```{response_json}```
            
            ---
            Here are the @task/@task list you have:
            
        """.trimIndent().trim()

    @PostConstruct
    override fun init() {
        prompt = basePrompt;
    }

    override fun process(input: String, maintainHistory: Boolean): ActionChain<Processed<String, String>> {
        recompilePrompt()
        val chain = super.process(input, true)
        if (chain.hasNext()) {
            val response = chain.content
            if (response.processed.isEmpty()) {
                val processed = Processed(response.original, response.original)
                return ActionChain(ChainState.ABORT, processed)
            }
            return chain
        }
        return chain
    }

    override fun recompilePrompt() {
        val taskManager = resourceCenter.getResourceByType(ResourceManagerType.TASK) as TaskManager
        val allTasks = taskManager.getAll()
        prompt = basePrompt + "\n" + allTasks.toJson()
    }

    override fun getActionType() = ActionType.TASK
    override fun transform(chain: ActionChain<Processed<String, String>>): ActionChain<Transformed<Any, Task>> {
        val content = chain.content.processed
        val gson = Gson()
        val transform = gson.fromJson(content, Task::class.java)
        val transformed = Transformed<Any, Task>(content, transform)
        return ActionChain(ChainState.NEXT, transformed)
    }

    override fun execute(chain: ActionChain<Transformed<Any, Task>>): ActionChain<Executed<Any, Any>> {
        logger.info("Executing task operation")
        val task = chain.content.processed
        val taskManager = resourceCenter.getResourceByType(ResourceManagerType.TASK) as TaskManager
        try {
            val result = when (task.operationType) {
                CREATE -> {
                    task.id = UUID.randomUUID().toString()
                    taskManager.add(task.toTaskDetail())
                    "Task created with id [${task.id}]"
                }

                UPDATE -> {
                    taskManager.update(task.toTaskDetail())
                    "Task updated with id [${task.id}]"
                }

                DELETE -> {
                    task.id?.let {
                        taskManager.deleteBy(it)
                        "Task deleted with id [${task.id}]"
                    } ?: "Invalid id to deleted [${task.id}]"
                }

                else -> {
                    logger.warn("No valid operation to perform [operation: {}]", task.operationType)
                    "Invalid Operation"
                }
            }
            logger.info("Updated Task List: {}", taskManager.getAll())
            return ActionChain(ChainState.FINISHED, Executed(task, result))
        } catch (e: Exception) {
            return ActionChain(ChainState.ERROR, Executed(task, e.message
                    ?: "Ops! Something went wrong when returning error message"))
        }

    }

}