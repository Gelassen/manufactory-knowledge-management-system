package io.github.gelassen.manufactory_knowledge_management.services

import io.github.gelassen.manufactory_knowledge_management.repository.MachinesRepository
import io.github.gelassen.manufactory_knowledge_management.model.Machine
import io.github.gelassen.manufactory_knowledge_management.model.request.MachineDTO
import io.github.gelassen.manufactory_knowledge_management.model.request.toEntity
import org.springframework.stereotype.Service

@Service
class MachineService(private val repository: MachinesRepository) {

    fun getMachines() : List<Machine> {
        return repository.getAllMachines()
    }

    fun getMachineByBarcode(barcode: String) : Machine? {
        return repository.findMachineByBarcode(barcode)
    }

    fun getMachineById(id: Long) : Machine? {
        return repository.findMachineById(id)
    }

    fun saveMachine(data: MachineDTO) : Machine {
        val machineEntity = data.toEntity()
        return  repository.save(machineEntity)
    }

}