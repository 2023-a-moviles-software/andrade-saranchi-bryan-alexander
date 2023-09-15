package com.example.exameniib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.firestore.FirebaseFirestore

class EditarCancion : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_cancion)
        val nombreAlbum = intent.getStringExtra("nombreA") ?: ""
        val nombreCancion = intent.getStringExtra("nombreC") ?: ""
        if (nombreAlbum.isNotEmpty() && nombreCancion.isNotEmpty()) {
            db.collection("album").whereEqualTo("titulo", nombreAlbum)
                .get()
                .addOnSuccessListener { documentosAlbum ->
                    if (documentosAlbum.documents.isNotEmpty()) {
                        val album = documentosAlbum.documents[0]
                        db.collection("album").document(album.id)
                            .collection("cancion").whereEqualTo("nombreCancion", nombreCancion)
                            .get()
                            .addOnSuccessListener { documentosCancion ->
                                if (documentosCancion.documents.isNotEmpty()) {
                                    val cancion = documentosCancion.documents[0]
                                    val cancionNombreNuevo =
                                        findViewById<EditText>(R.id.input_nuevo_nombre_cancion)
                                    val cancionArtistaNuevo =
                                        findViewById<EditText>(R.id.input_nuevo_artista_cancion)
                                    val cancionDuracionNuevo =
                                        findViewById<EditText>(R.id.input_nueva_duracion)
                                    val cancionAnioLanzamientoNuevo =
                                        findViewById<EditText>(R.id.input_nuevo_año_lanzamiento_cancion)
                                    cancionNombreNuevo.setText(cancion.getString("nombreCancion"))
                                    cancionArtistaNuevo.setText(cancion.getString("artista"))
                                    cancionDuracionNuevo.setText(
                                        cancion.getDouble("duracion").toString()
                                    )
                                    cancionAnioLanzamientoNuevo.setText(cancion.getString("anioLanzamiento"))
                                }
                            }
                    }
                }
                .addOnFailureListener {
                }
        }
        val btnEditarCancion = findViewById<Button>(R.id.btn_guardar_nuevos_cambios)
        btnEditarCancion
            .setOnClickListener {
                val cancionNombreNuevo =
                    findViewById<EditText>(R.id.input_nuevo_nombre_cancion).text.toString()
                val cancionArtistaNuevo =
                    findViewById<EditText>(R.id.input_nuevo_artista_cancion).text.toString()
                val cancionDuracionNuevo =
                    findViewById<EditText>(R.id.input_nueva_duracion).text.toString().toDouble()
                val cancionAnioLanzamientoNuevo =
                    findViewById<EditText>(R.id.input_nuevo_año_lanzamiento_cancion).text.toString()

                db.collection("album")
                    .whereEqualTo("titulo", nombreAlbum)
                    .get()
                    .addOnSuccessListener { documentos ->
                        if (documentos.documents.isNotEmpty()) {
                            val albumId = documentos.documents[0].id
                            db.collection("album").document(albumId)
                                .collection("cancion")
                                .whereEqualTo("nombreCancion", nombreCancion)
                                .get()
                                .addOnSuccessListener { documentosCancion ->
                                    if (documentosCancion.documents.isNotEmpty()) {
                                        val cancionId = documentosCancion.documents[0].id
                                        db.collection("album").document(albumId)
                                            .collection("cancion").document(cancionId)
                                            .update(
                                                "nombreCancion", cancionNombreNuevo,
                                                "artista", cancionArtistaNuevo,
                                                "duracion", cancionDuracionNuevo,
                                                "anioLanzamiento", cancionAnioLanzamientoNuevo
                                            )
                                            .addOnSuccessListener {
                                                finish()
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
    }
}
