package io.github.gelassen.manufactory_knowledge_management

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/v1/machine")
class MachineController(
    private val repository: MachinesRepository
) {

    @GetMapping("/barcode/{barcode}")
    fun getMachineByBarcode(@PathVariable barcode: String) =
        repository.findMachineByBarcode(barcode) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Machine with $barcode barcode does not exist. Did you send the right barcode?"
        )

}