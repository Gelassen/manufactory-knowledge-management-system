package io.github.gelassen.manufactory_knowledge_management.services

import io.github.gelassen.manufactory_knowledge_management.model.Breakdown
import io.github.gelassen.manufactory_knowledge_management.repository.MachinesRepository
import io.github.gelassen.manufactory_knowledge_management.model.Machine
import io.github.gelassen.manufactory_knowledge_management.model.request.BreakdownDTO
import io.github.gelassen.manufactory_knowledge_management.model.request.MachineDTO
import io.github.gelassen.manufactory_knowledge_management.model.request.toEntity
import org.apache.logging.log4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.math.log

@Transactional
@Service
class MachineService(private val repository: MachinesRepository) {

    val logger = LoggerFactory.getLogger(MachineService::class.java)

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
        return repository.save(machineEntity)
    }

    fun addBreakdown(machineId: Long, breakdownDto: BreakdownDTO): Machine {
        val machine = repository.findById(machineId)
            .orElseThrow { RuntimeException("Machine not found") }

        machine.breakdowns.add(breakdownDto.toEntity(machine))

        logger.debug("MachineService::addBreakdown - machine after adding breakdown: {}", machine)
        return repository.save(machine)
    }

}