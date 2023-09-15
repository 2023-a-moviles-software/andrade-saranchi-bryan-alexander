package com.example.deber03

import android.app.Activity
import android.content.Intent
import android.os.BaseBundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deber03.Adapter.AlbumAdapter
import com.example.deber03.Adapter.AlbumViewHolder

class MainActivity : AppCompatActivity() {
    lateinit var baseDeDatos : SQLiteHelper
    private lateinit var adapter : AlbumAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        baseDeDatos = SQLiteHelper(this)

        val recyclerView = findViewById<RecyclerView>(R.id.rv_album)
        adapter = AlbumAdapter(baseDeDatos.obtenerTodosLosAlbunes(), baseDeDatos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        AlbumViewHolder.setAdapter(adapter)
        EditarAlbum.setAdapter(adapter)

        val botonCrearAlbum = findViewById<Button>(R.id.btn_crear_album)
        botonCrearAlbum.setOnClickListener {
            val intent = Intent(this, CrearAlbum::class.java)
            startActivityForResult(intent, CREAR_ALBUM)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        baseDeDatos = SQLiteHelper(this)

        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREAR_ALBUM && resultCode == Activity.RESULT_OK && data != null) {
            val albumTitulo = data.getStringExtra("albumTitulo")
            val albumArtista = data.getStringExtra("albumArtista")
            val albumAnioLazamiento = data.getStringExtra("anioLanzamiento")
            val albumEsExplicito = data.getBooleanExtra("esExplicito", false)

            val nuevoAlbum = BAlbum(
                id = null,
                titulo = albumTitulo,
                artista = albumArtista,
                añoDeLanzamiento = albumAnioLazamiento,
                esExplicito = albumEsExplicito
            )
            baseDeDatos.crearAlbum(nuevoAlbum)
            val listAlbumes = baseDeDatos.obtenerTodosLosAlbunes()
            adapter.actualizar(listAlbumes)
        }
    }


    companion object {
        private const val CREAR_ALBUM = 1
    }
}