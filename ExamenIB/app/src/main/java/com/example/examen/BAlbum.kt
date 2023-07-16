package com.example.examen

class BAlbum(
    val id: Int,
    var titulo: String,
    var artista: String,
    var añoDeLanzamiento: String,
    var esExplicito: Boolean,
    var canciones: ArrayList<BCancion>
) {
    override fun toString(): String {
        return "${titulo} - ${artista} - ${añoDeLanzamiento} - ${esExplicito}"
    }

}