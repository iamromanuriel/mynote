package com.roman.mynote.utils.resource

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ResourceProviderModule {

    @Singleton
    @Provides
    fun providerResourceImplement(
        @ApplicationContext context: Context
    ): IResourceProvider{
        return ResourceProvider(context)
    }
}