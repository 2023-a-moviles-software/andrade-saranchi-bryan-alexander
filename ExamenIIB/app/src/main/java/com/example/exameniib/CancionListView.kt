package com.example.exameniib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.example.exameniib.Models.Cancion
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot

class CancionListView : AppCompatActivity() {
    val query: Query? = null
    val cancionList = arrayListOf<Cancion>()
    lateinit var listView: ListView
    lateinit var adaptador: ArrayAdapter<Cancion>
    private val db = FirebaseFirestore.getInstance()
    private var cancionSeleccionada: Cancion? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cancion_list_view)
        val crearCancion = findViewById<Button>(R.id.btn_crear_cancion)
        crearCancion.setOnClickListener {
            abrirActividadConParametros(
                CrearCancion::class.java,
                intent.getStringExtra("nombre") ?: "",
                cancionSeleccionada?.nombreCancion ?: ""
            )
        }
        listView = findViewById(R.id.lv_cancion)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            cancionList
        )
        listView.adapter = adaptador
        llenarDatos()
        registerForContextMenu(listView)
    }

    private fun llenarDatos() {
        val nombreAlbum = intent.getStringExtra("nombre") ?: ""
        val nombreAlbum2 = findViewById<TextView>(R.id.tv_album_titulo2)
        nombreAlbum2.text = nombreAlbum

        db.collection("album")
            .whereEqualTo("titulo", nombreAlbum)
            .get()
            .addOnSuccessListener { documentos ->
                if (documentos.documents.isNotEmpty()) {
                    val albumId = documentos.documents[0].id
                    obtenerCanciones(albumId)
                } else {
                }
            }
            .addOnFailureListener { exception ->
            }
    }

    private fun obtenerCanciones(albumId: String) {
        db.collection("album")
            .document(albumId)
            .collection("cancion")
            .get()
            .addOnSuccessListener { documentos ->
                cancionList.clear()
                for (documento in documentos) {
                    documento.id
                    añadirACancion(documento)
                }
                adaptador.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
            }
    }

    fun añadirACancion(cancion: QueryDocumentSnapshot) {
        val nuevaCancion = Cancion(
            cancion.data["id"] as String?,
            cancion.data["nombreCancion"] as String?,
            cancion.data["artista"] as String?,
            cancion.data["duracion"] as Double?,
            cancion.data["anioLanzamiento"] as String?,
        )
        cancionList.add(nuevaCancion)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val nombre = intent.getStringExtra("nombre") ?: ""
        val cancionSeleccionada = cancionSeleccionada?.nombreCancion ?: return super.onContextItemSelected(item)
        return when (item.itemId) {
            R.id.mi_editar -> {
                abrirActividadConParametros(
                    EditarCancion::class.java,
                    nombre,
                    cancionSeleccionada)
                true
            }
            R.id.mi_eliminar -> {
                eliminarCancion(nombre, cancionSeleccionada)
                true
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
        val inflater = menuInflater
        inflater.inflate(R.menu.cancion_menu, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        cancionSeleccionada = listView.adapter.getItem(info.position) as Cancion
    }

    fun eliminarCancion(nombreAlbum: String, nombreCancion: String) {
        db.collection("album")
            .whereEqualTo("titulo", nombreAlbum)
            .get()
            .addOnSuccessListener { documentosAlbum ->
                if (documentosAlbum.documents.isNotEmpty()) {
                    val albumId = documentosAlbum.documents[0].id
                    db.collection("album")
                        .document(albumId)
                        .collection("cancion")
                        .whereEqualTo("nombreCancion", nombreCancion)
                        .get()
                        .addOnSuccessListener { documentosCancion ->
                            if (documentosCancion.documents.isNotEmpty()) {
                                val cancionId = documentosCancion.documents[0].id
                                db.collection("album")
                                    .document(albumId)
                                    .collection("cancion")
                                    .document(cancionId)
                                    .delete()
                                    .addOnSuccessListener {
                                        llenarDatos()
                                    }
                                    .addOnFailureListener {
                                    }
                            }
                        }
                        .addOnFailureListener {
                        }
                }
            }
            .addOnFailureListener {
            }
    }

    fun abrirActividadConParametros(clase: Class<*>, nombreAlbum: String, nombreCancion: String) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("nombreA", nombreAlbum)
        intentExplicito.putExtra("nombreC", nombreCancion)
        startActivity(intentExplicito)
    }

    override fun onResume() {
        super.onResume()
        llenarDatos()
    }
}