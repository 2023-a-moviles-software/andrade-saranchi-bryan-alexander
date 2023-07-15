package com.example.examen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.text.set

class EditarCancion : AppCompatActivity() {
    val albumArreglo = BaseDeDatosMemoria.albumBaseDeDatos
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_cancion)

        val posicionCancion = intent.getIntExtra("posicionCancion",-1)
        val posicionAlbum = intent.getIntExtra("posicionAlbum",-1)

        val cancionAEditar = albumArreglo[posicionAlbum].canciones[posicionCancion]


        val nombreCancion = findViewById<EditText>(R.id.input_nuevo_nombre_cancion)
        val artista = findViewById<EditText>(R.id.input_nuevo_artista_cancion)
        val duracion = findViewById<EditText>(R.id.input_nueva_duracion)
        val añoLanzamiento = findViewById<EditText>(R.id.input_nuevo_año_lanzamiento_cancion)
        nombreCancion.setText(cancionAEditar.nombreCancion)
        artista.setText(cancionAEditar.artista)
        duracion.setText(cancionAEditar.duracion.toString())
        añoLanzamiento.setText(cancionAEditar.añoLanzamiento)


        val botonGuardarCambios = findViewById<Button>(R.id.btn_guardar_nuevos_cambios)
        botonGuardarCambios
            .setOnClickListener {
                val nuevoNombreCancion=nombreCancion.text.toString()
                val nuevoArtista = artista.text.toString()
                val nuevaDuracion = duracion.text.toString()
                val nuevoAñoLanzamiento = añoLanzamiento.text.toString()
                enviarDatos(nuevoNombreCancion,nuevoArtista,nuevaDuracion,nuevoAñoLanzamiento,posicionCancion)
            }

    }


    fun enviarDatos(nuevoNombreCancion:String, nuevoArtista:String,nuevaDuracion:String ,nuevoAñoLanzamiento:String,posicionCancion:Int){
        val intent = Intent()
        intent.putExtra("nuevoArtista", nuevoArtista)
        intent.putExtra("nuevoNombreCancion", nuevoNombreCancion)
        intent.putExtra("nuevoAñoLanzamiento", nuevoAñoLanzamiento)
        intent.putExtra("nuevaDuracion",nuevaDuracion)
        intent.putExtra("posicionCancion",posicionCancion)
        setResult(
            RESULT_OK,
            intent
        )
        finish()
    }
















}
