package com.example.deber03.Adapter

import android.content.Intent
import android.view.ContextMenu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.TextureView
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.deber03.BCancion
import com.example.deber03.EditarCancion
import com.example.deber03.R
import com.example.deber03.SQLiteHelper

class CancionViewHolder(view: View, baseDeDatos : SQLiteHelper) : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

    val cancionId2 = view.findViewById<TextView>(R.id.tv_cancionId)
    val cancionNombre = view.findViewById<TextView>(R.id.tv_cancionName)
    val cancionArtista = view.findViewById<TextView>(R.id.tv_cancionArtista)
    val cancionAnioLanzamiento = view.findViewById<TextView>(R.id.tv_cancionAnioLanzamiento)
    val cancionDuracion = view.findViewById<TextView>(R.id.tv_cancionDuracion)
    private val baseDeDatos : SQLiteHelper = baseDeDatos


    init {
        view.setOnCreateContextMenuListener(this)
    }
    companion object{
        private lateinit var adapter : CancionAdapter
        private var albumId : Int = 0
        fun setAdapter(cancionAdapter: CancionAdapter){
            adapter = cancionAdapter
        }
        fun setAlbumId(id: Int){
            albumId = id
        }

    }

    fun render(cancion : BCancion){
        cancionId2.text = cancion.id.toString()
        cancionNombre.text = cancion.nombreCancion
        cancionArtista.text = cancion.artista
        cancionAnioLanzamiento.text = cancion.añoLanzamiento
        cancionDuracion.text = "Duracion ${cancion.duracion.toString()}"
    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        val inflater = MenuInflater(v?.context)
        inflater.inflate(R.menu.cancion_menu,menu)
        if(menu != null){
            for(i in 0 until menu.size()){
                menu.getItem(i).setOnMenuItemClickListener(this)
            }
        }
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mi_editar ->{
                val cancionId = cancionId2.text.split("")[1].toInt()
                val intent = Intent(itemView.context,EditarCancion::class.java)
                intent.putExtra("cancionId",cancionId)
                itemView.context.startActivity(intent)
                true
        }
            R.id.mi_eliminar -> {
                val albumId = albumId
                val cancionId = cancionId2.text.split("")[1].toInt()
                baseDeDatos.eliminarCancion(albumId,cancionId)
                val nuevoDatos = baseDeDatos.obtenerCancionPorAlbumId(albumId)
                adapter.actualizarCanciones(nuevoDatos)
                true
            }
            else -> false
        }

    }


}