package io.github.gelassen.manufactory_knowledge_management.model

import jakarta.persistence.*

@Entity(name = "Photofixations")
data class Photofixation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var photo: String, /* Base64 string*/
    @ManyToOne
    @JoinColumn(name = "breakdown_id")
    var breakdown: Breakdown? = null
)