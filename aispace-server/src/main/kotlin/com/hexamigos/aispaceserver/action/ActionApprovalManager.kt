package com.hexamigos.aispaceserver.action

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component


@Component
class ActionApprovalManager(val pendingActions: HashMap<String, Approval> = HashMap()) {
    private val logger = LoggerFactory.getLogger(ActionApprovalManager::class.java)

    fun approve(id: String) = pendingActions[id]?.approve?.invoke().let { pendingActions.remove(id) }

    fun reject(id: String) = pendingActions[id]?.reject?.invoke().let { pendingActions.remove(id) }

    fun addForApproval(approval: Approval) {
        pendingActions[approval.id] = approval
    }

    fun removeFromApproval(id: String) {
        pendingActions.remove(id)
    }
}