package com.supersapiens.jobfinder.screen.joblist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.supersapiens.jobfinder.R
import com.supersapiens.jobfinder.databinding.FragmentJobListBinding
import com.supersapiens.jobfinder.inject.activityViewModelProvider
import com.supersapiens.jobfinder.job.Job
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import javax.inject.Provider

/** The job list screen. */
class JobListFragment : DaggerFragment() {
    private lateinit var binding: FragmentJobListBinding

    @Inject lateinit var modelProvider: Provider<JobListViewModel>
    private val model by activityViewModelProvider { modelProvider }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentJobListBinding
        .inflate(inflater, container, false)
        .let {
            binding = it
            it.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // The paged list adapter cannot resolve diffs between the
        // tracked job list and the search results list, so set up a
        // different adapter for each and swap between them depending
        // on whether the user is currently searching or not.
        val searchedAdapter = createAdapter(model.searchedJobs)
        val trackedAdapter = createAdapter(model.trackedJobs)
        model.isSearching.observe(viewLifecycleOwner) {
            // TODO: Swap recycler view adapters depending on whether
            // the user is searching or not. Enable swiping left on the
            // swipe callback only when displaying tracked jobs.
        }

        binding.jobs.setHasFixedSize(true)

        ItemTouchHelper(swipeCallback).attachToRecyclerView(binding.jobs)

        val searchView = binding.toolbar.menu
            ?.findItem(R.id.search)
            ?.actionView as? SearchView
            ?: return

        searchView.apply {
            queryHint = getString(R.string.search_jobs)

            setOnQueryTextListener(
                object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        model.query.value = query
                        return true
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        model.query.value = newText
                        return true
                    }
                }
            )

            setOnCloseListener {
                model.query.value = ""
                false
            }
        }
    }

    // Creates a job list adapter associated with a given live list of jobs.
    private fun createAdapter(source: LiveData<PagedList<Job>>): JobListAdapter {
        val adapter = JobListAdapter(viewLifecycleOwner) { job ->
            // TODO: Navigate to the job detail screen (JobFragment).
        }

        source.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        return adapter
    }

    // Handles swipe gestures on job list items.
    private val swipeCallback = object : ItemTouchHelper.SimpleCallback(0, 0) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ) = false

        override fun onSwiped(
            viewHolder: RecyclerView.ViewHolder,
            direction: Int
        ) {
            // TODO: Stop tracking swiped jobs.
        }
    }
}
