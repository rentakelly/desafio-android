package br.com.rentakelly.models

import com.google.gson.annotations.SerializedName

data class Repos(
    @SerializedName("items") val items: List<Repo>
)
