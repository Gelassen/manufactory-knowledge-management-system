package io.github.gelassen.manufactory_knowledge_management.storage.repositories

import io.github.gelassen.manufactory_knowledge_management.model.Machine
import io.github.gelassen.manufactory_knowledge_management.storage.model.MachineEntity

interface IMachinesRepository {

    suspend fun saveMachine(machine: Machine) : List<Long>

    suspend fun getMachineByUniqueIdentifier(barcode: String) : Machine?

}