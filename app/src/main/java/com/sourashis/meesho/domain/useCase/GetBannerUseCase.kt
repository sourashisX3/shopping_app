package com.sourashis.meesho.domain.useCase

import com.sourashis.meesho.common.ResultState
import com.sourashis.meesho.domain.models.BannersDataModel
import com.sourashis.meesho.domain.repository.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetBannerUseCase @Inject constructor(
    private val repo: Repo
) {

    fun getBannerUseCase(): Flow<ResultState<List<BannersDataModel>>> {
        return repo.getBanners()
    }

}