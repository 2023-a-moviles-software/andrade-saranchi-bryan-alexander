package com.example.exameniib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditarAlbum : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private var albumT: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_album)
        db = Firebase.firestore
        val albumTitulo = findViewById<EditText>(R.id.input_nuevo_titulo)
        val albumArtista = findViewById<EditText>(R.id.input_nuevo_artista)
        val albumAnioLanzamiento = findViewById<EditText>(R.id.input_nuevo_año)
        val valorId = findViewById<TextView>(R.id.tv_valor_id)
        val tituloAlbum = intent.getStringExtra("nombre")
        db.collection("album")
            .whereEqualTo("titulo", tituloAlbum)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.documents.isNotEmpty()) {
                    val albumAUX = documents.documents[0]
                    albumTitulo.setText(albumAUX.getString("titulo"))
                    albumArtista.setText(albumAUX.getString("artista"))
                    albumAnioLanzamiento.setText(albumAUX.getString("anioLanzamiento"))
                    albumAUX.getBoolean("esExplicito")?.let { asignarRadioButton(it) }
                    valorId.text = albumAUX.id
                    albumT = albumAUX.id
                }
            }.addOnFailureListener { }


        val btnActualizarAlbum = findViewById<Button>(R.id.btn_guardar_cambios)
        btnActualizarAlbum
            .setOnClickListener {
                val albumTitulo = findViewById<EditText>(R.id.input_nuevo_titulo)
                val albumArtista = findViewById<EditText>(R.id.input_nuevo_artista)
                val albumAnioLanzamiento = findViewById<EditText>(R.id.input_nuevo_año)
                val valorId = findViewById<TextView>(R.id.tv_valor_id)
                albumT = valorId.text.toString()
                albumT?.let { id ->
                    val albumActualizado = hashMapOf(
                        "titulo" to albumTitulo.text.toString(),
                        "artista" to albumArtista.text.toString(),
                        "anioLanzamiento" to albumAnioLanzamiento.text.toString(),
                        "esExplicito" to validar()
                    )
                    db.collection("album")
                        .document(id)
                        .update(albumActualizado as Map<String, Any>)
                        .addOnSuccessListener {
                            finish()
                        }
                        .addOnFailureListener {
                        }
                }
            }
    }

    fun asignarRadioButton(explicito: Boolean): Boolean {
        val radioSi = findViewById<RadioButton>(R.id.rbtn_si)
        val radioNo = findViewById<RadioButton>(R.id.rbtn_no)
        var bandera = false
        if (explicito) {
            radioSi.setChecked(true)
            bandera = true
        } else {
            radioNo.setChecked(true)
            bandera = false
        }
        return bandera
    }

    fun validar(): Boolean {
        val radioSi = findViewById<RadioButton>(R.id.rbtn_si)
        val radioNo = findViewById<RadioButton>(R.id.rbtn_no)
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