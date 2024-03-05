package io.github.gelassen.manufactory_knowledge_management.repository

import android.util.Log
import io.github.gelassen.manufactory_knowledge_management.App
import io.github.gelassen.manufactory_knowledge_management.model.Machine
import io.github.gelassen.manufactory_knowledge_management.network.IApi
import io.github.gelassen.manufactory_knowledge_management.network.model.ApiResponse
import io.github.gelassen.manufactory_knowledge_management.network.model.toApiResponse
import javax.inject.Inject

class MachinesRepository(
    @Inject val api: IApi
) : IMachinesRepository {

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
}