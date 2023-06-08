package com.hexamigos.aispaceserver.action

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct


@Component
class ActionCenter(val actionManager: ActionManager, val availableActions: ArrayList<ActionType> = arrayListOf()) {
    private val logger = LoggerFactory.getLogger(ActionCenter::class.java)

    @PostConstruct
    fun build() {
        ActionType.values().filter { it.tags.isNotEmpty() }.forEach { availableActions.add(it) }
        logger.info("Available actions initialized. Currently [{}] actions available for uses [actions: {}]", availableActions.size, availableActions)
    }

    fun detectAndExecute(input: String): List<String> {
        logger.info("Detecting and Executing actions")
        return actionManager.detectAction(input)
                .map { action -> actionManager.performAction(input, action) }
                .map { actionStatus -> actionStatus.content }
                .map { content -> if (content is ActionResponse<*, *>) content.processed.toString() else content.toString() }
                .toList();
    }
}