package com.sourashis.meesho.domain.useCase

import com.sourashis.meesho.common.ResultState
import com.sourashis.meesho.domain.models.CartDataModel
import com.sourashis.meesho.domain.repository.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val repo: Repo
) {

    fun addToCart(cartDataModel: CartDataModel): Flow<ResultState<String>> {
        return repo.addToCart(cartDataModel)
    }

}