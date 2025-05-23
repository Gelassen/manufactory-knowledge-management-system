package io.github.gelassen.manufactory_knowledge_management

import io.github.gelassen.manufactory_knowledge_management.model.ApiResponse
import io.github.gelassen.manufactory_knowledge_management.model.Machine
import io.github.gelassen.manufactory_knowledge_management.model.request.BreakdownDTO
import io.github.gelassen.manufactory_knowledge_management.model.request.MachineDTO
import io.github.gelassen.manufactory_knowledge_management.services.MachineService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
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
    fun getAllMachines(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(required = false) text: String?
    ): ResponseEntity<ApiResponse<Map<String, Any>>> {
        val pageable: Pageable = PageRequest.of(page, size)
        val machinePage = machineService.getMachines(
            pageable,
            text,
            manufacturerText = text
        )

        val responseBody = mapOf(
            "data" to machinePage.content,
            "total" to machinePage.totalElements
        )

        return ResponseEntity(ApiResponse(responseBody), HttpStatus.OK)
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


    @PostMapping()
    fun addMachine(@RequestBody machineDto: MachineDTO): ResponseEntity<Machine> {
        val saved = machineService.saveMachine(machineDto)
        return ResponseEntity.ok(saved)
    }

    @PostMapping("/{id}/breakdowns")
    fun addBreakdown(
        @PathVariable id: Long,
        @RequestBody breakdownDto: BreakdownDTO
    ): ResponseEntity<Machine> {
        val updatedMachine = machineService.addBreakdown(
            machineId = id,
            breakdownDto = breakdownDto
        )
        return ResponseEntity.ok(updatedMachine)
    }

    @PutMapping("/{machineId}/breakdowns/{breakdownId}")
    fun updateBreakdown(
        @PathVariable machineId: Long,
        @PathVariable breakdownId: Long,
        @RequestBody breakdownDto: BreakdownDTO
    ): ResponseEntity<Machine> {
        val updatedMachine = machineService.updateBreakdown(
            machineId = machineId,
            breakdownId = breakdownId,
            breakdownDto = breakdownDto
        )
        return ResponseEntity.ok(updatedMachine)
    }

    @GetMapping("/{id}/breakdowns")
    fun getBreakdownsByMachine(
        @PathVariable id: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "3") size: Int,
    ): ResponseEntity<ApiResponse<Map<String, Any>>> {
        val pageable: Pageable = PageRequest.of(page, size)
        val breakdownsPage = machineService.getBreakdownsByMachine(pageable, id)

        val responseBody = mapOf(
            "data" to breakdownsPage.content,
            "total" to breakdownsPage.totalElements
        )

        return ResponseEntity(ApiResponse(responseBody), HttpStatus.OK)
    }


}