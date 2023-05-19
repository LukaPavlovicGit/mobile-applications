package com.example.raftrading.modules

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // dependencies will live as long as application
object FirebaseModule {

    @Singleton
    @Provides
    @Named("Users")
    fun provideFirebaseDatabaseRefToUsers(@Named("Database") databaseRef: DatabaseReference):
            DatabaseReference = databaseRef.child("Users")

    @Provides
    @Singleton
    fun provideFirebaseAuthInstance():
            FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    @Named("Database")
    fun provideFirebaseDatabaseRef():
            DatabaseReference = FirebaseDatabase.getInstance("https://raftrading-8fc90-default-rtdb.europe-west1.firebasedatabase.app").reference

}