package io.github.gelassen.manufactory_knowledge_management.services

import org.springframework.stereotype.Service

@Service
class QrService {

    fun generateQrValue(machineId: Long): String {
        return "https://your-domain.com/machines/$machineId"
    }
}