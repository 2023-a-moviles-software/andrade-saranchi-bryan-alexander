package com.example.movilessoftware2023

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import org.w3c.dom.Text

class ECrudEntrenador : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ecrud_entrenador)

        val buttonBuscarBDD = findViewById<Button>(R.id.btn_buscar_bdd)
        buttonBuscarBDD
            .setOnClickListener {
                val id = findViewById<EditText>(R.id.input_id)
                val nombre = findViewById<EditText>(R.id.input_nombre)
                val descripcion = findViewById<EditText>(R.id.input_descripcion)
                val entrenador = EBaseDeDatos.tablaEntrenador!!
                    .consultarEntrenadorPorID(
                        id.text.toString().toInt()
                    )
                id.setText(entrenador.id.toString())
                nombre.setText(entrenador.nombre)
                descripcion.setText(entrenador.descripcion)
            }

        val botonCrearBDD = findViewById<Button>(R.id.btn_crear_bdd)
        botonCrearBDD
            .setOnClickListener{
                val nombre = findViewById<EditText>(R.id.input_nombre)
                val descripcion = findViewById<EditText>(R.id.input_descripcion)
                EBaseDeDatos.tablaEntrenador!!.crearEntrenador(
                    nombre.text.toString(),
                    descripcion.text.toString()
                )
            }

        val botonActualizarBDD = findViewById<Button>(R.id.btn_actualizar_bdd)
        botonActualizarBDD
            .setOnClickListener{
                val id = findViewById<EditText>(R.id.input_id)
                val nombre = findViewById<EditText>(R.id.input_nombre)
                val descripcion = findViewById<EditText>(R.id.input_descripcion)
                EBaseDeDatos.tablaEntrenador!!.actualizarEntrenadorFormulario(
                    nombre.text.toString(),
                    descripcion.text.toString(),
                    id.text.toString().toInt()
                )
            }

        val botonEliminarBDD = findViewById<Button>(R.id.btn_elminar_bdd)
        botonEliminarBDD
            .setOnClickListener{
                val id = findViewById<EditText>(R.id.input_id)
                EBaseDeDatos.tablaEntrenador!!.eliminarEntrenadorFormulario(
                    id.text.toString().toInt()
                )
            }

    }


}