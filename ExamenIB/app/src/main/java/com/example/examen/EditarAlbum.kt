package com.example.examen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton

class EditarAlbum : AppCompatActivity() {
    val albumArreglo = BaseDeDatosMemoria.albumBaseDeDatos
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_album)

        val posicionAlbum = intent.getIntExtra("albumPosicion",-1)
        val albumAEditar = albumArreglo[posicionAlbum]
        val titulo = findViewById<EditText>(R.id.input_nuevo_titulo)
        val artista = findViewById<EditText>(R.id.input_nuevo_artista)
        val añoLanzamiento = findViewById<EditText>(R.id.input_nuevo_año)
        val esExplicito = albumAEditar.esExplicito
        titulo.setText(albumAEditar.titulo)
        artista.setText(albumAEditar.artista)
        añoLanzamiento.setText(albumAEditar.añoDeLanzamiento)
        asignarRadioButton(esExplicito)


        val botonGuardarCambios = findViewById<Button>(R.id.btn_guardar_cambios)
        botonGuardarCambios
            .setOnClickListener {
                val nuevoTitulo = titulo.text.toString()
                val nuevoArtista = artista.text.toString()
                val nuevoAñoLanzamiento = añoLanzamiento.text.toString()
                val esExplicito = validar().toString()
                enviarDatos(nuevoTitulo,nuevoArtista,nuevoAñoLanzamiento,esExplicito,posicionAlbum)
            }

    }
    fun asignarRadioButton(explicito : Boolean):Boolean{
        val radioSi = findViewById<RadioButton>(R.id.rbtn_si)
        val radioNo = findViewById<RadioButton>(R.id.rbtn_no)
        var bandera = false
        if(explicito){
            radioSi.setChecked(true)
            bandera = true
        }
        else{
            radioNo.setChecked(true)
            bandera = false
        }
        return bandera
    }

    fun enviarDatos(nuevoTitulo:String, nuevoArtista:String, nuevoAñoLanzamiento:String, nuevoEsExplicito:String,posicionAlbum:Int){
        val intent = Intent()
        intent.putExtra("nuevoTitulo", nuevoTitulo)
        intent.putExtra("nuevoArtista", nuevoArtista)
        intent.putExtra("nuevoAñoLanzamiento", nuevoAñoLanzamiento)
        intent.putExtra("nuevoEsExplicito",nuevoEsExplicito)
        intent.putExtra("posicionAlbum",posicionAlbum)
        setResult(
            RESULT_OK,
            intent
        )
        finish()
        }

    fun validar():Boolean{
        val radioSi = findViewById<RadioButton>(R.id.rbtn_si)
        val radioNo = findViewById<RadioButton>(R.id.rbtn_no)
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