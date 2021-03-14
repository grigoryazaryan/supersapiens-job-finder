package com.supersapiens.jobfinder.inject

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Provider

inline fun <reified VM : ViewModel> ComponentActivity.viewModelFactory(
    noinline acquireValue: () -> VM
) = viewModels<VM> { InjectedViewModelFactory(acquireValue) }

inline fun <reified VM : ViewModel> ComponentActivity.viewModelProvider(
    noinline acquireProvider: () -> Provider<VM>
) = viewModels<VM> {
    InjectedViewModelFactory { acquireProvider().get() }
}

inline fun <reified VM : ViewModel> Fragment.activityViewModelProvider(
    noinline acquireProvider: () -> Provider<VM>
) = activityViewModels<VM> {
    InjectedViewModelFactory { acquireProvider().get() }
}

inline fun <reified VM : ViewModel> Fragment.viewModelProvider(
    noinline acquireProvider: () -> Provider<VM>
) = viewModels<VM> {
    InjectedViewModelFactory { acquireProvider().get() }
}

inline fun <reified VM : ViewModel> Fragment.activityViewModelFactory(
    noinline acquireValue: () -> VM
) = activityViewModels<VM> { InjectedViewModelFactory(acquireValue) }

inline fun <reified VM : ViewModel> Fragment.viewModelFactory(
    noinline acquireValue: () -> VM
) = viewModels<VM> { InjectedViewModelFactory(acquireValue) }

class InjectedViewModelFactory(
    private val acquireValue: () -> Any
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        acquireValue() as T
}
