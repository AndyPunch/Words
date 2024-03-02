package com.example.words.di

import android.content.Context
import androidx.room.Room
import com.example.words.data.db.PersistentDatabase
import com.example.words.data.helpers.IPreferenceDataStoreAPI
import com.example.words.data.helpers.PreferenceDataStoreHelper
import com.example.words.navigation.Navigator
import com.example.words.utils.AppConstants.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferenceDataStoreModule {

    @Singleton
    @Provides
    fun providesPreferenceDataStoreHelper(preferenceDataStoreHelper: PreferenceDataStoreHelper): IPreferenceDataStoreAPI {
        return preferenceDataStoreHelper
    }

    @Provides
    @Singleton
    fun providePersistentDb(@ApplicationContext context: Context): PersistentDatabase {
        return Room.databaseBuilder(context, PersistentDatabase::class.java, DB_NAME)
            .build()
    }

    @Provides
    @Singleton
    fun provideWordsDao(db: PersistentDatabase) = db.wordsDao()


    @Provides
    @Singleton
    fun provideNavigator(): Navigator {
        return Navigator
    }


}