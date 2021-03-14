package com.supersapiens.jobfinder.screen.joblist

import com.supersapiens.jobfinder.job.Job

/**
 * Implementors of this interface can handle jobs that are selected from
 * a job list.
 */
fun interface OnSelectJobListener {
    /**
     * Handles jobs selected from the list.
     *
     * @param job The selected job.
     */
    fun onSelectJob(job: Job)
}
