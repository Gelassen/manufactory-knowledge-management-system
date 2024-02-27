package io.github.gelassen.manufactory_knowledge_management.model

import jakarta.persistence.*
import java.util.*

@Entity(name = "Breakdowns")
data class Breakdown(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "breakdown_id")
    var id: Long? = null,
    var failure: String,
    var solution: String,
    var dateTime: Long,

    @ManyToOne
    @JoinColumn(name = "machine_id")
    var machine: Machine? = null,

    @OneToMany(mappedBy = "breakdown")
    var photofixations: Collection<Photofixation> = emptyList()
)
