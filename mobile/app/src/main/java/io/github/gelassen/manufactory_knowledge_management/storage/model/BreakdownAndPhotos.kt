package io.github.gelassen.manufactory_knowledge_management.storage.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class BreakdownAndPhotos (
    @Embedded
    val breakdown: BreakdownEntity,
    @Relation(
        parentColumn = Schema.Breakdown.UID,
        entityColumn = Schema.Photofixation.BREAKDOWN_ID
    )
    val photos: List<Schema.Photofixation>
)