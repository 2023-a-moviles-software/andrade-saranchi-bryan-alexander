package com.example.examen

data class BCancion (
    val id: Int,
    var nombreCancion: String,
    var artista: String,
    var duracion: Double,
    var añoLanzamiento: String,
){
    override fun toString(): String {
        return "${id} - ${nombreCancion} - ${artista} - ${duracion} - ${añoLanzamiento}"
    }
}