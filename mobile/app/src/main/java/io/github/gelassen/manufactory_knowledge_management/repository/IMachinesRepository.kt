package io.github.gelassen.manufactory_knowledge_management.repository

import io.github.gelassen.manufactory_knowledge_management.model.Machine
import io.github.gelassen.manufactory_knowledge_management.network.model.ApiResponse

interface IMachinesRepository {

    suspend fun getMachineByBarcode(barcode: String): ApiResponse<Machine>
}