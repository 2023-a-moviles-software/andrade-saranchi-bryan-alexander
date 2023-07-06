package com.example.movilessoftware2023

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    val callBackIntentPickUri =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode === RESULT_OK){
                if(result.data != null){
                    val uri : Uri = result.data!!.data!!
                    val cursor = contentResolver.query(uri, null,null, null, null, null)
                    cursor?.moveToFirst()
                    val indiceTelefono = cursor?.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                    )
                    val telefono = cursor?.getString(indiceTelefono!!)
                    cursor?.close()
                    "Telefono ${telefono}"
                }
            }
        }

    val callBackContenidoImplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    //Logica de negocio
                    val data = result.data
                    "${data?.getStringExtra("nombreModificado")}"
                }
            }
        }
    fun abrirActividadConParametros(
        clase: Class<*>
    ){
        val intentExplicito = Intent(this,clase)
        //Enviar parametros
        //(aceptamos primitivas)
        intentExplicito.putExtra("nombre", "Adrian")
        intentExplicito.putExtra("apellido", "Eguez")
        intentExplicito.putExtra("edad", 30)
        //enviamos intent con RESPUESTA
        callBackContenidoImplicito
            .launch(intentExplicito)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Base de datos SQLite
        EBaseDeDatos.tablaEntrenador = ESqliteHelperEntrenador(this)

        val botonCicloVida = findViewById<Button>(
            R.id.btn_ciclo_vida
        )
        botonCicloVida.setOnClickListener{
            irActividad(AACicloVida::class.java)
        }
        val botonListView = findViewById<Button>(
            R.id.btn_ir_list_view
        )
        botonListView.setOnClickListener{
            irActividad(BListView::class.java)
        }
        val botonIntentImplicito = findViewById<Button>(R.id.btn_ir_intent_implicito)
        botonIntentImplicito.setOnClickListener{
            val intentConRespuesta = Intent(
                Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            )
            callBackIntentPickUri.launch(intentConRespuesta)
        }
        val botonIntentExplicito = findViewById<Button>(R.id.btn_ir_intent_explicito)
        botonIntentExplicito
            .setOnClickListener{
                abrirActividadConParametros(CIntentExplicitoParametros::class.java)
            }


    }



    fun irActividad(
        clase: Class <*>
    ){
        val intent = Intent(this, clase)
        //NO RECIBIMOS RESPUESTA
        startActivity(intent)
    }



}

