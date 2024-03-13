package io.github.gelassen.manufactory_knowledge_management.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import io.github.gelassen.manufactory_knowledge_management.storage.model.MachineAndBreakdowns
import io.github.gelassen.manufactory_knowledge_management.storage.model.MachineEntity
import io.github.gelassen.manufactory_knowledge_management.storage.Schema
import kotlinx.coroutines.flow.Flow

@Dao
interface MachinesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMachine(vararg machineEntity: MachineEntity): List<Long>

    @Transaction
    @Query("" +
            "SELECT * FROM ${Schema.Machine.TABLE_NAME} " +
            "LEFT OUTER JOIN ${Schema.Breakdown.TABLE_NAME} " +
            "ON ${Schema.Machine.TABLE_NAME}.${Schema.Machine.UID} " +
                " = " +
                "${Schema.Breakdown.TABLE_NAME}.${Schema.Breakdown.MACHINE_ID} " +
            "LEFT OUTER JOIN ${Schema.Photofixation.TABLE_NAME} " +
                "ON ${Schema.Breakdown.TABLE_NAME}.${Schema.Breakdown.UID} " +
                " = " +
                "${Schema.Photofixation.TABLE_NAME}.${Schema.Photofixation.BREAKDOWN_ID} " +
            "WHERE ${Schema.Machine.TABLE_NAME}.${Schema.Machine.BARCODE} = :barcode;"
    )
    suspend fun getMachineByBarcode(barcode: String): MachineAndBreakdowns?

    @Transaction
    @Query("" +
            "SELECT * FROM ${Schema.Machine.TABLE_NAME} " +
            "LEFT OUTER JOIN ${Schema.Breakdown.TABLE_NAME} " +
            "ON ${Schema.Machine.TABLE_NAME}.${Schema.Machine.UID} " +
            " = " +
            "${Schema.Breakdown.TABLE_NAME}.${Schema.Breakdown.MACHINE_ID} " +
            "LEFT OUTER JOIN ${Schema.Photofixation.TABLE_NAME} " +
            "ON ${Schema.Breakdown.TABLE_NAME}.${Schema.Breakdown.UID} " +
            " = " +
            "${Schema.Photofixation.TABLE_NAME}.${Schema.Photofixation.BREAKDOWN_ID} " +
            "WHERE ${Schema.Machine.TABLE_NAME}.${Schema.Machine.BARCODE} = :barcode;"
    )
    fun getMachineByBarcodeFlow(barcode: String): Flow<MachineAndBreakdowns?>
}