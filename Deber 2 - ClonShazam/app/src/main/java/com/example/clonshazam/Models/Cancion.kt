package com.example.clonshazam.Models

data class Cancion(
    val artista: String,
    val pista: String,
    val album: String,
    val selloDiscrográfico: String,
    val añoPublicado: String,
    val ubicacion: String,
    val shazameado: String?,
    val photo: String,
)