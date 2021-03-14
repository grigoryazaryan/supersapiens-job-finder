package com.supersapiens.jobfinder.inject

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.supersapiens.jobfinder.AppDatabase
import com.supersapiens.jobfinder.BuildConfig
import com.supersapiens.jobfinder.job.GitHubJobsService
import com.supersapiens.jobfinder.job.JobDao
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/** A Dagger module that provides application-wide resources. */
@Module
class AppModule {
    @Provides
    fun provideApplicationContext(app: Application): Context = app

    @Singleton
    @Provides
    fun provideDatabase(applicationContext: Context): AppDatabase =
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "job-finder"
        ).build()

    @Singleton
    @Provides
    fun provideGitHubJobsService(): GitHubJobsService =
        Retrofit.Builder()
            .baseUrl(BuildConfig.JOBS_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(GitHubJobsService::class.java)

    @Singleton
    @Provides
    fun provideJobDao(database: AppDatabase): JobDao = database.jobDao()
}
