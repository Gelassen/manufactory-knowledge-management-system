package io.github.gelassen.manufactory_knowledge_management.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import io.github.gelassen.manufactory_knowledge_management.R

class MachinesFragment : Fragment() {

    companion object {
        private val NAMESPACE = MachinesFragment::class.qualifiedName.toString()
        const val MACHINE_ID = "MACHINE_ID"
        /**
         * External keys should be maintained unique
         *
         * @link https://developer.android.com/guide/fragments/communicate#pass-between-fragments
         * */
        val EXTRA_MACHINE_ID = NAMESPACE.plus(MACHINE_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_machines, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener(EXTRA_MACHINE_ID) { requestKey, bundle ->
            val machineIdFromBarcode = bundle.getString(MACHINE_ID)
            Toast.makeText(requireContext(), "Receive machine id ${machineIdFromBarcode}", Toast.LENGTH_SHORT)
                .show()
        }
    }
}