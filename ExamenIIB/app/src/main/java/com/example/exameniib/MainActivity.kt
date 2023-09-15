package com.example.exameniib

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
import com.example.exameniib.Models.Album
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    val albumList = arrayListOf<Album>()
    lateinit var listView: ListView
    lateinit var adaptador: ArrayAdapter<Album>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnCrearAlbum = findViewById<Button>(R.id.btn_crear_album)
        btnCrearAlbum.setOnClickListener {
            irActividad(CrearAlbum::class.java)
        }
        listView = findViewById(R.id.lv_album)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            albumList
        )
        listView.adapter = adaptador
        consultarAlbums(adaptador)
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
    }
    fun consultarAlbums(adaptador: ArrayAdapter<Album>) {
        val db = Firebase.firestore
        val albumRef = db.collection("album")
        albumRef
            .get()
            .addOnSuccessListener {
                limpiarArreglo()
                for (album in it){
                    album.id
                    anadirAArregloAlbum(album)
                }
                adaptador.notifyDataSetChanged()
            }
            .addOnFailureListener {
            }
    }

    fun eliminarAlbum(titulo: String) {
        val db = Firebase.firestore
        val albumRef = db.collection("album")
        albumRef
            .whereEqualTo("titulo", titulo)
            .get()
            .addOnSuccessListener { result ->
                if (result.documents.isNotEmpty()) {
                    val documentId = result.documents[0].id
                    albumRef
                        .document(documentId)
                        .delete()
                        .addOnSuccessListener {
                        }
                        .addOnFailureListener {
                        }
                }
            }
            .addOnFailureListener {
            }
    }
    fun limpiarArreglo() {albumList.clear()}
    fun anadirAArregloAlbum(
        album: QueryDocumentSnapshot
    ){
        val nuevoAlbum = Album(
            album.data.get("id") as String?,
            album.data.get("titulo") as String?,
            album.data.get("artista") as String?,
            album.data.get("anioLanzamiento") as String?,
            album.data.get("esExplicito") as Boolean
        )
        albumList.add(nuevoAlbum)
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        val nombreR = albumEscogido?.titulo ?: return super.onContextItemSelected(item)

        return when(item.itemId){
            R.id.mi_editar ->{
                abrirActividadConParametros(
                    EditarAlbum::class.java,
                    nombreR
                )
                return true
            }
            R.id.mi_eliminar ->{
                eliminarAlbum(nombreR)
                consultarAlbums(adaptador)
                return true
            }
            R.id.mi_ver_canciones ->{
                abrirActividadConParametros(
                    CancionListView::class.java,
                    nombreR
                )
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    var albumEscogido: Album? = null
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.album_menu, menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        albumEscogido = listView.adapter.getItem(info.position) as Album
    }



    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    fun abrirActividadConParametros(clase: Class<*>, nombre: String) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("nombre", nombre)
        startActivity(intentExplicito)
    }

    override fun onResume() {
        super.onResume()
        consultarAlbums(adaptador)
    }



















}