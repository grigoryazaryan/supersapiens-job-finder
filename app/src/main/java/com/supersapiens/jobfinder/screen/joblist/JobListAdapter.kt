package com.supersapiens.jobfinder.screen.joblist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.supersapiens.jobfinder.databinding.JobListItemBinding
import com.supersapiens.jobfinder.job.Job

/**
 * A paged list adapter for the jobs list.
 *
 * @param lifecycleOwner The lifecycle owner that owns this adapter.
 * @param jobListener A listener that handles when jobs are selected.
 */
class JobListAdapter(
        private val lifecycleOwner: LifecycleOwner,
        private val jobListener: OnSelectJobListener
) : PagedListAdapter<Job, JobListAdapter.JobViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Job>() {
            override fun areItemsTheSame(oldItem: Job, newItem: Job) =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Job, newItem: Job) =
                    oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ) = JobListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .let {
                JobViewHolder(it)
            }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class JobViewHolder(
            private val binding: JobListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        var job: Job? = null

        fun bind(job: Job?) {
            // TODO: Bind this view holder to the given job.
            binding.job = job
            this.job = job

            binding.root.setOnClickListener {
                job?.let {
                    jobListener.onSelectJob(it)
                }
            }

        }
    }
}
