package com.supersapiens.jobfinder.screen.job

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.supersapiens.jobfinder.R
import com.supersapiens.jobfinder.databinding.FragmentJobBinding
import com.supersapiens.jobfinder.inject.viewModelFactory
import com.supersapiens.jobfinder.job.JobDao
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/** The job detail screen. */
class JobFragment : DaggerFragment() {
    private lateinit var binding: FragmentJobBinding

    private val args by navArgs<JobFragmentArgs>()

    @Inject lateinit var modelFactory: JobViewModel.Factory

    @Inject lateinit var jobDao: JobDao

    private val model by viewModelFactory {
        modelFactory.create(args.jobId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentJobBinding
        .inflate(inflater, container, false)
        .let {
            binding = it
            it.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.let {
            it.lifecycleOwner = viewLifecycleOwner
            it.model = model

            it.toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            it.toolbar.setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.tracking) {
                    // TODO: Toggle tracking of this job.

                        lifecycleScope.launch {
                            model.toggleTracking()
                        }
                    true
                } else {
                    false
                }
            }
        }

        model.trackingIcon.observe(viewLifecycleOwner) { icon ->
            context?.let {
                binding.toolbar.menu
                    .findItem(R.id.tracking)
                    ?.icon = ContextCompat.getDrawable(it, icon)
            }
        }

        lifecycleScope.launch {
            val params = TextViewCompat.getTextMetricsParams(
                binding.description
            )

            model.computeDescription(params).collect {
                binding.description.text = it
            }
        }
    }
}
