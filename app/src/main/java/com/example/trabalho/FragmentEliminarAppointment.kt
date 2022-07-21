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
import com.example.trabalho.databinding.FragmentEliminarAppointmentBinding
import com.google.android.material.snackbar.Snackbar

class FragmentEliminarAppointment : Fragment() {
    private var _binding: FragmentEliminarAppointmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var appointment: Appointment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEliminarAppointmentBinding.inflate(inflater, container, false)
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

        appointment = FragmentEliminarAppointmentArgs.fromBundle(arguments!!).appointment

        binding.textViewNameAppontments.text = appointment?.appointment_name
        binding.textViewDate.text = appointment?.time.toString()
        binding.textViewClient.text = appointment?.client?.nome

    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_eliminar -> {
                eliminaAppoitment()
                true
            }
            R.id.action_cancelar -> {
                voltaListaAppointment()
                true
            }
            else -> false
        }

    private fun eliminaAppoitment() {
        val alertDialog = AlertDialog.Builder(requireContext())

        alertDialog.apply {
            setTitle(R.string.eliminar_appointment_label)
            setMessage(R.string.confirma_eliminar_appointment)
            setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialogInterface, i ->  })
            setPositiveButton(R.string.eliminar, DialogInterface.OnClickListener { dialogInterface, i -> confirmaEliminaAppoitment() })
            show()
        }
    }

    private fun confirmaEliminaAppoitment() {
        val enderecoAppointment = Uri.withAppendedPath(ContentProviderAppointment.ENDERECO_APPOINTEMTS, "${appointment?.id}")
        val registosEliminados = requireActivity().contentResolver.delete(enderecoAppointment, null, null)

        if (registosEliminados != 1) {
            Snackbar.make(
                binding.textViewNameAppontments,
                R.string.erro_eliminar_appointment,
                Snackbar.LENGTH_INDEFINITE
            ).show()
            return
        }

        Toast.makeText(requireContext(), R.string.appointment_eliminado_sucesso, Toast.LENGTH_LONG).show()
        voltaListaAppointment()
    }

    private fun voltaListaAppointment() {
        val acao = FragmentEliminarAppointmentDirections.actionFragmentEliminarAppoitmentToFragmentListaAppointment()
        findNavController().navigate(acao)
    }
}