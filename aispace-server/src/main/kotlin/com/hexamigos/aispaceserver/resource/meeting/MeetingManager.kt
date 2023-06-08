package com.hexamigos.aispaceserver.resource.meeting

import com.hexamigos.aispaceserver.action.meeting.MeetingDetails
import com.hexamigos.aispaceserver.resource.ResourceManager
import com.hexamigos.aispaceserver.resource.ResourceManagerType
import com.hexamigos.aispaceserver.util.name
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class MeetingManager : ResourceManager<String, MeetingDetails> {
    companion object {
        @JvmStatic
        val meetings = HashMap<String, MeetingDetails>()
    }

    @PostConstruct
    fun build() {
        MeetingDetails(title = "Seylan Cube Standup", transcription = """
            Project Manager: Mamun can I have your updates please?
            Mamun: Hi Ranjan, Last day I worked on Bug ID 2469 “subscribers table count needs to be added to the count confirmation list ”. I completed the task. Then I had a code review with my supervisor and he suggested some changes. Today I am working on them. Once I am done I will move to the next task assigned by my supervisor.
            Project Manager: Do you have any blockers or something? Do you need any discussion sessions?
            Mamun: Thanks. I already had a discussion with my supervisor and if I need any clarification I will go for another discussion with him.
            Project Manager: Do you think you can complete it today?
            Mamun: Yes. I can do it today. Once it’s done then I will go for another code review.
            Project Manager: Ok thanks Mamun.
            Mamun: Thanks Ranjan
            Project Manager: Abid needs to work on Cube CustomerId generation and Do the Release
            
        """.trimIndent().trim()).let { meetings[it.id] = it }
        MeetingDetails(title = "Idea Mart Standup", transcription = """
            `
            PM: Hi, Waleed. Can you give your update, please?
            Me: Good Morning. Yesterday I started working on the CAS module. but after merging the mobitel and ideamart branches there are some errors. I checked those errors in ideamart and mobitel branches. same errors are existing in those two branches also.
            today I will continue troubleshooting those errors and after that I will start development. 
            PM: Ok. no worries
            
        """.trimIndent().trim()).let { meetings[it.id] = it }
        MeetingDetails(title = "Digital Office Standup", transcription = """
            
            Project Manager: Hi Ahad. Let's have your update.
            Ahad: Hello Ranjan, Good Morning. Last day i was working on Soap Client ( MO Response Menu ) and Soap Server ( MO User Input ). Last day I was on 80% both task. Vimal give me idea how to approach and after that i have complete that. Now I am working on USSD Flow integration.
            Project Manager: You mean you have complete 100% on Soap Client and server.
            Ahad: Yes.
            Project Manager: Ok. Thanks ahad. If you find any problem please try to get help from vimal and let me know.
            Ahad. Ok. Thanks.
            
        """.trimIndent().trim()).let { meetings[it.id] = it }
    }

    override fun add(resource: MeetingDetails): String {
        meetings.putIfAbsent(resource.id, resource)
        return resource.id
    }

    override fun getBy(key: String): MeetingDetails? {
        return meetings[key]
    }

    override fun getAll(): MutableCollection<MeetingDetails> {
        return meetings.values
    }

    override fun update(resource: MeetingDetails): MeetingDetails? {
        meetings[resource.id] = resource
        return meetings[resource.id]
    }

    override fun deleteBy(key: String): Boolean {
        return meetings.remove(key) != null
    }

    override fun deleteAll(): Int {
        val count = meetings.size
        meetings.clear()
        return count;
    }

    override fun getResourceType() = ResourceManagerType.MEETING
    override fun toString() = this.name()

}