package com.example.exameniib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.firestore.FirebaseFirestore

class CrearCancion : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cancion)
        val nombreAlbum = intent.getStringExtra("nombreA") ?: ""
        val btnCrearCancion = findViewById<Button>(R.id.btn_guardar_cancion)
        btnCrearCancion.setOnClickListener {
            val cancionNombre = findViewById<EditText>(R.id.input_nombre_cancion).text.toString()
            val cancionArtista = findViewById<EditText>(R.id.input_artista_cancion).text.toString()
            val cancionDuracion =
                findViewById<EditText>(R.id.input_duracion_cancion).text.toString().toDouble()
            val cancionAnioLanzamiento =
                findViewById<EditText>(R.id.input_año_lanzamiento_cancion).text.toString()
            val nuevaCancion = hashMapOf(
                "nombreCancion" to cancionNombre,
                "artista" to cancionArtista,
                "duracion" to cancionDuracion,
                "anioLanzamiento" to cancionAnioLanzamiento
            )
            db.collection("album")
                .whereEqualTo("titulo", nombreAlbum)
                .get()
                .addOnSuccessListener { documentos ->
                    if (documentos.documents.isNotEmpty()) {
                        val albumId = documentos.documents[0].id
                        //Subcoleccion
                        db.collection("album").document(albumId)
                            .collection("cancion")
                            .add(nuevaCancion)
                            .addOnSuccessListener {
                                finish()
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