package com.example.deber03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.deber03.Adapter.CancionAdapter

class EditarCancion : AppCompatActivity() {
    private lateinit var baseDeDatos: SQLiteHelper

    companion object {
        private lateinit var adapter: CancionAdapter

        fun setAdapter(cancionAdapter: CancionAdapter) {
            adapter = cancionAdapter
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_cancion)
        val cancionId = intent.getIntExtra("cancionId",-1)
        baseDeDatos = SQLiteHelper(this)
        val cancionNombre = findViewById<TextView>(R.id.input_nuevo_nombre_cancion)
        val cancionArtista = findViewById<TextView>(R.id.input_nuevo_artista_cancion)
        val cancionDuracion = findViewById<TextView>(R.id.input_nueva_duracion)
        val cancionAnioLanzamiento = findViewById<TextView>(R.id.input_nuevo_año_lanzamiento_cancion)
        val btnCancionEditada = findViewById<Button>(R.id.btn_guardar_nuevos_cambios)

        val albumCancion = baseDeDatos.obtenerCancionYAlbumPorId(cancionId)

        val cancion = albumCancion?.cancion
        val albumId = albumCancion?.albumId

        cancionNombre.text = cancion?.nombreCancion
        cancionArtista.text = cancion?.artista
        cancionDuracion.text = cancion?.duracion.toString()
        cancionAnioLanzamiento.text = cancion?.añoLanzamiento

        btnCancionEditada
            .setOnClickListener {
                if(albumCancion != null){
                    baseDeDatos.actualizarCancion(
                        BCancion(
                            cancionId,
                            cancionNombre.text.toString(),
                            cancionArtista.text.toString(),
                            cancionDuracion.text.toString().toDouble(),
                            cancionAnioLanzamiento.text.toString()
                        )
                    )
                    val cancionList = baseDeDatos.obtenerCancionPorAlbumId(albumId)
                    adapter.actualizarCanciones(cancionList)
                    finish()
                }
            }






    }
}