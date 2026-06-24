package com.behnamuix.spygame.data.remote.authentication.model

import kotlinx.serialization.Serializable

@Serializable
data class ReqApi(
    val username:String,
    val password:String,
    val to:String,
    val from:String,
    val text:String
)
@Serializable
data class RespApi(
    val Value:String,
    val RetStatus:Int,
    val StrRetStatus:String
)
