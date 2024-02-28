package io.github.gelassen.manufactory_knowledge_management.model

data class ApiResponse<T>(
    val data: T? = null,
    var message: String = "Success"
)