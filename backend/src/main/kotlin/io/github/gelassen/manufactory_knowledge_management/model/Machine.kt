package io.github.gelassen.manufactory_knowledge_management.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import java.util.UUID

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
    @Column(
        nullable = false,
        unique = true,
        length = 36
    )
    var barcode: String = UUID.randomUUID().toString(),

    @OneToMany(
        mappedBy = "machine",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @JsonManagedReference
    var breakdowns: MutableList<Breakdown> = mutableListOf()
) {

    companion object {
        const val TABLE_NAME = "Machines"
    }
}
