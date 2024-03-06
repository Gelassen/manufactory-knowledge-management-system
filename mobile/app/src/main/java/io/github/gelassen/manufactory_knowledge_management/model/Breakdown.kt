package io.github.gelassen.manufactory_knowledge_management.model

import com.google.gson.annotations.SerializedName

data class Breakdown(
    @SerializedName("id"             ) var id             : Long?               = null,
    @SerializedName("failure"        ) var failure        : String?             = null,
    @SerializedName("solution"       ) var solution       : String?             = null,
    @SerializedName("dateTime"       ) var date           : Long?                = null,
    @SerializedName("photofixations" ) var photos         : List<Photofixation> = emptyList()
)
