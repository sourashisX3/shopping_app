package com.sourashis.meesho.domain.models

data class BannersDataModel(
     val name: String = "",
     val image: String = "",
     val date: Long = System.currentTimeMillis()
)
