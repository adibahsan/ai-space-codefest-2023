package com.hexamigos.aispaceserver.action

import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
abstract class ActionDetector(val actionType: ActionType) {

    fun detect(tokens: HashSet<String>): ActionType {
        val detected = actionType.tags.any { tokens.contains(it) }
        return if (detected) actionType else ActionType.NOT_DETECTED
    }
    override fun toString() = actionType.name
}