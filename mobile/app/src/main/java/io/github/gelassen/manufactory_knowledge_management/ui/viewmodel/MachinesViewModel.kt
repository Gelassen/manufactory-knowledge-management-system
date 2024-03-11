package io.github.gelassen.manufactory_knowledge_management.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.gelassen.manufactory_knowledge_management.App
import io.github.gelassen.manufactory_knowledge_management.model.Machine
import io.github.gelassen.manufactory_knowledge_management.network.model.Response
import io.github.gelassen.manufactory_knowledge_management.repository.MachinesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class Model(
    val machine: Machine? = null,
    val errors: List<String> = listOf<String>(),
    val isLoading: Boolean = false
)
class MachinesViewModel @Inject constructor(
    var machinesRepository: MachinesRepository
) : ViewModel() {

    private val state: MutableStateFlow<Model> = MutableStateFlow(Model())
    val uiState: StateFlow<Model> = state
        .asStateFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, state.value)

    suspend fun onStart(barcode: String) {
        Log.d(App.TAG, "[start] onStart($barcode)")
        state.update { state -> state.copy(isLoading = true) }
        val machine = machinesRepository.getCachedMachineByBarcode(barcode)
        state.update { state -> state.copy(machine = machine, isLoading = false) }
    }

    suspend fun fetchMachineByBarcode(barcode: String) {
        Log.d(App.TAG, "[start] fetchMachineByBarcode($barcode)")
        when(val response = machinesRepository.fetchMachineByBarcode(barcode)) {
            is Response.Error.Message -> {
                state.update { state ->
                    state.copy(
                        errors = state.errors.plus(response.msg),
                        isLoading = false
                    )
                }
            }
            else -> { /* no op */ }
        }
    }

}