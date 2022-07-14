package com.example.trabalho

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleCursorAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import com.example.trabalho.databinding.FragmentEditarServiceBinding
import com.google.android.material.snackbar.Snackbar

class FragmentEditarService : Fragment(){
    private var _binding: FragmentEditarServiceBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var service: Services? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarServiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_edicao

        if (arguments != null) {
            service = FragmentEditarServiceArgs.fromBundle(arguments!!).services

            if (service != null) {
                binding.editTextTypeService.setText(service!!.service_type)
            }
        }
    }



    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_guardar -> {
                guardar()
                true
            }
            R.id.action_cancelar -> {
                voltaListaServices()
                true
            }
            else -> false
        }



    private fun guardar() {
        val service_type = binding.editTextTypeService.text.toString()
        if (service_type.isBlank()) {
            binding.editTextTypeService.error = getString(R.string.service_obrigatorio)
            binding.editTextTypeService.requestFocus()
            return
        }

        val serviceGuardado =
            if (service == null) {
                insereService(service_type)
            } else {
                alteraService(service_type)
            }

        if (serviceGuardado) {
            Toast.makeText(requireContext(), R.string.service_guardado_sucesso, Toast.LENGTH_LONG)
                .show()
            voltaListaServices()
        } else {
            Snackbar.make(binding.editTextTypeService, R.string.erro_guardar_service, Snackbar.LENGTH_INDEFINITE).show()
            return
        }
    }

    private fun alteraService(service_type: String) : Boolean {
        val service = Services(service_type)

        val enderecoServices = Uri.withAppendedPath(ContentProviderAppointment.ENDERECO_SERVICE, "${this.service!!.id}")

        val registosAlterados = requireActivity().contentResolver.update(enderecoServices, service.toContentValues(), null, null)

        return registosAlterados == 1
    }

    private fun insereService(service_type: String): Boolean {
        val service = Services(service_type)

        val enderecoServiceInserido = requireActivity().contentResolver.insert(ContentProviderAppointment.ENDERECO_SERVICE, service.toContentValues())

        return enderecoServiceInserido != null
    }

    private fun voltaListaServices() {
        findNavController().navigate(R.id.action_FragmentEditarService_to_FragmentListaService)
    }
}