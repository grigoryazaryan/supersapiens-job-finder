package com.supersapiens.jobfinder.inject

import com.supersapiens.jobfinder.MainActivity
import com.supersapiens.jobfinder.screen.job.JobFragment
import com.supersapiens.jobfinder.screen.joblist.JobListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/** Contributed injectors for injected Android app components. */
@Module
abstract class ContributionsModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeJobListFragment(): JobListFragment

    @ContributesAndroidInjector
    abstract fun contributeJobFragment(): JobFragment
}
