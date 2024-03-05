package io.github.gelassen.manufactory_knowledge_management.model

import com.google.gson.annotations.SerializedName
import io.github.gelassen.manufactory_knowledge_management.storage.model.MachineAndBreakdowns
import io.github.gelassen.manufactory_knowledge_management.storage.model.MachineEntity

data class Machine(
    @SerializedName("id"           ) var id           : Long?                 = null,
    @SerializedName("name"         ) var name         : String?               = null,
    @SerializedName("manufacturer" ) var manufacturer : String?               = null,
    @SerializedName("barcode"      ) var barcode      : String?               = null,
    @SerializedName("breakdowns"   ) var breakdowns   : List<Breakdown>       = emptyList()
)

fun Machine.fromDomain(): MachineEntity {
    return MachineEntity(
        id = this.id!!,
        name = this.name!!,
        manufacturer = this.manufacturer!!,
        barcode = this.barcode!!
    )
}
