package com.example.trabalho

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trabalho.databinding.FragmentListaClientBinding

class FragmentListaClient : Fragment(){
    var clientSeleccionado : Client? = null
        get() = field
        set(value) {
            field = value
            (requireActivity() as MainActivity).mostraOpcoesAlterarEliminar(field != null)
        }


    private var _binding: FragmentListaClientBinding? = null
    private var adapterClients : AdapterClient? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListaClientBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapterClients = AdapterClient(this)
        binding.recyclerViewClient.adapter = adapterClients
        binding.recyclerViewClient.layoutManager = LinearLayoutManager(requireContext())

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_lista
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


   fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_inserir -> {
                val acao = FragmentListaClientDirections.actionFragmentListaClientToFragmentEditarClient()
                findNavController().navigate(acao)
                (activity as MainActivity).atualizaName(R.string.inserir_client_label)
                true
            }
            R.id.action_alterar -> {
                val acao = FragmentListaClientDirections.actionFragmentListaClientToFragmentEditarClient(clientSeleccionado)
                findNavController().navigate(acao)
                (activity as MainActivity).atualizaName(R.string.alterar_client_label)
                true
            }
            R.id.action_eliminar -> {
                val acao = FragmentListaClientDirections.actionFragmentListaClientToFragmentEliminarClient(clientSeleccionado!!)
                findNavController().navigate(acao)
                true
            }
            else -> false
        }





    companion object {
        const val ID_LOADER_CLIENTS = 0
    }
}