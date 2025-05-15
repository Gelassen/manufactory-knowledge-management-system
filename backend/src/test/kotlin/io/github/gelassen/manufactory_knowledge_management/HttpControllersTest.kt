package io.github.gelassen.manufactory_knowledge_management

import com.ninjasquad.springmockk.MockkBean
import io.github.gelassen.manufactory_knowledge_management.model.Breakdown
import io.github.gelassen.manufactory_knowledge_management.model.Machine
import io.github.gelassen.manufactory_knowledge_management.repository.CustomBreakdownsRepository
import io.github.gelassen.manufactory_knowledge_management.repository.MachinesRepository
import io.github.gelassen.manufactory_knowledge_management.services.MachineService
import io.mockk.every
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
class HttpControllersTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    lateinit var machinesRepository: MachinesRepository

    @MockBean
    private lateinit var machineService: MachineService

    @MockkBean
    lateinit var breakdownsRepository: CustomBreakdownsRepository

    @Test
    @Disabled("Temporally disabled due migration from repository to service")
    fun `on GET request machines?barcode=valid_barcode receives valid response`() {
        val stubApiResponses = stubApiResponses()
        every { machinesRepository.findMachineByBarcode(any()) } returns stubApiResponses.first()
        every { machineService.getMachineByBarcode(any()) } returns stubApiResponses.first()

        mockMvc.perform(get("/api/v1/machine?barcode=Sodick LQ1W").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.data.name").value(stubApiResponses.first().name))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.data.breakdowns[0].failure").value(stubApiResponses.first().breakdowns.first().failure))
    }

    @Test
    @Disabled("Temporally disabled due migration from repository to service")
    fun `on GET request machines?barcode=not_valid_barcode receives NOT_FOUND response`() {
        var validBarcode = "Sodick LQ1W"
        var notValidBarcode = "Sodick which does not exist"
        val stubApiResponses = stubApiResponses()
        every { machinesRepository.findMachineByBarcode(validBarcode) } returns stubApiResponses.first()
        every { machinesRepository.findMachineByBarcode(notValidBarcode) } returns null
        every { machineService.getMachineByBarcode(validBarcode) } returns stubApiResponses.first()
        every { machineService.getMachineByBarcode(notValidBarcode) } returns null

        mockMvc.perform(get("/api/v1/machine?barcode=$notValidBarcode").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").value("Machine with '$notValidBarcode' barcode does not exist. Did you send the right barcode?"))
    }

    private fun stubApiResponses(): List<Machine> {
        var sodick = Machine(
            name = "Sodick LQ1W",
            manufacturer = "Sodick",
            barcode = "Sodick LQ1W"
        )
        sodick.breakdowns.add(
            Breakdown(
                title = "Ошибка короткого замыкания circuit shortage",
                failure = " - вероятно, станок определяет короткое замыкание через падение напряжения в цепи\n" +
                        " - возможно, какая-то грязь коротит станок, и обычная полная чистка должна помочь решить проблему\n" +
                        " - станок полностью не чистили, но произвели замену части комплектующих (шлейфы?)\n" +
                        " - это решило проблему circuit shortage, но проблема медленной подачи проволки осталась\n" +
                        " - система ЧПУ была откатана до последней стабильной работы, при покупке станка или внесения изменения в ОС ЧПУ командой снимаются бекапы и хранятся на сервере организации\n" +
                        " - после восстановление системы из бекапов полноценная работа была восстановлена",
                solution = "Заменили шлейфы и восстановили ОС из бэкапов",
                dateTime = System.currentTimeMillis(),
                machine = sodick
            )
        )

        var fanuc = Machine(
            name = "Fanuc 2000",
            manufacturer = "Fanuc",
            barcode = "Fanuc 2000"
        )

        return listOf(sodick, fanuc)
    }
}