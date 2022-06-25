package com.nikhilchauhan.locationtracker.injection

import android.content.Context
import com.nikhilchauhan.locationtracker.datastore.PrefDataStoreManager
import com.nikhilchauhan.locationtracker.repository.LocationRepository
import com.nikhilchauhan.locationtracker.repository.LocationRepositoryImpl
import com.nikhilchauhan.locationtracker.utils.FileUtils
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

  @Provides
  @Singleton
  fun providePrefDataStoreManager(@ApplicationContext context: Context): PrefDataStoreManager =
    PrefDataStoreManager(context)

  @Provides
  @Singleton
  fun provideFileUtils(@ApplicationContext context: Context): FileUtils =
    FileUtils(context)
}