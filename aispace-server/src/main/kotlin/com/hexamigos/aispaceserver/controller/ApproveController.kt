package com.hexamigos.aispaceserver.controller

import com.hexamigos.aispaceserver.action.ActionApprovalManager
import com.hexamigos.aispaceserver.domain.ApprovalResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/action")
class ApproveController(val actionApprovalManager: ActionApprovalManager) {

    @GetMapping("/approve/{id}")
    fun approve(@PathVariable("id") id: String): ApprovalResponse {
        return ApprovalResponse(actionApprovalManager.approve(id) != null)
    }

    @GetMapping("/reject/{id}")
    fun reject(@PathVariable("id") id: String): ApprovalResponse {
        return ApprovalResponse(actionApprovalManager.reject(id) != null)
    }
}