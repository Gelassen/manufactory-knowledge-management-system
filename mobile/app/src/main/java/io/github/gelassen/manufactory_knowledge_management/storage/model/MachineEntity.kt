package io.github.gelassen.manufactory_knowledge_management.storage.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = Schema.Machine.TABLE_NAME,
)
data class MachineEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Schema.Machine.UID)
    val id: Long,
    @ColumnInfo(name = Schema.Machine.NAME)
    val name: String,
    @ColumnInfo(name = Schema.Machine.MANUFACTURER)
    val manufacturer: String,
    @ColumnInfo(name = Schema.Machine.BARCODE)
    val barcode: String
)
