package com.example.examen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton

class CrearAlbum() : AppCompatActivity(){

    val arreglo = BaseDeDatosMemoria.albumBaseDeDatos
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_album)
        val botonGuardarAlbum = findViewById<Button>(R.id.btn_guardar_album)
        botonGuardarAlbum
            .setOnClickListener {
                enviarDatos()
            }
    }

    fun enviarDatos(){
        val intent = Intent(this, AlbumListView::class.java)
        val id = findViewById<EditText>(R.id.input_id_album).text.toString()
        val titulo = findViewById<EditText>(R.id.input_titulo_album).text.toString()
        val artista = findViewById<EditText>(R.id.input_artista_album).text.toString()
        val añoLanzamiento = findViewById<EditText>(R.id.input_año_lanzamiento_album).text.toString()
        val esExplicito = validar().toString()
        intent.putExtra("id",id)
        intent.putExtra("titulo",titulo)
        intent.putExtra("artista",artista)
        intent.putExtra("añoLanzamiento",añoLanzamiento)
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