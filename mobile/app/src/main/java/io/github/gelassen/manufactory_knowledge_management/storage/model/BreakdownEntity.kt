package io.github.gelassen.manufactory_knowledge_management.storage.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import io.github.gelassen.manufactory_knowledge_management.storage.Schema

@Entity(
    tableName = Schema.Breakdown.TABLE_NAME,
    foreignKeys = arrayOf(
        ForeignKey(
            entity = MachineEntity::class,
            parentColumns = arrayOf(Schema.Machine.UID),
            childColumns = arrayOf(Schema.Breakdown.MACHINE_ID),
            onDelete = ForeignKey.CASCADE
        )
    )
)
data class BreakdownEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Schema.Breakdown.UID)
    val id: Long,
    @ColumnInfo(name = Schema.Breakdown.FAILURE)
    val failure: String,
    @ColumnInfo(name = Schema.Breakdown.SOLUTION)
    val solution: String,
    @ColumnInfo(name = Schema.Breakdown.DATE)
    val date: Long,
    @ColumnInfo(name = Schema.Breakdown.MACHINE_ID)
    val machineId: Long
)
