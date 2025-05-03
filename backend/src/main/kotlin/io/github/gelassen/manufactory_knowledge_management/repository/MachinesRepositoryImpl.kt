package io.github.gelassen.manufactory_knowledge_management.repository

import io.github.gelassen.manufactory_knowledge_management.model.Machine
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository

@Repository
class MachinesRepositoryImpl(
    @PersistenceContext private val em: EntityManager
) : CustomMachinesRepository {
    override fun getAllMachines(): List<Machine> {
        val query = em.createQuery("SELECT m FROM ${Machine.TABLE_NAME} m", Machine::class.java)
        return query.resultList
    }
}