package io.github.gelassen.manufactory_knowledge_management.storage.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import io.github.gelassen.manufactory_knowledge_management.model.Photofixation

//@Entity(tableName = )
@Entity(
    tableName = Schema.Photofixation.TABLE_NAME,
    foreignKeys = arrayOf(
        ForeignKey(
            entity = BreakdownEntity::class,
            parentColumns = arrayOf(Schema.Breakdown.UID), /* parentClassColumn */
            childColumns = arrayOf(Schema.Photofixation.BREAKDOWN_ID),
            onDelete = ForeignKey.CASCADE
        )
    )
)
data class PhotofixationEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Schema.Photofixation.UID)
    val id: Long,
    @ColumnInfo(name = Schema.Photofixation.PHOTO)
    val photo: String, /* image in base64 format */
    @ColumnInfo(name = Schema.Photofixation.BREAKDOWN_ID)
    val breakdownId: Long
)

fun PhotofixationEntity.toDomain(): Photofixation {
    return Photofixation(
        id = this.id,
        photo = this.photo
    )
}
