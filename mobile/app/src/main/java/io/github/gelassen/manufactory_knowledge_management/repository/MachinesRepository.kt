package io.github.gelassen.manufactory_knowledge_management.repository

import android.util.Log
import io.github.gelassen.manufactory_knowledge_management.App
import io.github.gelassen.manufactory_knowledge_management.model.Machine
import io.github.gelassen.manufactory_knowledge_management.model.asEntity
import io.github.gelassen.manufactory_knowledge_management.network.IApi
import io.github.gelassen.manufactory_knowledge_management.network.model.Response
import io.github.gelassen.manufactory_knowledge_management.network.model.toApiResponse
import io.github.gelassen.manufactory_knowledge_management.storage.dao.MachinesDao
import io.github.gelassen.manufactory_knowledge_management.storage.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MachinesRepository @Inject constructor(
    var api: IApi,
    var machinesDao: MachinesDao
) : IMachinesRepository {

    override fun getCachedMachineByBarcode(barcode: String): Flow<Machine?> {
        return machinesDao.getMachineByBarcodeFlow(barcode)
            .map { it?.toDomain() }
    }

    override suspend fun fetchMachineByBarcode(barcode: String): Response<Machine> {
        lateinit var result: Response<Machine>
        try {
            val response = api.getMachineByBarcode(barcode)
            if (response.isSuccessful) {
                result = Response.Data(response.body()!!.data as Machine)
            } else {
                result = Response.Error.Message(response.errorBody()!!.toString().toApiResponse().message)
            }
        } catch (ex: Exception) {
            val errorMsg = "Failed to request machine by barcode $barcode"
            Log.e(App.TAG, errorMsg, ex)
            result = Response.Error.Message(errorMsg)
        }
        if (result is Response.Data) {
            machinesDao.saveMachine(result.data.asEntity())
        }
        return result
    }

    override suspend fun getMachineByUniqueIdentifier(barcode: String): Machine? {
        return machinesDao.getMachineByBarcode(barcode)?.toDomain()
    }
}