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
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog

class AlbumListView : AppCompatActivity() {
    var arregloAlbum = BaseDeDatosMemoria.albumBaseDeDatos
    var idItemSeleccionado = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.album_list_view)
        //Adaptador
        val albumListView = findViewById<ListView>(R.id.lv_album_item)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloAlbum
        )
        albumListView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        val botonAñadirAlbum = findViewById<Button>(R.id.btn_crear_album)
        botonAñadirAlbum
            .setOnClickListener{
                irActividad(CrearAlbum::class.java)
            }
        registerForContextMenu(albumListView)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val positionItemSeleccionado = info.position
        return when(item.itemId){
            R.id.mi_editar ->{
                editarAlbum(positionItemSeleccionado,EditarAlbum::class.java)
                return true
            }
            R.id.mi_eliminar ->{
                abrirDialogo(positionItemSeleccionado)
                return true
            }
            R.id.mi_ver_canciones->{
                mostrarCanciones(positionItemSeleccionado)
                return true
            }
            else ->super.onContextItemSelected(item)
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
        inflater.inflate(R.menu.menu, menu)
        // obtener el id del ArrayList seleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        //Recibimos respuesta
        callbackContenidoIntentExplicito.launch(intent)
    }

    val callbackContenidoIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    val data = result.data
                    val id = "${data?.getStringExtra("id")}".toInt()
                    val titulo = "${data?.getStringExtra("titulo")}"
                    val artista = "${data?.getStringExtra("artista")}"
                    val añoLanzamiento = "${data?.getStringExtra("añoLanzamiento")}"
                    val esExplicito = "${data?.getStringExtra("esExplicito")}".toBoolean()
                    arregloAlbum.add(
                        BAlbum(id,titulo,artista,añoLanzamiento,esExplicito, ArrayList<BCancion>()
                    )
                    )
                    BaseDeDatosMemoria.albumBaseDeDatos = arregloAlbum
                    val albumListView = findViewById<ListView>(R.id.lv_album_item)
                    val adaptador = ArrayAdapter(
                        this,
                        android.R.layout.simple_list_item_1,
                        arregloAlbum
                    )
                    albumListView.adapter = adaptador
                    adaptador.notifyDataSetChanged()

                }
            }
        }

    val callbackEditar =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    val data = result.data
                    val titulo =data?.getStringExtra("nuevoTitulo")
                    val artista = data?.getStringExtra("nuevoArtista")
                    val añoLanzamiento = data?.getStringExtra("nuevoAñoLanzamiento")
                    val esExplicito = data?.getStringExtra("nuevoEsExplicito").toBoolean()
                    val albumPosicion = data?.getIntExtra("posicionAlbum",-1)
                    if((albumPosicion != null) && (albumPosicion!=-1)){
                        val album = arregloAlbum[albumPosicion]
                        if (titulo != null) {
                            album.titulo = titulo
                        }
                        if (artista != null) {
                            album.artista = artista
                        }
                        if (añoLanzamiento != null) {
                            album.añoDeLanzamiento = añoLanzamiento
                        }
                        album.esExplicito = esExplicito
                        BaseDeDatosMemoria.albumBaseDeDatos[albumPosicion] = album
                        val albumListView = findViewById<ListView>(R.id.lv_album_item)
                        val adaptador = albumListView.adapter as ArrayAdapter<BAlbum>
                        adaptador.notifyDataSetChanged()
                    }
                }
            }
        }

    private fun editarAlbum(posicion: Int, clase: Class<*>) {
        val intent = Intent(this, clase)
        intent.putExtra("albumPosicion", posicion)
        callbackEditar.launch(intent)
    }

    fun abrirDialogo(posicion:Int){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar?")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener{ // Callback
                    dialog, which -> // ALGUNA COSA
                eliminarAlbum(posicion)
            }
        )
        builder.setNegativeButton("Cancelar", null)
        val dialogo = builder.create()
        dialogo.show()
    }

    fun eliminarAlbum(posicion: Int){
        arregloAlbum
            .removeAt(posicion)
        BaseDeDatosMemoria.albumBaseDeDatos = arregloAlbum
        val listView = findViewById<ListView>(R.id.lv_album_item)
        val adaptador = listView.adapter as ArrayAdapter<BAlbum>
        adaptador.notifyDataSetChanged()
    }

    private fun mostrarCanciones(position: Int) {
        val intent = Intent(this, CancionListView::class.java)
        intent.putExtra("albumPosicion", position)
        startActivity(intent)
    }

}
