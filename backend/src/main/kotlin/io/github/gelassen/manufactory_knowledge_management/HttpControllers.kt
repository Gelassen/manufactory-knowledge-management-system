package io.github.gelassen.manufactory_knowledge_management

import io.github.gelassen.manufactory_knowledge_management.model.ApiResponse
import io.github.gelassen.manufactory_knowledge_management.model.Machine
import io.github.gelassen.manufactory_knowledge_management.model.request.MachineDTO
import io.github.gelassen.manufactory_knowledge_management.services.MachineService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/machine")
class MachineController(
    private val machineService: MachineService
) {

    @GetMapping("/all")
    fun getAllMachines(model: Model): ResponseEntity<ApiResponse<List<Machine>>> {
        val result: ResponseEntity<ApiResponse<List<Machine>>>
        val machines = machineService.getMachines()
        result = ResponseEntity(ApiResponse(machines), HttpStatus.OK)
        return result
    }

    @GetMapping("/barcode/{barcode}")
    fun getMachineByBarcode(
        @PathVariable barcode: String
    ): ResponseEntity<ApiResponse<Machine>> {
        val machine = machineService.getMachineByBarcode(barcode)
        return if (machine != null) {
            ResponseEntity(ApiResponse(machine), HttpStatus.OK)
        } else {
            ResponseEntity(
                ApiResponse(message = "Machine with barcode '$barcode' not found."),
                HttpStatus.NOT_FOUND
            )
        }
    }

    @GetMapping("/{id}")
    fun getMachineById(
        @PathVariable id: Long
    ): ResponseEntity<ApiResponse<Machine>> {
        val machine = machineService.getMachineById(id)
        return if (machine != null) {
            ResponseEntity(ApiResponse(machine), HttpStatus.OK)
        } else {
            ResponseEntity(
                ApiResponse(message = "Machine with ID '$id' not found."),
                HttpStatus.NOT_FOUND
            )
        }
    }


    @PostMapping
    fun addMachine(@RequestBody machineDto: MachineDTO): ResponseEntity<Machine> {
        val saved = machineService.saveMachine(machineDto)
        return ResponseEntity.ok(saved)
    }

}