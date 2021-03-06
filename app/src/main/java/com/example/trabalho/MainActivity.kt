package com.example.trabalho

import android.os.Bundle
/**import android.support.design.widget.Snackbar*/
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.trabalho.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    var idMenuAtual = R.menu.menu_main
        get() = field
        set(value) {
            if (value != field) {
                field = value
                invalidateOptionsMenu()
            }
        }

    var menu: Menu? = null

    var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(idMenuAtual, menu)
        this.menu = menu
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        val opcaoProcessada: Boolean

        if (fragment is FirstFragment) {
            opcaoProcessada = (fragment as FirstFragment).processaOpcaoMenu(item)
        //CLIENT
        } else if (fragment is FragmentListaClient) {
            opcaoProcessada = (fragment as FragmentListaClient).processaOpcaoMenu(item)
        } else if (fragment is FragmentEditarClient) {
            opcaoProcessada = (fragment as FragmentEditarClient).processaOpcaoMenu(item)
        } else if (fragment is FragmentEliminarClient) {
            opcaoProcessada = (fragment as FragmentEliminarClient).processaOpcaoMenu(item)

        //SERVICE
        } else if (fragment is FragmentListaService) {
            opcaoProcessada = (fragment as FragmentListaService).processaOpcaoMenu(item)
        } else if (fragment is FragmentEditarService) {
            opcaoProcessada = (fragment as FragmentEditarService).processaOpcaoMenu(item)
        } else if (fragment is FragmentEliminarService) {
            opcaoProcessada = (fragment as FragmentEliminarService).processaOpcaoMenu(item)

        //APPOINTMENT
        } else if (fragment is FragmentListaAppointment) {
            opcaoProcessada = (fragment as FragmentListaAppointment).processaOpcaoMenu(item)
        } else if (fragment is FragmentEditarAppointment) {
            opcaoProcessada = (fragment as FragmentEditarAppointment).processaOpcaoMenu(item)
        } else if (fragment is FragmentEliminarAppointment) {
            opcaoProcessada = (fragment as FragmentEliminarAppointment).processaOpcaoMenu(item)
        } else {
            opcaoProcessada = false
        }

        if (opcaoProcessada) return true

        return super.onOptionsItemSelected(item)
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    fun mostraOpcoesAlterarEliminar(mostra: Boolean) {
        menu!!.findItem(R.id.action_alterar).setVisible(mostra)
        menu!!.findItem(R.id.action_eliminar).setVisible(mostra)
    }

    fun atualizaName(id_string_Name: Int) {

        binding.toolbar.setTitle(id_string_Name)

    }


}