package io.github.gelassen.manufactory_knowledge_management.model

data class Breakdown(
    val id: Long,
    val failure: String,
    val solution: String,
    val date: Long,
    val photos: List<Photofixation> = emptyList()
)
