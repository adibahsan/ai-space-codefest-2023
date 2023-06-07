package com.hexamigos.aispaceserver.action.email

import com.hexamigos.aispaceserver.action.ActionDetector
import com.hexamigos.aispaceserver.action.ActionType
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class EmailActionDetector : ActionDetector(ActionType.SEND_EMAIL) {
    @PostConstruct
    private fun build() {
        tags["@send"] = true
    }

    override fun toString(): String {
        return "EmailActionDetector"
    }
}