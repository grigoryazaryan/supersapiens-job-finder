package com.supersapiens.jobfinder.inject

import android.app.Application
import com.supersapiens.jobfinder.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/** The top level Dagger component used to inject the application. */
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ContributionsModule::class
    ]
)
@Singleton
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder
        fun build(): AppComponent
    }

    fun inject(app: App)
}
