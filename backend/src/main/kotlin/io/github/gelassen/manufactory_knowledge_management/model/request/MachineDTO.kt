package io.github.gelassen.manufactory_knowledge_management.model.request

import io.github.gelassen.manufactory_knowledge_management.model.Machine

data class MachineDTO(
    val name: String,
    val manufacturer: String,
    val barcode: String,
    val breakdowns: List<BreakdownDTO> = emptyList()
)

fun MachineDTO.toEntity(): Machine {
    val machine = Machine(
        name = this.name,
        manufacturer = this.manufacturer,
        barcode = this.barcode
    )
    // Associate breakdowns with this machine
    machine.breakdowns = this.breakdowns.map { it.toEntity(machine) }.toMutableList()
    return machine
}
