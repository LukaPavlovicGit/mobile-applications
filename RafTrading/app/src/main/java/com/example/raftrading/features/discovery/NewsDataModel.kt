package com.example.raftrading.features.discovery

import com.google.gson.annotations.SerializedName

data class NewsModel (
    @SerializedName("data" ) var newsData : NewsData? = NewsData()
)

data class NewsData (
    @SerializedName("newsItems" ) var newsItems : ArrayList<NewsItems> = arrayListOf()
)

data class NewsItems (
    @SerializedName("title"   ) var title   : String? = null,
    @SerializedName("content" ) var content : String? = null,
    @SerializedName("link"    ) var link    : String? = null,
    @SerializedName("date"    ) var date    : String? = null,
    @SerializedName("image"   ) var image   : String? = null
)



