package com.supersapiens.jobfinder.job

import retrofit2.HttpException
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/** A Retrofit interface definition for the GitHub Jobs API. */
interface GitHubJobsService {
    /**
     * Fetches a page of job results.
     *
     * @param query Jobs must match this search query.
     * @param page The results page to return. Zero is the first page.
     * @return A list of jobs, which may be empty if there are no jobs
     *      or a page is requested that is beyond the final page.
     * @throws HttpException if the API request returns an error status code.
     */
    @GET("positions.json")
    suspend fun fetchJobs(
        @Query("description") query: String,
        @Query("page") page: Int = 0
    ): List<Job>

    /**
     * Fetches a single job by its unique ID.
     *
     * @param jobId The job ID.
     * @return The job.
     * @throws HttpException if the API request returns an error status code.
     */
    @GET("positions/{jobId}.json")
    suspend fun fetchJob(@Path("jobId") jobId: String): Job
}
