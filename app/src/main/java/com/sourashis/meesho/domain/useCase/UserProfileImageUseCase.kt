package com.sourashis.meesho.domain.useCase

import android.net.Uri
import com.sourashis.meesho.common.ResultState
import com.sourashis.meesho.domain.repository.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserProfileImageUseCase @Inject constructor(
    private val repo: Repo
) {

    fun userProfileImageUseCase(uri: Uri): Flow<ResultState<String>> {
        return repo.userProfileImage(uri)
    }

}