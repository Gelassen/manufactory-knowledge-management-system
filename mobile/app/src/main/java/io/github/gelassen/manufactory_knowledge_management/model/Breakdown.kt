package io.github.gelassen.manufactory_knowledge_management.model

import com.google.gson.annotations.SerializedName

data class Breakdown(
    @SerializedName("id"             ) var id             : Int?                = null,
    @SerializedName("failure"        ) var failure        : String?             = null,
    @SerializedName("solution"       ) var solution       : String?             = null,
    @SerializedName("dateTime"       ) var dateTime       : Int?                = null,
    @SerializedName("photofixations" ) var photofixations : List<Photofixation> = emptyList()
)
