package com.roman.mynote.data.database

import android.content.Context
import androidx.room.Room
import com.roman.mynote.utils.constant.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun bindDatabase(@ApplicationContext context: Context): Database{
        return Room.databaseBuilder(
            context,
            Database::class.java,
            Constants.DATABASENAME
        )   .fallbackToDestructiveMigration()
            .build()
    }

}