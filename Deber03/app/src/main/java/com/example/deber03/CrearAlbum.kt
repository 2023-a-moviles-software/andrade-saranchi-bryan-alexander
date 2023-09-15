package com.example.deber03

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import com.example.deber03.Adapter.AlbumAdapter

class CrearAlbum : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_album)
        val botonGuardarDatos = findViewById<Button>(R.id.btn_guardar_album)
        botonGuardarDatos
            .setOnClickListener {
                enviarDatos()
            }

    }

    fun enviarDatos(){
        val intent = Intent(this, MainActivity::class.java)
        val titulo = findViewById<EditText>(R.id.input_titulo_album).text.toString()
        val artista = findViewById<EditText>(R.id.input_artista_album).text.toString()
        val añoLanzamiento = findViewById<EditText>(R.id.input_año_lanzamiento_album).text.toString()
        val esExplicito = validar().toString()
        intent.putExtra("albumTitulo",titulo)
        intent.putExtra("albumArtista",artista)
        intent.putExtra("anioLanzamiento",añoLanzamiento)
        intent.putExtra("esExplicito",esExplicito)

        setResult(
            RESULT_OK,
            intent
        )

        finish()
    }

    fun validar():Boolean{
        val radioSi = findViewById<RadioButton>(R.id.rbtn_si_explicito)
        val radioNo = findViewById<RadioButton>(R.id.rbtn_no_expliccto)
        var bandera = false
        if(radioSi.isChecked()==true){
            bandera = true
        }
        if (radioNo.isChecked()){
            bandera = false
        }
        return  bandera
    }
}