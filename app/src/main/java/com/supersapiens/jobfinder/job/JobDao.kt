package com.supersapiens.jobfinder.job

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/** A Room DAO for accessing locally stored [Job] data. */
@Dao
interface JobDao {
    /**
     * Deletes a single job using its unique ID.
     *
     * @param id The job ID.
     */
    @Query("DELETE FROM Job WHERE id = :id")
    suspend fun deleteById(id: String)

    /**
     * Gets an Android paging library data source factory that can back
     * a paged list of all jobs.
     *
     * @return The job data source factory.
     */
    @Query("SELECT * FROM Job ORDER BY company, title")
    fun getTrackedDataSource(): DataSource.Factory<Int, Job>

    /**
     * Finds a [Flow] of a single job using its unique ID. The job will
     * be re-fetched and emitted whenever the database changes.
     *
     * @param id The job ID.
     * @return A flow that emits the requested job.
     */
    @Query("SELECT * FROM Job WHERE id = :id")
    fun findById(id: String): Flow<Job?>

    /**
     * Saves a job, replacing it if it already exists.
     *
     * @param job The job.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(job: Job)
}
