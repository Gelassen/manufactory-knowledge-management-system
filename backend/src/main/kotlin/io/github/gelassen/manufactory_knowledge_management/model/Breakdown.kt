package io.github.gelassen.manufactory_knowledge_management.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.util.*

@Entity(name = "Breakdowns")
data class Breakdown(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "breakdown_id")
    var id: Long? = null,
    var title: String,
    @Column(length = 5000)
    var failure: String,
    @Column(length = 5000)
    var solution: String,
    var dateTime: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id")
    @JsonBackReference
    var machine: Machine? = null,

    @OneToMany(mappedBy = "breakdown")
    var photofixations: Collection<Photofixation> = emptyList()
)
