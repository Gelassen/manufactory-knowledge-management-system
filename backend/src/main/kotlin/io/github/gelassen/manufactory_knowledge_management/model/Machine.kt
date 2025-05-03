package io.github.gelassen.manufactory_knowledge_management.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

/**
 * To prevent infinite recursion use solutions described in this
 * publication @link https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
 * */
@Entity(name = Machine.TABLE_NAME)
data class Machine(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "machine_id")
    var id: Long? = null,
    var name: String,
    var manufacturer: String,
    var barcode: String,

    @OneToMany(mappedBy = "machine", fetch = FetchType.LAZY)
    @JsonManagedReference
    var breakdowns: MutableList<Breakdown> = mutableListOf()
) {

    companion object {
        const val TABLE_NAME = "Machines"
    }
}
