package com.example.movilessoftware2023

class BEntrenador(
    var id: Int,
    var nombre: String?,
    var descripcion: String?,
) {
    override fun toString(): String {
        return "${id} - ${nombre} - ${descripcion}"
    }

}