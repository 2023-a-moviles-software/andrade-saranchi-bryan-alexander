package com.example.exameniib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CrearAlbum : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private var documentoId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_album)
        db = Firebase.firestore
        val albumTitulo = findViewById<EditText>(R.id.input_titulo_album)
        val albumArtista = findViewById<EditText>(R.id.input_artista_album)
        val albumAnioLanzamiento = findViewById<EditText>(R.id.input_año_lanzamiento_album)
        val btnCrearAlbum = findViewById<Button>(R.id.btn_guardar_album)
        btnCrearAlbum.setOnClickListener {
            val album = hashMapOf(
                "titulo" to albumTitulo.text.toString(),
                "artista" to albumArtista.text.toString(),
                "anioLanzamiento" to albumAnioLanzamiento.text.toString(),
                "esExplicito" to validar()
            )
            db.collection("album")
                .add(album)
                .addOnSuccessListener {
                    finish()
                }
                .addOnFailureListener {
                }
        }
    }
    fun validar(): Boolean {
        val radioSi = findViewById<RadioButton>(R.id.rbtn_si_explicito)
        val radioNo = findViewById<RadioButton>(R.id.rbtn_no_expliccto)
        var bandera = false
        if (radioSi.isChecked() == true) {
            bandera = true
        }
        if (radioNo.isChecked()) {
            bandera = false
        }
        return bandera
    }
}
