package com.example.raftrading.dtos

import com.google.gson.annotations.SerializedName

data class Root (
    @SerializedName("data" ) var data : Data? = Data()
)

data class Data (
    @SerializedName("newsItems" ) var newsItems : ArrayList<NewsItems> = arrayListOf()
)

data class NewsItems (
    @SerializedName("title"   ) var title   : String? = null,
    @SerializedName("content" ) var content : String? = null,
    @SerializedName("link"    ) var link    : String? = null,
    @SerializedName("date"    ) var date    : String? = null,
    @SerializedName("image"   ) var image   : String? = null
)