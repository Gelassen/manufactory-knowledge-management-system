package io.github.gelassen.manufactory_knowledge_management.di;

import androidx.lifecycle.ViewModel;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import io.github.gelassen.manufactory_knowledge_management.di.ViewModelFactory;
import io.github.gelassen.manufactory_knowledge_management.di.ViewModelKey;
import io.github.gelassen.manufactory_knowledge_management.ui.viewmodel.MachinesViewModel;

@Module
public abstract class TestViewModelModule {

    @Binds
    @Named(ViewModelModule.MACHINE_VIEW_MODEL_FACTORY)
    public abstract ViewModelFactory bindViewModelFactory(ViewModelFactory viewModelFactory);

    /*
        you have to explicitly define ViewModel instead of concrete
        implementation to avoid cycle dependency error
    */
    @Binds
    @IntoMap
    @ViewModelKey(MachinesViewModel.class)
    abstract ViewModel bindMachinesViewModel(MachinesViewModel vm);
}
