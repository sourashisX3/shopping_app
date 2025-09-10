package com.sourashis.meesho.domain.useCase

import com.sourashis.meesho.common.ResultState
import com.sourashis.meesho.domain.models.UserDataParent
import com.sourashis.meesho.domain.repository.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateUserDataUseCase @Inject constructor(
    private val repo: Repo
) {

    fun updateUserData(userDataParent: UserDataParent): Flow<ResultState<String>> {
        return repo.updateUserData(userDataParent)
    }

}