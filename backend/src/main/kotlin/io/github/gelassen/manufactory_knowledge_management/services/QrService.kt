package io.github.gelassen.manufactory_knowledge_management.services

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID
import io.github.gelassen.manufactory_knowledge_management.repository.MachinesRepository
import jakarta.persistence.EntityNotFoundException


@Service
class QrService(
    private val machineRepository: MachinesRepository,
) {

    @Transactional
    fun getOrCreateQrValue(machineId: Long): String {
        val machine = machineRepository.findById(machineId)
            .orElseThrow {
                EntityNotFoundException(
                    "Machine $machineId not found"
                )
            }

        if (machine.barcode.isNullOrBlank()) {
            machine.barcode = UUID.randomUUID().toString()
            machineRepository.save(machine)
        }

        // TODO: migrate url to properties
//        return "https://192.168.0.136:3000/api/v1/machine/barcode/${machine.barcode}"
        return machine.barcode
    }
}