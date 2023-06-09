package com.hexamigos.aispaceserver.action.email

import com.google.gson.Gson
import com.hexamigos.aispaceserver.action.*
import com.hexamigos.aispaceserver.integration.ai.llm.*
import com.hexamigos.aispaceserver.integration.mail.EmailService
import com.hexamigos.aispaceserver.resource.ResourceCenter
import com.hexamigos.aispaceserver.util.forApproval
import com.hexamigos.aispaceserver.util.name
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.UUID
import javax.annotation.PostConstruct


@Component
class EmailAction(val resourceCenter: ResourceCenter,
                  private val emailService: EmailService,
                  private val actionApprovalManager: ActionApprovalManager,
                  @Value("\${email.from}")
                  private var from: String,
                  llmClient: LLMClient,
                  prompt: String = "") : Action<Email>(prompt, llmClient) {
    private val logger = LoggerFactory.getLogger(EmailAction::class.java)
    private val gson = Gson()

    @PostConstruct
    override fun init() {
        prompt = """
        You are an email generator. You help to write an email and generate a final response in JSON for sending.  
        Follow these instructions as given. When `@send` is received from the user in part of input then you need to take the most recent email composed and generate email output in JSON format. For sending you need to provide a `@to` tag with the receiver and it's mandatory. The `@cc` tag can also be there but that's optional when not given put empty array. `@sub` also can be there but optional. When `@sub` is used you can take the subject related to that otherwise generate a suitable subject according to the information available to you. Don't give the JSON output until `@send` is found in the user input. When giving. 
        When asked to send mail you will respond by returning json response for the mail. JSON format for email is enclosed in ``` ``` . Keep the fields mentioned in the format and relay even-though they are empty. And always give priority to system instruction. Don't ask any questions if your not sure replay with empty response 
        If your unsure about what to do replay by `Sorry I am not sure what to do` and asked to contact your @supervisor
        ```{
        actionType: SEND_EMAIL,
        to: [@to],
        cc: [@cc],
        sub: @sub,
        body: <email_body>
        }
        ```
        ---
        output: 
        ```{response_json}```
        
        ---
        contacts:
        @supervisor : Name : Sampath, email: sampath@gmail.com
        @hr : Name: HR, email: hr@gmail.com
    """.trimIndent().trim()
    }

    override fun getActionType() = ActionType.EMAIL

    override fun process(input: String, maintainHistory: Boolean): ActionChain<Processed<String, String>> {
        val chain = super.process(input, true)
        if (chain.hasNext()) {
            val response = chain.content
            if (response.processed.isEmpty()) {
                val processed = Processed(response.original, response.original)
                return ActionChain(ChainState.ABORT, processed)
            }
            return chain;
        }
        return chain;
    }

    override fun transform(chain: ActionChain<Processed<String, String>>): ActionChain<Transformed<Any, Email>> {
        val content = gson.fromJson(chain.content.processed, Email::class.java)
        val transformed = Transformed<Any, Email>(chain.content, content)
        return ActionChain(ChainState.NEXT, transformed)
    }

    override fun execute(chain: ActionChain<Transformed<Any, Email>>): ActionChain<Executed<Any, Any>> {
        val email = chain.content.processed
        email.id = UUID.randomUUID().toString()
        email.from = from
        email.approve = {
            logger.info("Approving email[$email]")
            emailService.send(email)
        }
        email.reject = {
            logger.info("Rejecting email[$email]")
            actionApprovalManager.removeFromApproval(email.id)
        }
        actionApprovalManager.addForApproval(email)
        val message = "${ActionStatus.APPROVAL_NEEDED.message}.\nPlease find the email to be sent.\n\n${email.forApproval()}".trim()
        return ActionChain(ChainState.FINISHED, Executed(chain.content.processed, message))
    }

    override fun toString() = this.name()
}