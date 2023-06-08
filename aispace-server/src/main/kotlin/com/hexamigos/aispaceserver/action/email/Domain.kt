package com.hexamigos.aispaceserver.action.email

import com.google.gson.annotations.SerializedName
import com.hexamigos.aispaceserver.action.Approval
import java.util.UUID

data class Email(
        @Transient
        override var id: String = UUID.randomUUID().toString(),
        @Transient
        var from: String = "",
        @SerializedName("actionType")
        var actionType: String? = null,
        @SerializedName("to")
        var to: ArrayList<String> = arrayListOf(),
        @SerializedName("cc")
        var cc: ArrayList<String> = arrayListOf(),
        @SerializedName("sub")
        var sub: String? = null,
        @SerializedName("body")
        var body: String? = null,
        @Transient
        override var approve: () -> Unit,
        @Transient
        override var reject: () -> Unit,
) : Approval(id, approve, reject)
