package com.rachitbhutani.kalendar.di

import com.rachitbhutani.kalendar.tasks.TaskService
import com.rachitbhutani.kalendar.tasks.data.TaskRepository
import com.rachitbhutani.kalendar.tasks.data.TaskRepositoryImpl
import com.rachitbhutani.kalendar.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provideRetrofit() = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.BASE_URL).build()

    @Provides
    fun provideTaskService(retrofit: Retrofit) = retrofit.create(TaskService::class.java)

    @Provides
    fun provideTaskRepository(service: TaskService): TaskRepository {
        return TaskRepositoryImpl(service)
    }

}