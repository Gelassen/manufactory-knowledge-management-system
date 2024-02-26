package io.github.gelassen.manufactory_knowledge_management

import io.github.gelassen.manufactory_knowledge_management.model.Breakdown
import io.github.gelassen.manufactory_knowledge_management.model.Machine
import org.springframework.data.repository.CrudRepository

interface MachinesRepository : CrudRepository<Machine, Long> {

    fun getMachineByBarcode(barcode: String) : Machine?

}

interface BreakdownsRepository : CrudRepository<Breakdown, Long> {

    /*fun getBreakdownsByMachine(machineId: Long) : Collection<Breakdown>*/

}