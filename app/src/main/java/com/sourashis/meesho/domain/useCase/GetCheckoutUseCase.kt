package com.sourashis.meesho.domain.useCase

import com.sourashis.meesho.common.ResultState
import com.sourashis.meesho.domain.models.ProductsDataModel
import com.sourashis.meesho.domain.repository.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCheckoutUseCase @Inject constructor(
    private val repo: Repo
) {

    fun getCheckoutUseCase(productId: String): Flow<ResultState<ProductsDataModel>> {
        return repo.getCheckout(productId)
    }

}