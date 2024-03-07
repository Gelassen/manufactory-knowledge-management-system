package io.github.gelassen.manufactory_knowledge_management.repository

import android.util.Log
import io.github.gelassen.manufactory_knowledge_management.App
import io.github.gelassen.manufactory_knowledge_management.model.Machine
import io.github.gelassen.manufactory_knowledge_management.model.fromDomain
import io.github.gelassen.manufactory_knowledge_management.network.IApi
import io.github.gelassen.manufactory_knowledge_management.network.model.ApiResponse
import io.github.gelassen.manufactory_knowledge_management.network.model.toApiResponse
import io.github.gelassen.manufactory_knowledge_management.storage.dao.MachinesDao
import io.github.gelassen.manufactory_knowledge_management.storage.model.toDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MachinesRepository @Inject constructor(
    var api: IApi,
    var machinesDao: MachinesDao
) : IMachinesRepository {

    override fun getCachedMachineByBarcode(barcode: String): Flow<Machine> {
        TODO("Not yet implemented")
    }

    override suspend fun getMachineByBarcode(barcode: String): ApiResponse<Machine> {
        lateinit var result: ApiResponse<Machine>
        try {
            val response = api.getMachineByBarcode(barcode)
            if (response.isSuccessful) {
                result.data = response.body()!!.data
            } else {
                result = response.errorBody()!!.toString().toApiResponse()
            }
        } catch (ex: Exception) {
            val errorMsg = "Failed to request machine by barcode $barcode"
            Log.e(App.TAG, errorMsg, ex)
            result = ApiResponse(data = null, message = errorMsg)
        }
        return result
    }

    override suspend fun fetchMachineByBarcode(barcode: String): ApiResponse<Machine> {
        lateinit var result: ApiResponse<Machine>
        try {
            val apiResponse = getMachineByBarcode(barcode)
//            apiResponse.data == null

        } catch (ex: Exception) {

        }
        return result
    }

    override suspend fun saveMachine(machine: Machine): List<Long> {
        return machinesDao.saveMachine(machine.fromDomain())
    }

    override suspend fun getMachineByUniqueIdentifier(barcode: String): Machine? {
        return machinesDao.getMachineByBarcode(barcode)?.toDomain()
    }
}