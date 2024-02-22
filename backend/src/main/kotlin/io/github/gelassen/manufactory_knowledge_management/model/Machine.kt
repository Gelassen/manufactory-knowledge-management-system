package io.github.gelassen.manufactory_knowledge_management.model

data class Machine(
    var id: Long,
    var name: String,
    var manufacturer: String,
    var breakdowns: Collection<Breakdown>
)
