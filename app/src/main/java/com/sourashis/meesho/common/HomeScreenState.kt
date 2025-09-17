package com.sourashis.meesho.common

import com.sourashis.meesho.domain.models.BannersDataModel
import com.sourashis.meesho.domain.models.CategoryDataModel

data class HomeScreenState(

    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val categories: List<CategoryDataModel>? = null,
    val products: List<CategoryDataModel>? = null,
    val banners: List<BannersDataModel>? = null

)