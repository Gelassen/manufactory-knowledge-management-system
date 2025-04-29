package io.github.gelassen.manufactory_knowledge_management.services

import io.github.gelassen.manufactory_knowledge_management.MachinesRepository
import io.github.gelassen.manufactory_knowledge_management.model.Machine
import io.github.gelassen.manufactory_knowledge_management.model.request.MachineDTO
import io.github.gelassen.manufactory_knowledge_management.model.request.toEntity

class MachineService(private val repository: MachinesRepository) {

    fun getMachines() : List<Machine> {
        return repository.getAllMachines()
    }

    fun getMachineByBarcode(barcode: String) : Machine? {
        return repository.findMachineByBarcode(barcode)
    }

    fun saveMachine(data: MachineDTO) : Machine {
        val machineEntity = data.toEntity()
        return  repository.save(machineEntity)
    }

}