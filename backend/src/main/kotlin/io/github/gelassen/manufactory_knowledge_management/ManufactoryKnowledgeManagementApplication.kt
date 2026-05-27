package io.github.gelassen.manufactory_knowledge_management

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(
	exclude = [RepositoryRestMvcAutoConfiguration::class],
	scanBasePackages = [
        "io.github.gelassen.manufactory_knowledge_management"
    ]
)
class ManufactoryKnowledgeManagementApplication

fun main(args: Array<String>) {
	runApplication<ManufactoryKnowledgeManagementApplication>(*args)
}
