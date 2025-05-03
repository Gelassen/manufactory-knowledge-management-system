package io.github.gelassen.manufactory_knowledge_management

import io.github.gelassen.manufactory_knowledge_management.model.ApiResponse
import io.github.gelassen.manufactory_knowledge_management.model.Machine
import io.github.gelassen.manufactory_knowledge_management.model.request.MachineDTO
import io.github.gelassen.manufactory_knowledge_management.services.MachineService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
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

    @GetMapping
    fun getMachineByBarcode(model: Model, @RequestParam barcode: String): ResponseEntity<ApiResponse<Machine>> {
        val result: ResponseEntity<ApiResponse<Machine>>
        val machine = machineService.getMachineByBarcode(barcode)
        if (machine != null) {
            result = ResponseEntity(ApiResponse(machine), HttpStatus.OK)
        } else {
            result = ResponseEntity(
                ApiResponse(message = "Machine with '$barcode' barcode does not exist. Did you send the right barcode?"),
                HttpStatus.NOT_FOUND
            )
        }
        return result
    }

    @PostMapping
    fun addMachine(@RequestBody machineDto: MachineDTO): ResponseEntity<Machine> {
        val saved = machineService.saveMachine(machineDto)
        return ResponseEntity.ok(saved)
    }

}