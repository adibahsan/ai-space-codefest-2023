package com.hexamigos.aispaceserver.resource.task

import com.hexamigos.aispaceserver.action.task.Task
import com.hexamigos.aispaceserver.action.task.TaskDetail
import com.hexamigos.aispaceserver.action.task.TaskState
import com.hexamigos.aispaceserver.resource.ResourceManager
import com.hexamigos.aispaceserver.resource.ResourceManagerType
import com.hexamigos.aispaceserver.util.name
import org.springframework.stereotype.Component
import java.util.UUID
import javax.annotation.PostConstruct

@Component
class TaskManager : ResourceManager<String, TaskDetail> {
    companion object {
        @JvmStatic
        val tasks = HashMap<String, TaskDetail>()
    }

    @PostConstruct
    fun build() {
        TaskDetail(title = "Complete HR Documentation", description = "Review and fill out all necessary HR documents, including employment contracts, tax forms, and benefit enrollment forms.", state = TaskState.PENDING).let { tasks[it.id] = it }

        TaskDetail(title = "Set Up Payroll and Banking", description = "Provide payroll and banking details to the HR or finance department to ensure timely salary payments and direct deposit setup.", state = TaskState.PENDING).let { tasks[it.id] = it }

        TaskDetail(title = "Obtain Security Access", description = "Request and obtain security access cards or keys required to enter the office premises or specific areas within the company.", state = TaskState.PENDING).let { tasks[it.id] = it }

        TaskDetail(title = "Acquire Necessary Equipment", description = "Coordinate with the IT department to obtain and set up the required equipment, such as a laptop, phone, and any additional tools.", state = TaskState.PENDING).let { tasks[it.id] = it }

        TaskDetail(title = "Familiarize with Company Policies", description = "Read and understand the company policies, including the employee handbook, code of conduct, and any specific policies related to your role.", state = TaskState.PENDING).let { tasks[it.id] = it }

        TaskDetail(title = "Attend Orientation Sessions", description = "Participate in orientation sessions to learn about the company's history, mission, values, organizational structure, and key departments.", state = TaskState.PENDING).let { tasks[it.id] = it }

        TaskDetail(title = "Meet with HR Representative", description = "Schedule a meeting with an HR representative to address any questions or concerns, discuss company benefits, and get acquainted with HR processes.", state = TaskState.PENDING).let { tasks[it.id] = it }

        TaskDetail(title = "Meet with Manager and Team Members", description = "Arrange introductory meetings with your manager and team members to establish relationships, understand expectations, and discuss upcoming projects.", state = TaskState.PENDING).let { tasks[it.id] = it }

        TaskDetail(title = "Review Job Responsibilities", description = "Go through the job description and expectations for your role, clarify any uncertainties, and align your understanding with your manager.", state = TaskState.PENDING).let { tasks[it.id] = it }

        TaskDetail(title = "Understand Reporting and Communication Channels", description = "Identify the reporting structure within the company, including the direct supervisor and any key stakeholders, to ensure clear communication channels.", state = TaskState.PENDING).let { tasks[it.id] = it }

        TaskDetail(title = "Learn Internal Tools and Systems", description = "", state = TaskState.PENDING).let { tasks[it.id] = it }

        TaskDetail(title = "Set Goals and Objectives", description = "Collaborate with your manager to establish short-term and long-term goals that align with your role and the company's objectives.", state = TaskState.PENDING).let { tasks[it.id] = it }

        TaskDetail(title = "Attend Training Programs", description = "Participate in any training programs or workshops designed to enhance your skills, knowledge, and understanding of the company's processes.", state = TaskState.PENDING).let { tasks[it.id] = it }

        TaskDetail(title = "Explore Company Culture", description = "Engage in company culture initiatives, such as attending team-building activities, joining employee resource groups, or participating in social events.", state = TaskState.PENDING).let { tasks[it.id] = it }
    }

    override fun add(resource: TaskDetail): String {
        tasks.putIfAbsent(resource.id, resource)
        return resource.id
    }

    override fun getBy(key: String): TaskDetail? {
        return tasks[key]
    }

    override fun getAll(): MutableCollection<TaskDetail> {
        return tasks.values
    }

    override fun update(resource: TaskDetail): TaskDetail? {
        tasks[resource.id] = resource
        return tasks[resource.id]
    }

    override fun deleteBy(key: String): Boolean {
        return tasks.remove(key) != null
    }

    override fun deleteAll(): Int {
        val count = tasks.size
        tasks.clear()
        return count;
    }

    override fun getResourceType() = ResourceManagerType.TASK

    override fun toString() = this.name()

}