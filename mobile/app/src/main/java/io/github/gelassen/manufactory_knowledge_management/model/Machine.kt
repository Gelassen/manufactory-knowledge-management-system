package io.github.gelassen.manufactory_knowledge_management.model

import io.github.gelassen.manufactory_knowledge_management.storage.model.MachineAndBreakdowns
import io.github.gelassen.manufactory_knowledge_management.storage.model.MachineEntity

data class Machine(
    val id: Long,
    val name: String,
    val manufacturer: String,
    val barcode: String,
    val breakdowns: List<Breakdown> = emptyList()
)

fun Machine.fromDomain(): MachineEntity {
    return MachineEntity(
        id = this.id,
        name = this.name,
        manufacturer = this.manufacturer,
        barcode = this.barcode
    )
}
