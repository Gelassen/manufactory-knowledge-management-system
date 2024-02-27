package io.github.gelassen.manufactory_knowledge_management

import io.github.gelassen.manufactory_knowledge_management.model.Machine
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ProjectConfiguration {

    @Bean
    fun databaseInitializer(machineRepository: MachinesRepository) = ApplicationRunner {
/*        var sodickSaved = machineRepository.save(Machine(
            name = "Sodick 360b",
            manufacturer = "Sodick",
            barcode = "Sodick 360b"
        )
        )*/
/*        var sodickSaved = machineRepository.save(Machine(
                name = "Sodick 360b",
                manufacturer = "Sodick",
                barcode = "Sodick 360b"
            )
        )
        var fanucId = machineRepository.save(Machine(
            name = "Fanuc 2000",
            manufacturer = "Fanuc",
            barcode = "Fanuc 2000")
        )*/
    }
}