package com.supersapiens.jobfinder.job

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * This class is a data source for the Android paging library that
 * pulls pages of jobs from the GitHub Jobs API.
 *
 * @param jobsService A client for the GitHub Jobs API.
 * @param query Jobs must match this search string.
 * @param scope A coroutine scope used to launch job requests.
 */
class GitHubJobsDataSource(
    private val jobsService: GitHubJobsService,
    private val query: String,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, Job>() {
    @Singleton
    class Factory @Inject constructor(
        private val jobsService: GitHubJobsService,
        private val query: String,
        private val scope: CoroutineScope
    ) : DataSource.Factory<Int, Job>() {
        override fun create() =
            GitHubJobsDataSource(jobsService, query, scope)
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Job>
    ) {
        // The API pages are numbered starting from zero.
        loadPage(0) { jobs ->
            callback.onResult(jobs, null, 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Job>) {
        loadPage(params.key) { jobs ->
            val previousPage = if (params.key == 0)
                null
            else
                params.key - 1

            callback.onResult(jobs, previousPage)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Job>) {
        loadPage(params.key) { jobs ->
            val nextPage = if (jobs.isEmpty())
                null
            else
                params.key + 1

            callback.onResult(jobs, nextPage)
        }
    }


    // Requests a single page of jobs from the API.
    private inline fun loadPage(
        page: Int,
        crossinline handlePage: (List<Job>) -> Unit
    ) {
        // TODO: Fetch the requested page of jobs from the API. Gracefully
        // handle any exceptions by invalidating the data source.
        scope.launch {
            try {
                val jobs = jobsService.fetchJobs(query, page)
                handlePage(jobs)
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }
}
