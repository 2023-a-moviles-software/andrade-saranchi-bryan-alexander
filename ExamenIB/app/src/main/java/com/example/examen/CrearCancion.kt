package com.example.examen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class CrearCancion : AppCompatActivity() {
    val albumArreglo = BaseDeDatosMemoria.albumBaseDeDatos
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cancion)

        val posicionAlbum = intent.getIntExtra("posicionAlbum",-1)
        val tvArtista = findViewById<EditText>(R.id.input_artista_cancion)
        val artistaInicial = albumArreglo[posicionAlbum].artista
        tvArtista.setText(artistaInicial)
        val botonGuardarCancion = findViewById<Button>(R.id.btn_guardar_cancion)
        botonGuardarCancion
            .setOnClickListener{
                enviarDatos(posicionAlbum)
            }

    }

    fun enviarDatos(posicionAlbum : Int){
        val intent = Intent(this, CancionListView::class.java)
        val id = findViewById<EditText>(R.id.input_id_cancion).text.toString()
        val nombreCancion = findViewById<EditText>(R.id.input_nombre_cancion).text.toString()
        val artista = findViewById<EditText>(R.id.input_artista_cancion).text.toString()
        val duracion = findViewById<EditText>(R.id.input_duracion_cancion).text.toString()
        val añoLanzamiento = findViewById<EditText>(R.id.input_año_lanzamiento_cancion).text.toString()
        val posicion = posicionAlbum
        intent.putExtra("id",id)
        intent.putExtra("nombreCancion",nombreCancion)
        intent.putExtra("artista", artista)
        intent.putExtra("duracion",duracion)
        intent.putExtra("añoLanzamiento",añoLanzamiento)
        intent.putExtra("posicionAlbum", posicion)
        setResult(
            RESULT_OK,
            intent
        )

        finish()
    }

}