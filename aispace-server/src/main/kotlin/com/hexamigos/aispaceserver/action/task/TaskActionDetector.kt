package com.hexamigos.aispaceserver.action.task

import com.hexamigos.aispaceserver.action.ActionDetector
import com.hexamigos.aispaceserver.action.ActionType
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class TaskActionDetector : ActionDetector(ActionType.TASK)