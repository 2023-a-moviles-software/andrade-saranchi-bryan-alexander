package com.example.exameniib.Models

data class Album (
    var id :String?,
    var titulo: String?,
    var artista: String?,
    var añoDeLanzamiento: String?,
    var esExplicito: Boolean,
){
    override fun toString(): String {
        return "Titulo : ${titulo}  - Artista : ${artista} - Año de lanzamiento : ${añoDeLanzamiento}"
    }
}