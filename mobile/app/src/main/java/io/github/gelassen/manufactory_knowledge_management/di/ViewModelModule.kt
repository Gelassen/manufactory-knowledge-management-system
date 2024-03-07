package io.github.gelassen.manufactory_knowledge_management.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.github.gelassen.manufactory_knowledge_management.ui.viewmodel.MachinesViewModel
import javax.inject.Singleton

@Module
abstract class ViewModelModule : ViewModel() {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MachinesViewModel::class)
    abstract fun bindMachinesViewModel(vm: MachinesViewModel) : MachinesViewModel
}