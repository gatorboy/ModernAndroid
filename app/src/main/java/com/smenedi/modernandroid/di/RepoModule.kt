package com.smenedi.modernandroid.di

import com.smenedi.modernandroid.data.repository.GitRepositoryImpl
import com.smenedi.modernandroid.domain.GitRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
interface RepoModule {
    @Binds
    fun getGitRepository(repository: GitRepositoryImpl): GitRepository
}
