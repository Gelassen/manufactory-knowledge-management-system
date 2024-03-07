package io.github.gelassen.manufactory_knowledge_management.repository

import io.github.gelassen.manufactory_knowledge_management.model.Machine
import io.github.gelassen.manufactory_knowledge_management.network.model.ApiResponse
import io.github.gelassen.manufactory_knowledge_management.network.model.Response
import kotlinx.coroutines.flow.Flow

interface IMachinesRepository {

    suspend fun fetchMachineByBarcode(barcode: String): Response<Machine>
    /**
     * This call not only executes web request, but also caches data. Returned result
     * can have an error message if an api call was unsuccessful.
     *
     * {@link #getCachedMachineByBarcode(String) getCachedMachineByBarcode(String)}
     * could be used in parallel to access recently fetched data.
     *
     * @return response wrapped into domain object
     * */

    suspend fun getCachedMachineByBarcode(barcode: String): Machine?

    suspend fun saveMachine(machine: Machine) : List<Long>

    suspend fun getMachineByUniqueIdentifier(barcode: String) : Machine?
}