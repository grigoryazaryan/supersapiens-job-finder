package com.supersapiens.jobfinder

import androidx.room.Database
import androidx.room.RoomDatabase
import com.supersapiens.jobfinder.job.Job
import com.supersapiens.jobfinder.job.JobDao

@Database(entities = [Job::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jobDao(): JobDao
}
