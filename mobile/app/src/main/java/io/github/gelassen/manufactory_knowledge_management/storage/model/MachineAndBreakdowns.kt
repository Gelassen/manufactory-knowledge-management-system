package io.github.gelassen.manufactory_knowledge_management.storage.model

import androidx.room.Embedded
import androidx.room.Relation

data class MachineAndBreakdowns(
    @Embedded
    val machine: MachineEntity,
    @Relation(
        parentColumn = Schema.Machine.UID,
        entityColumn = Schema.Breakdown.MACHINE_ID
    )
    val breakdowns: List<BreakdownEntity>
)
