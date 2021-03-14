package com.supersapiens.jobfinder.screen.job

import android.text.PrecomputedText
import androidx.core.text.PrecomputedTextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.supersapiens.jobfinder.R
import com.supersapiens.jobfinder.job.GitHubJobsService
import com.supersapiens.jobfinder.job.Job
import com.supersapiens.jobfinder.job.JobDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject
import javax.inject.Singleton

/**
 * The job detail screen view model.
 *
 * @param jobId The unique ID of the displayed job.
 * @param jobDao A DAO for local job storage.
 * @param jobService A GitHub Jobs API client.
 */
class JobViewModel(
    private val jobId: String,
    private val jobDao: JobDao,
    private val jobService: GitHubJobsService
) : ViewModel() {
    @Singleton
    class Factory @Inject constructor(
        private val jobDao: JobDao,
        private val jobService: GitHubJobsService
    ) {
        fun create(jobId: String) = JobViewModel(jobId, jobDao, jobService)
    }

    // A flow of the locally-stored data for this job.
    private val trackedJob = jobDao
        .findById(jobId)
        .shareIn(viewModelScope, SharingStarted.Lazily, replay = 1)

    // A flow that emits locally-stored data for a job if possible, then
    // fetches and emits updated data for the job from the network.
    private val job = flow<Job?> {
        // TODO: Emit the locally-cached job only if it exists, then
        // fetch and emit the current job from the API.
    }.shareIn(viewModelScope, SharingStarted.Lazily, replay = 1)

    /** A [LiveData] containing the most recently-received data for this job. */
    // TODO: Provide a LiveData version of the `job`
    val liveJob = MutableLiveData<Job?>()

    /**
     * The drawable resource to display representing the tracking status
     * of this job.
     */
    val trackingIcon = trackedJob.map {
        if (it != null)
            R.drawable.favorite_filled
        else
            R.drawable.favorite_outline
    }.distinctUntilChanged().asLiveData()

    /**
     * Computes [PrecomputedText] for the job description HTML off the
     * main thread.
     *
     * @param params The precomputed text parameters for the text view
     *      that will display the description.
     * @return The precomputed description text.
     */
    // TODO: Parse and precompute the description HTML off the main thread.
    fun computeDescription(params: PrecomputedTextCompat.Params) =
        emptyFlow<PrecomputedTextCompat>()

    /** Toggles whether this job is tracked or not. */
    suspend fun toggleTracking() {
        // TODO: Toggle whether the job is tracked or not by storing and
        // deleting it from the database.
    }

    // Fetches updated data for this job from the API.
    // TODO: Fetch the job from the job service. If the job is currently
    // tracked, update the locally-cached data with the fetched data.
    // Catch any 404 errors that occur and return null. Rethrow any other
    // HTTP exceptions.
    private suspend fun fetchJob(): Job? = null
}
