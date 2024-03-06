package io.github.gelassen.manufactory_knowledge_management.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.gelassen.manufactory_knowledge_management.model.Machine
import io.github.gelassen.manufactory_knowledge_management.storage.repositories.MachinesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class Model(
    val machines: MutableList<Machine> = mutableListOf(),
    val errors: MutableList<String> = mutableListOf(),
    val isLoading: Boolean = false
)
class MachinesViewModel @Inject constructor(
    var machinesRepository: MachinesRepository
) : ViewModel() {

    private val state: MutableStateFlow<Model> = MutableStateFlow(Model())
    val uiState: StateFlow<Model> = state
        .asStateFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, state.value)

    suspend fun fetchMachinesByBarcode(machineId: String) {
        machinesRepository.getMachineByUniqueIdentifier(machineId)

    }

}