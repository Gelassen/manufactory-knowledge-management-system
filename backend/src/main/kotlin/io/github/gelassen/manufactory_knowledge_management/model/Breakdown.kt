package io.github.gelassen.manufactory_knowledge_management.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity(name = "breakdown")
data class Breakdown(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var failure: String,
    var solution: String,
    var dateTime: Long,
    var machineId: Long,
    @ManyToOne
    var machine: Machine
)
