package com.mariusz.dogapp.data.di

import com.mariusz.dogapp.data.DefaultDogRepository
import com.mariusz.dogapp.data.DogRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Singleton
    @Binds
    fun bindsDogRepository(
        dogRepository: DefaultDogRepository
    ): DogRepository
}