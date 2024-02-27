package io.github.gelassen.manufactory_knowledge_management

import io.github.gelassen.manufactory_knowledge_management.model.Breakdown
import io.github.gelassen.manufactory_knowledge_management.model.Machine
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.Repository

@NoRepositoryBean
interface CustomMachinesRepository : Repository<Machine, Long> {

    fun getMachineByBarcode(barcode: String) : Machine?

}

interface MachinesRepository : CrudRepository<Machine, Long>, CustomMachinesRepository { }

/*
*   Ref. https://docs.spring.io/spring-data/jpa/reference/repositories/definition.html#page-title
* */
@NoRepositoryBean
interface CustomBreakdownsRepository : Repository<Breakdown, Long> {

    fun getBreakdownsByMachine(machineId: Long) : Collection<Breakdown>

}

interface BreakdownsRepository : CrudRepository<Breakdown, Long>, CustomBreakdownsRepository { }