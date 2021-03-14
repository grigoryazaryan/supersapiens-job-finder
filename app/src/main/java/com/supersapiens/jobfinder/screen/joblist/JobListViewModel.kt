package com.supersapiens.jobfinder.screen.joblist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.supersapiens.jobfinder.job.GitHubJobsDataSource
import com.supersapiens.jobfinder.job.GitHubJobsService
import com.supersapiens.jobfinder.job.Job
import com.supersapiens.jobfinder.job.JobDao
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

// How long to wait for changes to the query string to settle down before
// fetching an updated jobs list.
// TODO: Use this to avoid making too many queries to the API when the
// user is typing in the search box.
private const val JOB_QUERY_DEBOUNCE_TIME = 300L // milliseconds

/**
 * The job list screen view model.
 *
 * @param jobDao A DAO for local job storage.
 * @param jobService A GitHub Jobs API client.
 */
class JobListViewModel @Inject constructor(
    private val jobDao: JobDao,
    private val jobService: GitHubJobsService
) : ViewModel() {
    /** The current job search query. */
    val query = MutableStateFlow("")

    /** Whether the user has a currently-active job search query. */
    // TODO: The user is searching if the query string is not blank.
    val isSearching = MutableLiveData(false)

    /**
     * A live paged list of the results of the current job search query.
     */
    // TODO: Use the current search query to create a data source
    // factory with the correct parameters.
    val searchedJobs = MutableLiveData<String>()
        .switchMap {
            GitHubJobsDataSource.Factory(jobService, it, viewModelScope)
                .toLiveData(50)
        }

    /** A live paged list of the locally-stored tracked jobs. */
    // TODO: Fetch the tracked jobs as a paged list from the job DAO.
    val trackedJobs = MutableLiveData<PagedList<Job>>()

    /**
     * Stop tracking a job.
     *
     * @param job The job.
     */
    fun stopTrackingJob(job: Job) {
        // TODO: Use the job DAO to delete the job by ID.
    }
}
