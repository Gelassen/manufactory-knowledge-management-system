package io.github.gelassen.manufactory_knowledge_management.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity(name = "Photofixation")
data class Photofixation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var photo: String, /* Base64 string*/
    @ManyToOne
    var breakdown: Breakdown
)