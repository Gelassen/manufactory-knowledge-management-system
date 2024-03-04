package io.github.gelassen.manufactory_knowledge_management.di

import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import io.github.gelassen.manufactory_knowledge_management.MainActivity

@Module(
    includes = [
        AndroidSupportInjectionModule::class,
        AndroidInjectionModule::class
    ]
)
abstract class InjectorModule {

    @ContributesAndroidInjector
    abstract fun provideMainActivityInjector() : MainActivity

}