package io.github.gelassen.manufactory_knowledge_management.storage.model

import androidx.room.Embedded
import androidx.room.Relation
import io.github.gelassen.manufactory_knowledge_management.model.Machine
import io.github.gelassen.manufactory_knowledge_management.storage.Schema

data class MachineAndBreakdowns(
    @Embedded
    val machine: MachineEntity,
    /**
     * Pay attention to extra field entity and aggregated data class instead of
     * usual entity for one-to-many relationship
     * @see https://developer.android.com/training/data-storage/room/relationships#nested-relationships
     * */
    @Relation(
        entity = BreakdownEntity::class,
        parentColumn = Schema.Machine.UID,
        entityColumn = Schema.Breakdown.MACHINE_ID
    )
    val breakdowns: List<BreakdownAndPhotos>
)

fun MachineAndBreakdowns.toDomain(): Machine {
    return Machine(
        id = machine.id,
        name = machine.name,
        manufacturer = machine.manufacturer,
        barcode = machine.barcode,
        breakdowns = breakdowns.map { it.toDomain() }
    )
}
