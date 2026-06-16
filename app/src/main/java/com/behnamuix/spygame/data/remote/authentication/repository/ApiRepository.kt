package com.behnamuix.spygame.data.remote.authentication.repository

import com.behnamuix.appointment.const.API_URL
import com.behnamuix.spygame.data.remote.authentication.model.ReqApi
import com.behnamuix.spygame.data.remote.authentication.model.RespApi
import com.behnamuix.spygame.utils.setLog
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ApiRepository(private val client: HttpClient) {

    suspend fun sendSms(reqApi: ReqApi): RespApi? {
        return try {
            client.post(API_URL) {
                setBody(reqApi)
                contentType(ContentType.Application.Json)
            }.body()
        } catch (e: Exception) {
            e.printStackTrace()
            setLog(e.message.toString())
            null
        }

    }

}