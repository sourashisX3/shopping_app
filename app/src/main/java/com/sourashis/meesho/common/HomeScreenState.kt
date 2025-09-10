package com.sourashis.meesho.common

import com.sourashis.meesho.domain.models.BannersDataModel
import com.sourashis.meesho.domain.models.CategoryDataModel
import com.sourashis.meesho.domain.models.ProductsDataModel

data class HomeScreenState(

    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val categories: List<CategoryDataModel>? = null,
    val products: List<ProductsDataModel>? = null,
    val banners: List<BannersDataModel>? = null

)