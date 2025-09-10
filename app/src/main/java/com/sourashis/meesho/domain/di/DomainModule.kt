package com.sourashis.meesho.domain.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sourashis.meesho.data.repository.RepoImpl
import com.sourashis.meesho.domain.repository.Repo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun providesRepo(firebaseAuth: FirebaseAuth, firebaseFirestore: FirebaseFirestore): Repo {
        return RepoImpl(firebaseAuth, firebaseFirestore)
    }

}