package io.github.gelassen.manufactory_knowledge_management.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity(name = "Machines")
data class Machine(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "machine_id")
    var id: Long? = null,
    var name: String,
    var manufacturer: String,
    var barcode: String,
    @OneToMany(mappedBy = "machine")
    var breakdowns: Collection<Breakdown> = emptyList()
)
