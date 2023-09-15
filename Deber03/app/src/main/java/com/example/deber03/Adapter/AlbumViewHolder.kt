package com.example.deber03.Adapter

import android.content.Intent
import android.view.ContextMenu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.deber03.BAlbum
import com.example.deber03.CancionRecyclerView
import com.example.deber03.EditarAlbum
import com.example.deber03.R
import com.example.deber03.SQLiteHelper

class AlbumViewHolder(view: View, baseDeDatos:SQLiteHelper):RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener,
    MenuItem.OnMenuItemClickListener {

    private val albumId = view.findViewById<TextView>(R.id.tv_albumId)
    private val albumTitulo = view.findViewById<TextView>(R.id.tv_albumName)
    private val albumArtista = view.findViewById<TextView>(R.id.tv_albumArtista)
    private val albumAnioLanzamiento = view.findViewById<TextView>(R.id.tv_albumAnioLanzamiento)
    private val albumEsExplicito = view.findViewById<TextView>(R.id.tv_albumEsExplicito)
    private val baseDeDatos : SQLiteHelper = baseDeDatos

    init {
        view.setOnCreateContextMenuListener(this)
    }

    companion object{
        private lateinit var adapter : AlbumAdapter
        fun setAdapter(albumAdapter : AlbumAdapter){
            adapter = albumAdapter
        }
    }

    fun render(album : BAlbum){
        albumId.text = album.id.toString()
        albumTitulo.text = album.titulo
        albumArtista.text = album.artista
        albumAnioLanzamiento.text = album.añoDeLanzamiento
        albumEsExplicito.text = album.esExplicito.toString()
    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        val inflater = MenuInflater(v?.context)
        inflater.inflate(R.menu.album_menu,menu)

        if(menu != null){
            for ( i in 0 until menu.size()){
                menu.getItem(i).setOnMenuItemClickListener(this)
            }
        }

    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_ver_canciones -> {
                val aux = albumId.text.split("")[1].toInt()
                val titulo = albumTitulo.text.toString()
                val intent = Intent(itemView.context, CancionRecyclerView::class.java)
                intent.putExtra("albumId", aux)
                intent.putExtra("nombreAlbum",titulo)
                itemView.context.startActivity(intent)
                true
            }

            R.id.mi_editar ->{
                val aux = albumId.text.split("")[1].toInt()
                val intent = Intent(itemView.context, EditarAlbum::class.java)
                intent.putExtra("albumId", aux)
                itemView.context.startActivity(intent)
                true
            }

            R.id.mi_eliminar ->{
                val aux = albumId.text.split("")[1].toInt()
                baseDeDatos.eliminarAlbum(aux)
                val albumLista = baseDeDatos.obtenerTodosLosAlbunes()
                adapter.actualizar(albumLista)
                true
            }

            else -> false
        }
    }


}