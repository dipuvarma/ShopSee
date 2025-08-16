package com.dipuguide.shopsee.di

import com.dipuguide.shopsee.data.repo.AuthRepoImpl
import com.dipuguide.shopsee.domain.repo.AuthRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepo(
        authRepoImpl: AuthRepoImpl,
    ): AuthRepo
}