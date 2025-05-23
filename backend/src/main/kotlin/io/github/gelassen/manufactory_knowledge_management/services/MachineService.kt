package io.github.gelassen.manufactory_knowledge_management.services

import io.github.gelassen.manufactory_knowledge_management.model.Breakdown
import io.github.gelassen.manufactory_knowledge_management.repository.MachinesRepository
import io.github.gelassen.manufactory_knowledge_management.model.Machine
import io.github.gelassen.manufactory_knowledge_management.model.request.BreakdownDTO
import io.github.gelassen.manufactory_knowledge_management.model.request.MachineDTO
import io.github.gelassen.manufactory_knowledge_management.model.request.toEntity
import jakarta.persistence.EntityNotFoundException
import org.apache.logging.log4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import kotlin.math.log

@Transactional
@Service
class MachineService(private val repository: MachinesRepository) {

    val logger = LoggerFactory.getLogger(MachineService::class.java)

    fun getMachines(pageable: Pageable, name: String?, manufacturerText: String?): Page<Machine> {
        return if (!name.isNullOrBlank()) {
            repository.findByNameContainingIgnoreCaseOrManufacturerContainingIgnoreCase(
                name = name,
                manufacturer= manufacturerText,
                pageable
            )
        } else {
            repository.findAll(pageable)
        }
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

    fun updateBreakdown(machineId: Long, breakdownId: Long, breakdownDto: BreakdownDTO): Machine {
        val machine = repository.findById(machineId)
            .orElseThrow { throw IllegalArgumentException("Machine not found: $machineId") }

        val breakdown = machine.breakdowns.find { it.id == breakdownId }
            ?: throw IllegalArgumentException("Breakdown not found: $breakdownId")

        // Prefer mutating existing object instead of replacing it
        breakdown.title = breakdownDto.title
        breakdown.failure = breakdownDto.failure
        breakdown.solution = breakdownDto.solution
        breakdown.dateTime = Instant.now().epochSecond

        logger.debug("MachineService::updateBreakdown - machine after editing breakdown: {}", machine)
        return repository.save(machine)
    }


    fun getBreakdownsByMachine(pageable: Pageable, machineId: Long): Page<Breakdown> {
        val machine = repository.findById(machineId)
            .orElseThrow { EntityNotFoundException("Machine with ID $machineId not found") }

        val breakdowns = machine.breakdowns.sortedByDescending { it.dateTime } // or custom sort

        val start = pageable.offset.toInt()
        val end = (start + pageable.pageSize).coerceAtMost(breakdowns.size)

        val content = if (start >= breakdowns.size) emptyList() else breakdowns.subList(start, end)

        return PageImpl(content, pageable, breakdowns.size.toLong())
    }


}