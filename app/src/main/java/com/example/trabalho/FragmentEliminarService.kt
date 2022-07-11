package com.example.trabalho

import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.trabalho.databinding.FragmentEliminarServiceBinding
import com.google.android.material.snackbar.Snackbar

class FragmentEliminarService : Fragment() {
    private var _binding: FragmentEliminarServiceBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var services: Services

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEliminarServiceBinding.inflate(inflater, container, false)
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
        activity.idMenuAtual = R.menu.menu_eliminar

        services = FragmentEliminarServiceArgs.fromBundle(arguments!!).service

        binding.textViewTypeService.text = services.service_type
    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_eliminar -> {
                eliminaService()
                true
            }
            R.id.action_cancelar -> {
                voltaListaService()
                true
            }
            else -> false
        }

    private fun eliminaService() {
        val alertDialog = AlertDialog.Builder(requireContext())

        alertDialog.apply {
            setTitle(R.string.eliminar_service_label)
            setMessage(R.string.confirma_eliminar_service)
            setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialogInterface, i ->  })
            setPositiveButton(R.string.eliminar, DialogInterface.OnClickListener { dialogInterface, i -> confirmaEliminarService() })
            show()
        }
    }

    private fun confirmaEliminarService() {
        val enderecoServices = Uri.withAppendedPath(ContentProviderAppointment.ENDERECO_APPOINTEMTS, "${services.service_type}")
        val registosEliminados = requireActivity().contentResolver.delete(enderecoServices, null, null)

        if (registosEliminados != 1) {
            Snackbar.make(
                binding.textViewTypeService,
                R.string.erro_eliminar_livro,
                Snackbar.LENGTH_INDEFINITE
            ).show()
            return
        }

        Toast.makeText(requireContext(), R.string.service_eliminado_sucesso, Toast.LENGTH_LONG).show()
        voltaListaService()
    }

    private fun voltaListaService() {
        val acao = FragmentEliminarServiceDirections.actionFragmentEliminarServiceToFragmentListaService()
        findNavController().navigate(acao)
    }
}