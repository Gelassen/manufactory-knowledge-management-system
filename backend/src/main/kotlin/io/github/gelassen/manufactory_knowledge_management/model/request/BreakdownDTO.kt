package io.github.gelassen.manufactory_knowledge_management.model.request

import io.github.gelassen.manufactory_knowledge_management.model.Breakdown
import io.github.gelassen.manufactory_knowledge_management.model.Machine

data class BreakdownDTO(
    val title: String,
    val failure: String,
    val solution: String = "",
    val dateTime: Long,
    val photofixations: List<PhotofixationDTO> = mutableListOf<PhotofixationDTO>()
)

fun BreakdownDTO.toEntity(machine: Machine): Breakdown {
    val breakdown = Breakdown(
        title = this.title,
        failure = this.failure,
        solution = this.solution,
        dateTime = this.dateTime,
        machine = machine
    )
    // Associate photofixations with this breakdown
    breakdown.photofixations = this.photofixations.map { it.toEntity(breakdown) }
    return breakdown
}
