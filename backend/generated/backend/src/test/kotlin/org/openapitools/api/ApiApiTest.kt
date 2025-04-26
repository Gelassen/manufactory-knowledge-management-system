package org.openapitools.api

import org.openapitools.model.ApiResponseMachine
import org.openapitools.model.ApiResponseMessage
import org.junit.jupiter.api.Test
import org.springframework.http.ResponseEntity

class ApiApiTest {

    private val api: ApiApiController = ApiApiController()

    /**
     * To test ApiApiController.apiV1MachineGet
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    fun apiV1MachineGetTest() {
        val barcode: kotlin.String = TODO()
        val response: ResponseEntity<ApiResponseMachine> = api.apiV1MachineGet(barcode)

        // TODO: test validations
    }
}
