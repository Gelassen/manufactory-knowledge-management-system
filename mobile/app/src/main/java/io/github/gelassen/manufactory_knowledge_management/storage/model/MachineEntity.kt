package io.github.gelassen.manufactory_knowledge_management.storage.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import io.github.gelassen.manufactory_knowledge_management.storage.Schema

@Entity(
    tableName = Schema.Machine.TABLE_NAME,
    indices = [Index(value = [Schema.Machine.UID])]
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
