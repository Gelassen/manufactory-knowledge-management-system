package io.github.gelassen.manufactory_knowledge_management.storage.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import io.github.gelassen.manufactory_knowledge_management.model.Breakdown

@Entity
data class BreakdownAndPhotos (
    @Embedded
    val breakdown: BreakdownEntity,
    @Relation(
        parentColumn = Schema.Breakdown.UID,
        entityColumn = Schema.Photofixation.BREAKDOWN_ID
    )
    val photos: List<PhotofixationEntity>
)

fun BreakdownAndPhotos.toDomain(): Breakdown {
    return Breakdown(
        id = breakdown.id,
        failure = breakdown.failure,
        solution = breakdown.solution,
        date = breakdown.date,
        photos = photos.map { it.toDomain() }
    )
}