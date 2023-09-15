package com.example.deber03

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import com.example.deber03.Adapter.AlbumAdapter

class EditarAlbum : AppCompatActivity() {
    private lateinit var baseDeDatos : SQLiteHelper
    companion object{
        private lateinit var adapter : AlbumAdapter

        fun setAdapter(albumAdapter: AlbumAdapter){
            adapter = albumAdapter
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_album)

        baseDeDatos = SQLiteHelper(this)
        val albumId = intent.getIntExtra("albumId", -1)
        val album = albumId?.let { baseDeDatos.obtenerAlbumPorId(albumId) }


        val titulo = findViewById<EditText>(R.id.input_nuevo_titulo)
        val artista = findViewById<EditText>(R.id.input_nuevo_artista)
        val añoLanzamiento = findViewById<EditText>(R.id.input_nuevo_año)
        val esExplicito = album?.esExplicito

        titulo.setText(album?.titulo)
        artista.setText(album?.artista)
        añoLanzamiento.setText(album?.añoDeLanzamiento)
        if (esExplicito != null) {
            asignarRadioButton(esExplicito)
        }

        val botonGuardarCambios = findViewById<Button>(R.id.btn_guardar_cambios)
        botonGuardarCambios
            .setOnClickListener {
                album?.titulo = titulo.text.toString()
                album?.artista = artista.text.toString()
                album?.añoDeLanzamiento = añoLanzamiento.text.toString()
                album?.esExplicito = validar()
                album?.id?.let{ it ->
                    baseDeDatos.actualizarAlbum(album)

                }
                val albumList = baseDeDatos.obtenerTodosLosAlbunes()
                adapter.actualizar(albumList)
                finish()
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