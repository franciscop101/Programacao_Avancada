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
import com.example.trabalho.databinding.FragmentEliminarClientBinding
import com.google.android.material.snackbar.Snackbar

class FragmentEliminarClient : Fragment() {
    private var _binding: FragmentEliminarClientBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var client: Client

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEliminarClientBinding.inflate(inflater, container, false)
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

        client = FragmentEliminarClientArgs.fromBundle(arguments!!).client

        binding.textViewName.text = client.nome
        binding.textViewPhone.text = client.phone_number
        binding.textViewEmail.text = client.email

    }



    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_eliminar -> {
                eliminaClient()
                true
            }
            R.id.action_cancelar -> {
                voltaListaClients()
                true
            }
            else -> false
        }

    private fun eliminaClient() {
        val alertDialog = AlertDialog.Builder(requireContext())

        alertDialog.apply {
            setTitle(R.string.eliminar_client_label)
            setMessage(R.string.confirma_eliminar_client)
            setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialogInterface, i ->  })
            setPositiveButton(R.string.eliminar, DialogInterface.OnClickListener { dialogInterface, i -> confirmaEliminaClient() })
            show()
        }
    }

    private fun confirmaEliminaClient() {
        val enderecoClient = Uri.withAppendedPath(ContentProviderAppointment.ENDERECO_CLIENTS, "${client.id}")
        val registosEliminados = requireActivity().contentResolver.delete(enderecoClient, null, null)

        if (registosEliminados != 1) {
            Snackbar.make(
                binding.textViewName,
                R.string.erro_eliminar_client,
                Snackbar.LENGTH_INDEFINITE
            ).show()
            return
        }

        Toast.makeText(requireContext(), R.string.client_eliminado_sucesso, Toast.LENGTH_LONG).show()
        voltaListaClients()
    }

    private fun voltaListaClients() {
        val acao = FragmentEliminarClientDirections.actionFragmentEliminarClientToFragmentListaClient()
        findNavController().navigate(acao)
    }
}