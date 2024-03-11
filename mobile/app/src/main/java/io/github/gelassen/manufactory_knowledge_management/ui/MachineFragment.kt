package io.github.gelassen.manufactory_knowledge_management.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.android.support.AndroidSupportInjection
import io.github.gelassen.manufactory_knowledge_management.App
import io.github.gelassen.manufactory_knowledge_management.R
import io.github.gelassen.manufactory_knowledge_management.di.ViewModelFactory
import io.github.gelassen.manufactory_knowledge_management.di.ViewModelModule
import io.github.gelassen.manufactory_knowledge_management.ui.viewmodel.MachinesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class MachineFragment : Fragment() {

    companion object {
        private val NAMESPACE = MachineFragment::class.qualifiedName.toString()
        const val MACHINE_ID = "MACHINE_ID"
        /**
         * External keys should be maintained unique
         *
         * @link https://developer.android.com/guide/fragments/communicate#pass-between-fragments
         * */
        val EXTRA_MACHINE_ID = NAMESPACE.plus(MACHINE_ID)
    }

    @Inject
    @Named(ViewModelModule.Names.MACHINE_VIEW_MODEL_FACTORY)
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: MachinesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MachinesViewModel::class.java)
        return inflater.inflate(R.layout.fragment_machines, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener(EXTRA_MACHINE_ID) { requestKey, bundle ->
            val machineIdFromBarcode = bundle.getString(MACHINE_ID)
            Log.d(App.TAG, "Receive machine id $machineIdFromBarcode")
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.fetchMachineByBarcode(machineIdFromBarcode.toString())
                    }
                }

                launch {
                    viewModel.onStart(machineIdFromBarcode.toString())
                }
            }
        }
        subscribeOnUpdates()
    }

    private fun subscribeOnUpdates() {
        lifecycleScope.launch {
            viewModel.uiState.collectLatest { it ->
                Log.d(App.TAG, "Machine by barcode ${it.machine?.name ?: "machine is null"}")
            }
        }
    }

    private suspend fun fetchMachinesByBarcode(machineId: String) {
/*        lifecycleScope.launch {
            // TODO complete me
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {

            }

        }*/
    }
}