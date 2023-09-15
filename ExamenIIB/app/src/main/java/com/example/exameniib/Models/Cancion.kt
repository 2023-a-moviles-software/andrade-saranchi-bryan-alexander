package com.example.exameniib.Models

data class Cancion(
    val id: String?,
    var nombreCancion: String?,
    var artista: String?,
    var duracion: Double?,
    var añoLanzamiento: String?,
){
    override fun toString(): String {
        return "Nombre : ${nombreCancion} - Duracion : ${duracion} - Año de lanzamiento : ${añoLanzamiento}"
    }
}