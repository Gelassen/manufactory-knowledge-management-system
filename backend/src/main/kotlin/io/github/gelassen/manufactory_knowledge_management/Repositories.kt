package io.github.gelassen.manufactory_knowledge_management

import io.github.gelassen.manufactory_knowledge_management.model.Machine
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.Repository


@NoRepositoryBean
interface CustomMachinesRepository : Repository<Machine, Long> {
    fun findMachineByBarcode(barcode: String) : Machine?
}

interface MachinesRepository : CrudRepository<Machine, Long>, CustomMachinesRepository {}

/*
*   Ref. https://docs.spring.io/spring-data/jpa/reference/repositories/definition.html#page-title
* *//*

@NoRepositoryBean
interface BreakdownsRepository : Repository<Breakdown, Long> {

    fun getBreakdownsByMachine(machineId: Long) : Collection<Breakdown>

}*/
