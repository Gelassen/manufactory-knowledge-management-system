package org.openapitools.api

import org.openapitools.model.ApiResponseMachine
import org.openapitools.model.ApiResponseMessage
import io.swagger.v3.oas.annotations.*
import io.swagger.v3.oas.annotations.enums.*
import io.swagger.v3.oas.annotations.media.*
import io.swagger.v3.oas.annotations.responses.*
import io.swagger.v3.oas.annotations.security.*
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

import org.springframework.web.bind.annotation.*
import org.springframework.validation.annotation.Validated
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.beans.factory.annotation.Autowired

import javax.validation.Valid
import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Email
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

import kotlin.collections.List
import kotlin.collections.Map

@RestController
@Validated
@RequestMapping("\${api.base-path:}")
class ApiApiController() {

    @Operation(
        summary = "Get a machine by barcode",
        operationId = "apiV1MachineGet",
        description = """""",
        responses = [
            ApiResponse(responseCode = "200", description = "Machine found", content = [Content(schema = Schema(implementation = ApiResponseMachine::class))]),
            ApiResponse(responseCode = "404", description = "Machine not found", content = [Content(schema = Schema(implementation = ApiResponseMessage::class))]) ]
    )
    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/api/v1/machine"],
        produces = ["application/json"]
    )
    fun apiV1MachineGet(@NotNull @Parameter(description = "", required = true) @Valid @RequestParam(value = "barcode", required = true) barcode: kotlin.String): ResponseEntity<ApiResponseMachine> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }
}
