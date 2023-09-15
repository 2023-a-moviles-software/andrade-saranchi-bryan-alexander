package com.example.deber03

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deber03.Adapter.CancionAdapter
import com.example.deber03.Adapter.CancionViewHolder

class CancionRecyclerView : AppCompatActivity() {
    private lateinit var adapter : CancionAdapter
    private lateinit var baseDeDatos : SQLiteHelper
    private var albumId : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cancion_recycler_view)
        val tituloAlbum = intent.getStringExtra("nombreAlbum")
        val albumId = intent.getIntExtra("albumId",-1)
        val tvAlbumTitulo = findViewById<TextView>(R.id.tv_album_titulo2)
        tvAlbumTitulo.text = tituloAlbum

        baseDeDatos = SQLiteHelper(this)
        albumId.let { baseDeDatos.obtenerCancionPorAlbumId(albumId)}
        val recyclerView = findViewById<RecyclerView>(R.id.rv_canciones)
        adapter = CancionAdapter(baseDeDatos.obtenerCancionPorAlbumId(albumId), baseDeDatos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        CancionViewHolder.setAdapter(adapter)
        EditarCancion.setAdapter(adapter)

        CancionViewHolder.setAlbumId(albumId)
        val btnCrearCancion = findViewById<Button>(R.id.btn_crear_cancion)
        btnCrearCancion
            .setOnClickListener {
                crearCancion(albumId,CrearCancion::class.java)
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CancionRecyclerView.CREAR_CANCION && resultCode == Activity.RESULT_OK && data != null) {
            baseDeDatos = SQLiteHelper(this)
            val cancionNombre = data.getStringExtra("cancionNombre")
            val cancionArtista = data.getStringExtra("cancionArtista")
            val cancionDuracion = data.getDoubleExtra("cancionDuracion",0.0)
            val cancionAnioLanzamiento = data.getStringExtra("cancionAnioLanzamiento")
            val albumId2 = intent.getIntExtra("albumId",-1)
            val test = findViewById<TextView>(R.id.tv_titulo_cancion)
            test.text = albumId.toString()

            val nuevaCancion = BCancion(
                null,
                cancionNombre.toString(),
                cancionArtista.toString(),
                cancionDuracion,
                cancionAnioLanzamiento.toString()
            )
            baseDeDatos.crearCancion(nuevaCancion, albumId2)
            val listCanciones = baseDeDatos.obtenerCancionPorAlbumId(albumId2)
            adapter.actualizarCanciones(listCanciones)
        }
    }
    val callbackCrear =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {
                    val albumPosicion = intent.getIntExtra("albumId",-1)
                    val data = result.data
                    val cancionNombre = data?.getStringExtra("cancionNombre")
                    val cancionArtista = data?.getStringExtra("cancionArtista")
                    val cancionDuracion = data?.getDoubleExtra("cancionDuracion",0.0)
                    val cancionAnioLanzamiento = data?.getStringExtra("cancionAnioLanzamiento")
                    val test = findViewById<TextView>(R.id.tv_titulo_cancion)
                    test.text = albumPosicion.toString()

                    val nuevaCancion = cancionDuracion?.let {
                        BCancion(
                            null,
                            cancionNombre.toString(),
                            cancionArtista.toString(),
                            it,
                            cancionAnioLanzamiento.toString()
                        )
                    }
                    if (nuevaCancion != null) {
                        baseDeDatos.crearCancion(nuevaCancion, albumPosicion)
                    }
                    val listCanciones = baseDeDatos.obtenerCancionPorAlbumId(albumPosicion)
                    adapter.actualizarCanciones(listCanciones)


                }
            }
        }

    private fun crearCancion(posicion: Int, clase: Class<*>) {
        val intent = Intent(this, clase)
        intent.putExtra("posicionAlbum", posicion)
        callbackCrear.launch(intent)

    }

    companion object {
        private const val CREAR_CANCION= 1
    }
}