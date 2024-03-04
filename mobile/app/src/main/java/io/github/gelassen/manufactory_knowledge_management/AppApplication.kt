package io.github.gelassen.manufactory_knowledge_management

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import io.github.gelassen.manufactory_knowledge_management.di.AppComponent
import io.github.gelassen.manufactory_knowledge_management.di.AppModule
import io.github.gelassen.manufactory_knowledge_management.di.DaggerAppComponent
import javax.inject.Inject

class AppApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector : DispatchingAndroidInjector<Any>

    protected lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        component = prepareAppComponent()
        component.inject(this)
    }

    protected open fun prepareAppComponent(): AppComponent {
        return DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }
}