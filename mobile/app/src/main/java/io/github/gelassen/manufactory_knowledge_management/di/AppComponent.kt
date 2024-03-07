package io.github.gelassen.manufactory_knowledge_management.di

import dagger.Component
import dagger.android.AndroidInjector
import io.github.gelassen.manufactory_knowledge_management.AppApplication
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [],
    modules = [
        AppModule::class,
        TestViewModelModule::class,
        InjectorModule::class
    ]
)
interface AppComponent {

    fun inject(subject: AppApplication)

}