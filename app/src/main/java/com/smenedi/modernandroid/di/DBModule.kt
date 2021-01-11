package com.smenedi.modernandroid.di

import android.content.Context
import com.smenedi.modernandroid.data.database.GitDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ApplicationComponent::class)
@Module
object DBModule {
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): GitDatabase = GitDatabase.getInstance(context)
}
