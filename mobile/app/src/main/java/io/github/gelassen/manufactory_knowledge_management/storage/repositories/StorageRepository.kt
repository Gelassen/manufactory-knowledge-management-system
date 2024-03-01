package io.github.gelassen.manufactory_knowledge_management.storage.repositories

import io.github.gelassen.manufactory_knowledge_management.model.Machine
import io.github.gelassen.manufactory_knowledge_management.model.fromDomain
import io.github.gelassen.manufactory_knowledge_management.storage.dao.MachinesDao
import io.github.gelassen.manufactory_knowledge_management.storage.model.MachineEntity
import io.github.gelassen.manufactory_knowledge_management.storage.model.toDomain

class StorageRepository(
    private val machinesDao: MachinesDao
) : IMachinesRepository {
    override suspend fun saveMachine(machine: Machine): Long {
        return machinesDao.saveMachine(machine.fromDomain())
    }

    override suspend fun getMachineByBarcode(barcode: String): Machine {
        return machinesDao.getMachineByBarcode(barcode).toDomain()
    }
}


