package com.github.ivan_osipov.clabo.api.model

import com.google.gson.annotations.SerializedName

open class Identifiable {
    @SerializedName("id")
    lateinit open var id: String

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Identifiable

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }


}