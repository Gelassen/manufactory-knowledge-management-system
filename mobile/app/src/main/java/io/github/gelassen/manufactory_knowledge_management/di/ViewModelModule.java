package io.github.gelassen.manufactory_knowledge_management.di;

import static io.github.gelassen.manufactory_knowledge_management.di.ViewModelModule.Names.MACHINE_VIEW_MODEL_FACTORY;

import androidx.lifecycle.ViewModel;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import io.github.gelassen.manufactory_knowledge_management.ui.viewmodel.MachinesViewModel;

@Module
public abstract class ViewModelModule {

    public static class Names {
        public static final String MACHINE_VIEW_MODEL_FACTORY = "MACHINE_VIEW_MODEL_FACTORY";
    }

    @Binds
    @Named(MACHINE_VIEW_MODEL_FACTORY)
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
