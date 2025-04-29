package io.github.gelassen.manufactory_knowledge_management.model.request

import io.github.gelassen.manufactory_knowledge_management.model.Breakdown
import io.github.gelassen.manufactory_knowledge_management.model.Photofixation

data class PhotofixationDTO(
    val photo: String // Base64-encoded string
)

fun PhotofixationDTO.toEntity(breakdown: Breakdown): Photofixation {
    return Photofixation(
        photo = this.photo,
        breakdown = breakdown
    )
}
