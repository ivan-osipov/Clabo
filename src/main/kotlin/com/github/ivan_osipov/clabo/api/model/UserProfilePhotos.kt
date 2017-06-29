package com.github.ivan_osipov.clabo.api.model

import com.google.gson.annotations.SerializedName

class UserProfilePhotos {

    @SerializedName("total_count")
    private var _totalCount: Int? = null

    val totalCount: Int
        get() = _totalCount!!

    @SerializedName("photos")
    lateinit var photos: List<List<PhotoSize>>

}