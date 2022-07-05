package com.example.trabalho

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem

import android.view.View
import android.view.ViewGroup

import android.widget.Toast
import androidx.fragment.app.Fragment

import androidx.navigation.fragment.findNavController

import com.google.android.material.snackbar.Snackbar
import com.example.trabalho.databinding.FragmentEditarClientBinding

class FragmentEditarClient : Fragment() {
    private var _binding: FragmentEditarClientBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var client: Client? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarClientBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
/*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_edicao

        if (arguments != null) {
            client = FragmentEditarClientArgs.fromBundle(arguments!!).client

            if (client != null) {
                binding.editTextNome.setText(client!!.nome)
                binding.editTextTelemovel.setText(client!!.phone_number)
                binding.editTextEmail.setText(client!!.email)
            }
        }

    }
 */

    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_guardar -> {
                guardar()
                true
            }
            R.id.action_cancelar -> {
                voltaListaClient()
                true
            }
            else -> false
        }

    private fun guardar() {
        val nome = binding.editTextNome.text.toString()
        if (nome.isBlank()) {
            binding.editTextNome.error = getString(R.string.nome_obrigatorio)
            binding.editTextNome.requestFocus()
            return
        }

        val telemovel = binding.editTextTelemovel.text.toString()
        if (telemovel.isBlank()) {
            binding.editTextTelemovel.error = getString(R.string.phone_obrigatorio)
            binding.editTextTelemovel.requestFocus()
            return
        }
        val email = binding.editTextEmail.text.toString()
        if (email.isBlank()) {
            binding.editTextEmail.error = getString(R.string.email_obrigatorio)
            binding.editTextEmail.requestFocus()
            return
        }


        val clientGuardado =
            if (client == null) {
                insereClient(nome, telemovel, email)
            } else {
                alteraClient(nome, telemovel, email)
            }

        if (clientGuardado) {
            Toast.makeText(requireContext(), R.string.client_guardado_sucesso, Toast.LENGTH_LONG)
                .show()
            voltaListaClient()
        } else {
            Snackbar.make(binding.editTextNome, R.string.erro_guardar_client, Snackbar.LENGTH_INDEFINITE).show()
            return
        }
    }

    private fun alteraClient(nome: String, telemovel: String, email: String) : Boolean {
        val client = Client(nome, telemovel, email )

        val enderecoClient = Uri.withAppendedPath(ContentProviderAppointment.ENDERECO_CLIENTS, "${this.client!!.id}")

        val registosAlterados = requireActivity().contentResolver.update(enderecoClient, client.toContentValues(), null, null)

        return registosAlterados == 1
    }

    private fun insereClient(nome: String, telemovel: String, email: String): Boolean {
        val client = Client(nome, telemovel, email )

        val enderecoClientInserido = requireActivity().contentResolver.insert(ContentProviderAppointment.ENDERECO_CLIENTS, client.toContentValues())

        return enderecoClientInserido != null
    }

    private fun voltaListaClient() {
        findNavController().navigate(R.id.action_FragmentEditarClient_to_FragmentListaClient)
    }
}