package com.sourashis.meesho.domain.useCase

import com.sourashis.meesho.common.ResultState
import com.sourashis.meesho.domain.models.UserData
import com.sourashis.meesho.domain.repository.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val repo: Repo
) {

    fun createUser(userData: UserData): Flow<ResultState<String>> {
        return repo.registerUserWithEmailAndPassword(userData)
    }

}