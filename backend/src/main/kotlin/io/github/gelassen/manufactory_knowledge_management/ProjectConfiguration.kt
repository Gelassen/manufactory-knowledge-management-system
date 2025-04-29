package io.github.gelassen.manufactory_knowledge_management

import io.github.gelassen.manufactory_knowledge_management.model.Breakdown
import io.github.gelassen.manufactory_knowledge_management.model.Machine
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer



@Configuration
class ProjectConfiguration {

    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**")
                    .allowedOrigins("http://localhost:3000")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true)
            }
        }
    }

//    @Bean
//    fun databaseInitializer(machineRepository: MachinesRepository, breakdownsRepository: BreakdownsRepository) = ApplicationRunner {
//        stubApiResponses(machineRepository, breakdownsRepository)
//    }
//
//    private fun stubApiResponses(machineRepository: MachinesRepository, breakdownsRepository: BreakdownsRepository) {
//        var sodickSaved = machineRepository.save(
//            Machine(
//                name = "Sodick LQ1W",
//                manufacturer = "Sodick",
//                barcode = "Sodick LQ1W"
//            )
//        )
//
//        breakdownsRepository.save(Breakdown(
//            failure = "Ошибка короткого замыкания circuit shortage",
//            solution =
//                    " - вероятно, станок определяет короткое замыкание через падение напряжения в цепи\n" +
//                    " - возможно, какая-то грязь коротит станок, и обычная полная чистка должна помочь решить проблему\n" +
//                    " - станок полностью не чистили, но произвели замену части комплектующих (шлейфы?)\n" +
//                    " - это решило проблему circuit shortage, но проблема медленной подачи проволки осталась\n" +
//                    " - система ЧПУ была откатана до последней стабильной работы, при покупке станка или внесения изменения в ОС ЧПУ командой снимаются бекапы и хранятся на сервере организации\n" +
//                    " - после восстановление системы из бекапов полноценная работа была восстановлена",
//            dateTime = System.currentTimeMillis(),
//            machine = sodickSaved
//        ))
//
//        var fanucSaved = machineRepository.save(
//            Machine(
//                name = "Fanuc 2000",
//                manufacturer = "Fanuc",
//                barcode = "Fanuc 2000"
//            )
//        )
//    }
}