package com.sourashis.meesho.domain.models

data class CategoryDataModel(
     var name: String = "",
     var date: Long = System.currentTimeMillis(),
     var createdBy: String = "",
     var categoryImage: String = "",
)
