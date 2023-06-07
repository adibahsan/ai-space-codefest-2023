package com.hexamigos.aispaceserver.action.meeting

import com.hexamigos.aispaceserver.action.ActionDetector
import com.hexamigos.aispaceserver.action.ActionType
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class MeetingActionDetector : ActionDetector(ActionType.MEETING) {
    @PostConstruct
    private fun build() {
        tags["@meeting"] = true
    }

    override fun toString() = "TaskActionDetector"
}