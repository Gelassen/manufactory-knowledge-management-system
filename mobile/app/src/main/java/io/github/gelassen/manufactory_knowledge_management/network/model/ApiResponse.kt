package io.github.gelassen.manufactory_knowledge_management.network.model

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import io.github.gelassen.manufactory_knowledge_management.model.Machine

data class ApiResponse<T> (
    @SerializedName("data") var data : T?,
    @SerializedName("message") var message : String
)

fun String.toApiResponse() : ApiResponse<Machine> {
    val gson = GsonBuilder().create()
    return gson.fromJson<ApiResponse<Machine>>(this, ApiResponse::class.java)
}