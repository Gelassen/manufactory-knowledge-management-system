package io.github.gelassen.manufactory_knowledge_management.network

import io.github.gelassen.manufactory_knowledge_management.model.Machine
import io.github.gelassen.manufactory_knowledge_management.network.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface IApi {

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("/api/v1/machine")
    suspend fun getMachineByBarcode(
        @Query("barcode") barcode: String
    ) : Response<ApiResponse<Machine>>
}