package com.sourashis.meesho.domain.useCase

import com.sourashis.meesho.common.ResultState
import com.sourashis.meesho.domain.models.ProductsDataModel
import com.sourashis.meesho.domain.repository.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllProductUseCase @Inject constructor(
    private val repo: Repo
) {

    fun getAllProducts(): Flow<ResultState<List<ProductsDataModel>>> {
        return repo.getAllProducts()
    }

}