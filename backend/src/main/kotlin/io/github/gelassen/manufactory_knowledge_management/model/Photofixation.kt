package io.github.gelassen.manufactory_knowledge_management.model

data class Photofixation(
    var id: Long,
    var photo: String, /* Base64 string*/
    var breakdownId: Long
)