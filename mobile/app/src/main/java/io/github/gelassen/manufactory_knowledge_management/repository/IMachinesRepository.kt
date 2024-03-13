package io.github.gelassen.manufactory_knowledge_management.repository

import io.github.gelassen.manufactory_knowledge_management.model.Machine
import io.github.gelassen.manufactory_knowledge_management.network.model.Response
import kotlinx.coroutines.flow.Flow

interface IMachinesRepository {

    suspend fun fetchMachineByBarcode(barcode: String): Response<Machine>

    /**
     * Flow is used for data which expected to be changed and it is a right
     * way to implement an Observer pattern on coroutines
     * */
    fun getCachedMachineByBarcode(barcode: String): Flow<Machine?>

    suspend fun getMachineByUniqueIdentifier(barcode: String) : Machine?
}