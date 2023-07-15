package com.example.examen

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog

class CancionListView : AppCompatActivity() {
    val arregloAlbum = BaseDeDatosMemoria.albumBaseDeDatos
    var canciones: ArrayList<BCancion> = ArrayList()
    var idItemSeleccionado = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cancion_list_view)

        val posicionAlbum = intent.getIntExtra("albumPosicion", -1)
        val album = arregloAlbum[posicionAlbum]
        canciones = album.canciones

        val cancionListView = findViewById<ListView>(R.id.lv_cancion_item)
        val albumPerteneciente = findViewById<TextView>(R.id.tv_album_perteneciente)
        albumPerteneciente.setText(arregloAlbum[posicionAlbum].titulo)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            canciones
        )
        cancionListView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        val botonCrearCancion = findViewById<Button>(R.id.btn_crear_cancion)
        botonCrearCancion
            .setOnClickListener {
                crearCancion(posicionAlbum, CrearCancion::class.java)
            }

        registerForContextMenu(cancionListView)
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val posicionAlbum = intent.getIntExtra("albumPosicion", -1)
        val positionItemSeleccionadoCancion = info.position
        return when (item.itemId) {
            R.id.mi_editar -> {
                editarCancion(
                    positionItemSeleccionadoCancion,
                    posicionAlbum,
                    EditarCancion::class.java
                )
                return true
            }

            R.id.mi_eliminar -> {
                abrirDialogo(positionItemSeleccionadoCancion)
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // llenar las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu2, menu)
        // obtener el id del ArrayList seleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
    }

    fun abrirDialogo(posicion: Int) {
        val builder = AlertDialog.Builder(this)
        val posicionAlbum = intent.getIntExtra("albumPosicion", -1)
        builder.setTitle("Desea eliminar?")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener { // Callback
                    dialog, which -> // ALGUNA COSA
                eliminarCancion(posicion, posicionAlbum)
            }
        )
        builder.setNegativeButton("Cancelar", null)
        val dialogo = builder.create()
        dialogo.show()
    }

    val callbackCrear =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {
                    val data = result.data
                    val id = data?.getStringExtra("id")?.toInt()
                    val nombreCancion = data?.getStringExtra("nombreCancion").toString()
                    val artista = data?.getStringExtra("artista").toString()
                    val duracion = data?.getStringExtra("duracion")?.toDouble()
                    val añoLanzamiento = data?.getStringExtra("añoLanzamiento").toString()
                    val albumPosicion = data?.getIntExtra("posicionAlbum", -1)

                    if ((albumPosicion != null) && (albumPosicion != -1)) {
                        if (id != null && nombreCancion != null && artista != null && duracion != null && añoLanzamiento != null) {
                            canciones.add(
                                BCancion(
                                    id,
                                    nombreCancion,
                                    artista,
                                    duracion,
                                    añoLanzamiento
                                )
                            )
                            BaseDeDatosMemoria.albumBaseDeDatos[albumPosicion].canciones = canciones
                            val cancionListView = findViewById<ListView>(R.id.lv_cancion_item)
                            val adaptador = cancionListView.adapter as ArrayAdapter<BCancion>
                            cancionListView.adapter = adaptador
                            adaptador.notifyDataSetChanged()

                        }


                    }


                }
            }
        }

    val callbackEditar =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {
                    val albumPosicion = intent.getIntExtra("albumPosicion", -1)
                    val data = result.data
                    val nombreCancion = data?.getStringExtra("nuevoNombreCancion").toString()
                    val artista = data?.getStringExtra("nuevoArtista").toString()
                    val duracion = data?.getStringExtra("nuevaDuracion")?.toDouble()
                    val añoLanzamiento = data?.getStringExtra("nuevoAñoLanzamiento").toString()
                    val posicionCancion = data?.getIntExtra("posicionCancion", -1)
                    val id =
                        BaseDeDatosMemoria.albumBaseDeDatos[albumPosicion].canciones[posicionCancion!!].id
                    if ((albumPosicion != null) && (albumPosicion != -1)) {
                        if (nombreCancion != null && artista != null && duracion != null && añoLanzamiento != null) {
                            val cancion = arregloAlbum[albumPosicion].canciones[posicionCancion]

                            cancion.nombreCancion = nombreCancion
                            cancion.artista = artista
                            cancion.duracion = duracion
                            cancion.añoLanzamiento = añoLanzamiento

                            BaseDeDatosMemoria.albumBaseDeDatos[albumPosicion].canciones[posicionCancion] =
                                cancion
                            val cancionListView = findViewById<ListView>(R.id.lv_cancion_item)
                            val adaptador = cancionListView.adapter as ArrayAdapter<BCancion>
                            cancionListView.adapter = adaptador
                            adaptador.notifyDataSetChanged()
                        }


                    }


                }
            }
        }


    private fun crearCancion(posicion: Int, clase: Class<*>) {
        val intent = Intent(this, clase)
        intent.putExtra("posicionAlbum", posicion)
        callbackCrear.launch(intent)
    }

    private fun editarCancion(posicionCancion: Int, posicionAlbum: Int, clase: Class<*>) {
        val intent = Intent(this, clase)
        intent.putExtra("posicionCancion", posicionCancion)
        intent.putExtra("posicionAlbum", posicionAlbum)
        callbackEditar.launch(intent)
    }


    private fun eliminarCancion(positionCancion: Int, posicionAlbum: Int) {
        canciones
            .removeAt(positionCancion)
        BaseDeDatosMemoria.albumBaseDeDatos[posicionAlbum].canciones = canciones
        val cancionlistView = findViewById<ListView>(R.id.lv_cancion_item)
        val adaptador = cancionlistView.adapter as ArrayAdapter<BCancion>
        adaptador.notifyDataSetChanged()
    }
}



