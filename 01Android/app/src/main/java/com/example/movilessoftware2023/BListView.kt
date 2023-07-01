package com.example.movilessoftware2023

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class BListView : AppCompatActivity() {
    val arreglo = BbaseDatosMemoria.arregloBEntrenador
    var idItemSeleccionado = 0
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view)
        //adaptador
        val listView = findViewById<ListView>(
            R.id.lv_list_view
        )
        val adaptador = ArrayAdapter(
            this, //contexto
            android.R.layout.simple_list_item_1, //layout.xml que se va a usar
            arreglo
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        val botonAniadirListView = findViewById<Button>(R.id.btn_aniadir_listView)
        botonAniadirListView.setOnClickListener{
            añadirEntrenador(adaptador)
        }
        registerForContextMenu(listView)



    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_editar ->{
                "Hacer algo con: ${idItemSeleccionado}"
                return true
            }
            R.id.mi_eliminar ->{
                "Hacer algo con: ${idItemSeleccionado}"
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirDialogo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar?")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener{ //callback
                dialog, which -> //ALGUNA COSA
            }
        )
        builder.setNegativeButton("Cancelar",null)

        val opciones = resources.getStringArray(
            R.array.string_array_opciones_dialogo
        )

        val seleccionPrevia = booleanArrayOf(
            true, //Lunes seleccionado
            false, //Martes no seleccionado
            false, //Miercoles no seleccionado
        )

        builder.setMultiChoiceItems(
            opciones,
            seleccionPrevia,
            {
                dialog, which, isChecked ->
                "Dio clic en el item ${which}"
            }
        )
        val dialog = builder.create()


    }



    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        //llenar las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu,menu)
        //obtener el id del ArrayList seleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
    }
    fun añadirEntrenador(
        adapter: ArrayAdapter<BEntrenador>
    ){
        arreglo.add(
            BEntrenador(4, "Adrian", "Descripcion")
        )
        adapter.notifyDataSetChanged()
    }




}