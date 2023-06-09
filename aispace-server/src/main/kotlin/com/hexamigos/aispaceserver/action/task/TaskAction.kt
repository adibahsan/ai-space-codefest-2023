package com.hexamigos.aispaceserver.action.task

import com.google.gson.Gson
import com.hexamigos.aispaceserver.action.*
import com.hexamigos.aispaceserver.action.OperationType.*
import com.hexamigos.aispaceserver.integration.ai.llm.LLMClient
import com.hexamigos.aispaceserver.resource.ResourceCenter
import com.hexamigos.aispaceserver.resource.ResourceManagerType
import com.hexamigos.aispaceserver.resource.task.TaskManager
import com.hexamigos.aispaceserver.util.name
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
            You are a task manager. You can query, create, update, and delete tasks. 
            Response for create, update and delete operations always will start with ``` and end with ```. 
            When replaying for create, update and delete operations respond in the JSON format provided here.
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
            @id, @title, @description, @state, @assigned are placeholder that needs to be replaced with the proper value from @task. If the there's no value in placeholder then keep empty types.
            User can query using id, title, description, assigned, state available here in @task section.
            When replaying for query on tasks you will respond in the table format in markdown with header No, Title, State
            When asked to show tasks or task or task list, take the information from @task section here and replay. If your unsure about that to do replay by 'Sorry. I don't know how proceed with this.' and suggest to contact with @supervisor
            When asked to next task replay with a pending task from @task section in a normal text format with title, description, state. If there's no pending task available then replay with 'It seems there's no pending task. You can check without @supervisor for new task'.
            When user asked for how to do/complete the task you can check the description and give a answer based on that if no description is available then replay 'Sorry. I don't know how help with this.' and suggest to contact with @supervisor
            ---
            output: 
            ```{response_json}```
            
            ---
            contacts:
            @supervisor : Name : Sampath, email: sampath@gmail.com
            @hr : Name: HR, email: hr@gmail.com
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
        prompt = "$basePrompt\n${allTasks.toJson()}"
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

    override fun toString() = this.name()


}