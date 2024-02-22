package io.github.gelassen.manufactory_knowledge_management.model

import jakarta.persistence.Entity

@Entity(name = "Machine")
data class Machine(
    var id: Long,
    var name: String,
    var manufacturer: String,
)
