package com.example.examen

class BaseDeDatosMemoria {
    companion object{
        var albumBaseDeDatos = arrayListOf<BAlbum>()
        val cancionesAlbum1 = arrayListOf<BCancion>()
        val cancionesAlbum2 = arrayListOf<BCancion>()
        init {
            cancionesAlbum1.add(BCancion(1, "Latinoamerica","Calle 13",5.01, "2011/09/27"))
            cancionesAlbum1.add(BCancion(2, "La vuelta al mundo", "Calle 13", 3.54, "2010/05/15"))
            albumBaseDeDatos
                .add(
                    BAlbum(1, "Entren los que quieran","Calle 13", "2010/10/26", true, cancionesAlbum1)
                )
            cancionesAlbum2.add(BCancion(1,"Disfruto", "Carla Morrison", 4.04,"2013/03/10"))
            albumBaseDeDatos
                .add(
                    BAlbum(2, "Dejenme mirar", "Carla Morrison", "2012/04/26",false, cancionesAlbum2)
                )
        }
    }



}