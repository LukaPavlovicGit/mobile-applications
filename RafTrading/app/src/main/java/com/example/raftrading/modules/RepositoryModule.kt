package com.example.raftrading.modules

import android.content.SharedPreferences
import com.example.raftrading.application.SharedPreferencesManager
import com.example.raftrading.dataSource.UserRepository
import com.example.raftrading.dataSource.firebaseDB.UserRepositoryFirebaseImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.mlkit.common.sdkinternal.SharedPrefManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        auth: FirebaseAuth,
        @Named("Users")  usersRoot: DatabaseReference
    ): UserRepository = UserRepositoryFirebaseImpl(auth, usersRoot)


}