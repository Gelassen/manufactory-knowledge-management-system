package io.github.gelassen.manufactory_knowledge_management.repository

import io.github.gelassen.manufactory_knowledge_management.model.Machine
import io.github.gelassen.manufactory_knowledge_management.network.model.ApiResponse
import kotlinx.coroutines.flow.Flow

interface IMachinesRepository {

    suspend fun getMachineByBarcode(barcode: String): ApiResponse<Machine>

    /**
     * Unlike {@link #getMachineByBarcode(String) getMachineByBarcode} this call
     * not only executes web request, but also caches data. Returned result
     * should be used only for recognizing if there was an error in request.
     *
     * {@link #getCachedMachineByBarcode(String) getCachedMachineByBarcode(String)}
     * should be used in parallel to access recently fetched data
     *
     * @return api response wrapped into class with request metadata
     * */
    suspend fun fetchMachineByBarcode(barcode: String): ApiResponse<Machine>

    fun getCachedMachineByBarcode(barcode: String): Flow<Machine>

    suspend fun saveMachine(machine: Machine) : List<Long>

    suspend fun getMachineByUniqueIdentifier(barcode: String) : Machine?
}