package io.github.gelassen.manufactory_knowledge_management.di

import dagger.Component
import io.github.gelassen.manufactory_knowledge_management.AppApplication
import io.github.gelassen.manufactory_knowledge_management.MainActivity
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [],
    modules = [
        AppModule::class,
        ViewModelModule::class,
        InjectorModule::class
    ]
)
interface AppComponent {

    fun inject(subject: AppApplication)

}