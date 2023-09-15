package com.example.deber03

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class CrearCancion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cancion)
        val albumId = intent.getIntExtra("posicionAlbum",-1)

        val cancionNombre = findViewById<TextView>(R.id.input_nombre_cancion)
        val cancionArtista = findViewById<TextView>(R.id.input_artista_cancion)
        val cancionDuracion = findViewById<TextView>(R.id.input_duracion_cancion)
        val cancionAnioLanzamiento = findViewById<TextView>(R.id.input_año_lanzamiento_cancion)
        val btnCrearCancion = findViewById<Button>(R.id.btn_guardar_cancion)
        btnCrearCancion
            .setOnClickListener {
                val cancionN = cancionNombre.text.toString()
                val cancionAr = cancionArtista.text.toString()
                val cancionDu = cancionDuracion.text.toString()
                val cancionAn = cancionAnioLanzamiento.text.toString()

                val intent = Intent()
                intent.putExtra("cancionNombre", cancionN)
                intent.putExtra("cancionArtista",cancionAr)
                intent.putExtra("cancionDuracion", cancionDu)
                intent.putExtra("cancionAnioLanzamiento", cancionAn)
                intent.putExtra("albumId", albumId)

                setResult(
                    RESULT_OK,
                    intent
                )

                finish()
            }
    }
}