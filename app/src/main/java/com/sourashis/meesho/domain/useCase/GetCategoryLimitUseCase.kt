package com.sourashis.meesho.domain.useCase

import com.sourashis.meesho.common.ResultState
import com.sourashis.meesho.domain.models.CategoryDataModel
import com.sourashis.meesho.domain.repository.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoryLimitUseCase @Inject constructor(
    private val repo: Repo
) {

    fun getCategoryLimited(): Flow<ResultState<List<CategoryDataModel>>> {
        return repo.getCategoriesInLimited()
    }

}