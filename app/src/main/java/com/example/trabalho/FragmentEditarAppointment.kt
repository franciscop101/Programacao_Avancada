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
import com.example.trabalho.databinding.FragmentEditarAppointmentBinding
import com.google.android.material.snackbar.Snackbar

class FragmentEditarAppointment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var _binding: FragmentEditarAppointmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var appointment: Appointment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarAppointmentBinding.inflate(inflater, container, false)
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
            appointment = FragmentEditarAppointmentArgs.fromBundle(arguments!!).appointments

            if (appointment != null) {
                binding.editTextNameAppointment.setText(appointment!!.appointment_name)
                binding.editTextTime.setText(appointment!!.time.toString())
                binding.editTextDate.setText(appointment!!.date.toString())
               // binding.spinnerClient.adapter(appointment!!.client)
                }
        }

        //LoaderManager.getInstance(this).initLoader(ID_LOADER_CLIENT, null, this)
    }

    companion object {
        const val ID_LOADER_CLIENT = 0
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param id The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> =
        CursorLoader(
            requireContext(),
            ContentProviderAppointment.ENDERECO_APPOINTEMTS,
            TabelaBDClient.TODAS_COLUNAS,
            null,
            null,
            "${TabelaBDClient.CAMPO_NOME}"
        )

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is *not* allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See [ FragmentManager.openTransaction()][androidx.fragment.app.FragmentManager.beginTransaction] for further discussion on this.
     *
     *
     * This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     *
     *
     *  *
     *
     *The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a [android.database.Cursor]
     * and you place it in a [android.widget.CursorAdapter], use
     * the [android.widget.CursorAdapter.CursorAdapter] constructor *without* passing
     * in either [android.widget.CursorAdapter.FLAG_AUTO_REQUERY]
     * or [android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER]
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     *  *  The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a [android.database.Cursor] from a [android.content.CursorLoader],
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * [android.widget.CursorAdapter], you should use the
     * [android.widget.CursorAdapter.swapCursor]
     * method so that the old Cursor is not closed.
     *
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that has finished.
     * @param data The data generated by the Loader.
     */
    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        val adapterClient = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaBDClient.CAMPO_NOME),
            intArrayOf(android.R.id.text1),
            0
        )

        binding.spinnerClient.adapter = adapterClient

        atualizaClientSelecionada()
    }

    private fun atualizaClientSelecionada() {
        if (appointment == null) return
        val idClient = appointment!!.client.id

        val ultimaClient = binding.spinnerClient.count - 1

        for (i in 0..ultimaClient) {
            if (binding.spinnerClient.getItemIdAtPosition(i) == idClient) {
                binding.spinnerClient.setSelection(i)
                return
            }
        }
    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that is being reset.
     */
    override fun onLoaderReset(loader: Loader<Cursor>) {
        if (_binding == null) return
        binding.spinnerClient.adapter = null
    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_guardar -> {
                guardar()
                true
            }
            R.id.action_cancelar -> {
                voltaListaAppointment()
                true
            }
            else -> false
        }

    private fun guardar() {
        val name = binding.editTextNameAppointment.text.toString()
        if (name.isBlank()) {
            binding.editTextNameAppointment.error = getString(R.string.appointment_name_obrigatorio)
            binding.editTextNameAppointment.requestFocus()
            return
        }

        val stime = binding.editTextTime.text.toString()
        if (stime.isBlank()) {
            binding.editTextTime.error = getString(R.string.time_obrigatorio)
            binding.editTextTime.requestFocus()
            return
        }
        val time = stime.toLong()


        val sdate = binding.editTextDate.text.toString()
        if (sdate.isBlank()) {
            binding.editTextDate.error = getString(R.string.client_obrigatorio)
            binding.editTextDate.requestFocus()
            return
        }
        val date = stime.toLong()

        val idClient = binding.spinnerClient.selectedItemId
        if (idClient == Spinner.INVALID_ROW_ID) {
            binding.textViewClient.error = getString(R.string.client_obrigatoria)
            binding.spinnerClient.requestFocus()
            return
        }

        val AppointmentGuardado =
            if (appointment == null) {
                insereAppointment(name, time, date, idClient)
            } else {
                alteraAppointment(name, time, date, idClient)
            }

        if (AppointmentGuardado) {
            Toast.makeText(requireContext(), R.string.Appointment_guardado_sucesso, Toast.LENGTH_LONG)
                .show()
            voltaListaAppointment()
        } else {
            Snackbar.make(binding.editTextNameAppointment, R.string.erro_guardar_appointment_name, Snackbar.LENGTH_INDEFINITE).show()
            return
        }
    }

    private fun alteraAppointment(
        appointment_name: String, time: Long, date: Long,
        idClient: Long) : Boolean {
        val appointment = Appointment(appointment_name, time, date, Client("","","",id = idClient))

        val enderecoAppointment = Uri.withAppendedPath(ContentProviderAppointment.ENDERECO_APPOINTEMTS, "${this.appointment!!.id}")

        val registosAlterados = requireActivity().contentResolver.update(enderecoAppointment, appointment.toContentValues(), null, null)

        return registosAlterados == 1
    }

    private fun insereAppointment(appointment_name: String, time: Long, date: Long, idClient: Long): Boolean {
        val appointment = Appointment(appointment_name, time, date, Client("","","",id = idClient))

        val enderecoAppointmentInserido = requireActivity().contentResolver.insert(ContentProviderAppointment.ENDERECO_APPOINTEMTS, appointment.toContentValues())

        return enderecoAppointmentInserido != null
    }

    private fun voltaListaAppointment() {
        findNavController().navigate(R.id.action_FragmentEditarAppointment_to_FragmentListaAppointment)
    }
}