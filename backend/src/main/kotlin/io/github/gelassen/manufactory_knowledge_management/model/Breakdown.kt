package io.github.gelassen.manufactory_knowledge_management.model

data class Breakdown(
    var id: Long,
    var failure: String,
    var solution: String,
    var dateTime: Long,
    var machineId: Long,
    var photofixations: Collection<Photofixation>
)
