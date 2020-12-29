package br.com.rentakelly.models

import com.google.gson.annotations.SerializedName

data class Pull (
    @SerializedName("id") val id: Int,
    @SerializedName("user") val user : Owner,
    @SerializedName("title") val title : String,
    @SerializedName("body") val description: String,

    )