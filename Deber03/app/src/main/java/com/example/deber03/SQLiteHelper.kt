package com.example.deber03

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.core.content.contentValuesOf

class SQLiteHelper(context: Context): SQLiteOpenHelper(
    context,
    "album",
    null,
    1
) {
    companion object{
        private const val DATABASE_NAME = "album.db"

        //ALBUM
        private const val TABLE_ALBUM = "album"
        private const val COLUMN_ALBUM_ID = "albumId"
        private const val COLUMN_ALBUM_TITULO = "albumTitulo"
        private const val COLUMN_ALBUM_ARTISTA = "albumArtista"
        private const val COLUMN_ALBUM_ANIOLANZAMIENTO = "albumAnioLanzamiento"
        private const val COLUMN_ALBUM_ESEXPLICITO = "albumEsExplicito"

        //CANCION
        private const val TABLE_CANCION = "cancion"
        private const val COLUMN_CANCION_ID = "cancionID"
        private const val COLUMN_CANCION_NOMBRE = "cancionNombre"
        private const val COLUMN_CANCION_ARTISTA = "cancionArtista"
        private const val COLUMN_CANCION_DURACION = "cancionDuracion"
        private const val COLUMN_CANCION_ANIOLANZAMIENTO = "cancionAnioLanzamiento"
        private const val COLUMN_ALBUMID_FK = "albumId_fk"


    }
    override fun onCreate(db: SQLiteDatabase?) {
        val scripSQLCrearTablaAlbum =
            """
                CREATE TABLE $TABLE_ALBUM (
                $COLUMN_ALBUM_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_ALBUM_TITULO TEXT,
                $COLUMN_ALBUM_ARTISTA TEXT,
                $COLUMN_ALBUM_ANIOLANZAMIENTO TEXT,
                $COLUMN_ALBUM_ESEXPLICITO INTEGER
                );
            """.trimIndent()
        db?.execSQL(scripSQLCrearTablaAlbum)

        val scriptSQLCrearTablaCancion =
            """
                CREATE TABLE $TABLE_CANCION (
                $COLUMN_CANCION_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_CANCION_NOMBRE TEXT,
                $COLUMN_CANCION_ARTISTA TEXT,
                $COLUMN_CANCION_DURACION REAL,
                $COLUMN_CANCION_ANIOLANZAMIENTO TEXT,
                $COLUMN_ALBUMID_FK INTEGER,
                FOREIGN KEY($COLUMN_ALBUMID_FK) REFERENCES $TABLE_ALBUM($COLUMN_ALBUM_ID)
                );
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaCancion)

        val eliminarRegistro = """
            DELETE FROM $TABLE_CANCION
        """.trimIndent()
        db?.execSQL(eliminarRegistro)
    }

    //ALBUM

    fun crearAlbum(
        album : BAlbum
    ):Boolean{
        val baseDatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put(COLUMN_ALBUM_TITULO, album.titulo)
        valoresAGuardar.put(COLUMN_ALBUM_ARTISTA, album.artista)
        valoresAGuardar.put(COLUMN_ALBUM_ANIOLANZAMIENTO, album.añoDeLanzamiento)
        valoresAGuardar.put(COLUMN_ALBUM_ESEXPLICITO, transformarBooleanAInt(album.esExplicito))
        val resultadoGuardar = baseDatosEscritura
            .insert(TABLE_ALBUM,
                null,
                valoresAGuardar)
        baseDatosEscritura.close()
        if (resultadoGuardar != -1L) {

            Log.d("SQLite", "Registro creado con ID: $resultadoGuardar")
        } else {

            Log.e("SQLite", "Error al insertar el registro en la base de datos")
        }
        return if(resultadoGuardar.toInt() === -1) false else true
    }

    fun eliminarAlbum(
        id : Int
    ): Boolean{
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(TABLE_ALBUM,
                "$COLUMN_ALBUM_ID = ?",
                parametrosConsultaDelete)
        conexionEscritura.close()
        return if(resultadoEliminacion.toInt() === -1) false else true
    }

    fun actualizarAlbum(
        album : BAlbum
    ):Boolean{
        val conexionEscritura = this.writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put(COLUMN_ALBUM_TITULO, album.titulo)
        valoresAActualizar.put(COLUMN_ALBUM_ARTISTA, album.artista)
        valoresAActualizar.put(COLUMN_ALBUM_ANIOLANZAMIENTO, album.añoDeLanzamiento)
        valoresAActualizar.put(COLUMN_ALBUM_ESEXPLICITO, album.esExplicito)

        val parametrosConsultaActualizar = arrayOf(album.id.toString())
        val resultadoActualizacion = conexionEscritura
            .update(
                TABLE_ALBUM,
                valoresAActualizar,
                "$COLUMN_ALBUM_ID = ?",
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if (resultadoActualizacion.toInt() === -1) false else true
    }

    fun obtenerAlbumPorId(albumId : Int):BAlbum?{
        val conexionLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM $TABLE_ALBUM WHERE $COLUMN_ALBUM_ID = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(albumId.toString())
        val resultadoConsultaLectura = conexionLectura.rawQuery(
            scriptConsultaLectura,
            parametrosConsultaLectura
        )
        val existeAlbum = resultadoConsultaLectura.moveToFirst()
        val albumEncontrado = BAlbum(0,"","","",false)
        if(existeAlbum){
            do{
                val albumId = resultadoConsultaLectura.getInt(0)
                val albumTitulo = resultadoConsultaLectura.getString(1)
                val albumArtista = resultadoConsultaLectura.getString(2)
                val albumAnioLanzamiento = resultadoConsultaLectura.getString(3)
                val albumEsExplicito = resultadoConsultaLectura.getInt(4)
                if(albumId != null){
                    albumEncontrado.id = albumId
                    albumEncontrado.titulo = albumTitulo
                    albumEncontrado.artista = albumArtista
                    albumEncontrado.añoDeLanzamiento = albumAnioLanzamiento
                    albumEncontrado.esExplicito = transformarIntABoolean(albumEsExplicito)
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        conexionLectura.close()
        return albumEncontrado

    }
    fun transformarBooleanAInt(boolean: Boolean):Int{
        return if(boolean) 1 else 0
    }

    fun transformarIntABoolean(number : Int): Boolean{
        return if(number === 1) true else false
    }

    fun obtenerTodosLosAlbunes(): List<BAlbum>
    {
        val albumList : ArrayList<BAlbum> = ArrayList()
        val query = """
            SELECT * FROM $TABLE_ALBUM
        """.trimIndent()
        val conexionLectura = readableDatabase
        val resultadoConsultaLectura = conexionLectura
            .rawQuery(query,null)
        if(resultadoConsultaLectura.moveToFirst()){
            do{
                val id = resultadoConsultaLectura.getInt(0)
                val titulo = resultadoConsultaLectura.getString(1)
                val artista = resultadoConsultaLectura.getString(2)
                val anioLanzamiento = resultadoConsultaLectura.getString(3)
                val esExplicito = resultadoConsultaLectura.getInt(4)

                val booleanEsExplicito = transformarIntABoolean(esExplicito)
                val album = BAlbum(id,titulo,artista,anioLanzamiento, booleanEsExplicito)
                albumList.add(album)
            }while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        conexionLectura.close()
        return albumList
    }

    //CANCION
    fun crearCancion(
        cancion : BCancion, albumId : Int
    ){
        val baseDatosEscritura = writableDatabase
        val valoresAGuardar = contentValuesOf()
        valoresAGuardar.put(COLUMN_CANCION_NOMBRE, cancion.nombreCancion)
        valoresAGuardar.put(COLUMN_CANCION_ARTISTA, cancion.artista)
        valoresAGuardar.put(COLUMN_CANCION_DURACION,cancion.duracion)
        valoresAGuardar.put(COLUMN_CANCION_ANIOLANZAMIENTO,cancion.añoLanzamiento)
        valoresAGuardar.put(COLUMN_ALBUMID_FK, albumId)

        baseDatosEscritura.insert(TABLE_CANCION, null, valoresAGuardar)
        baseDatosEscritura.close()
    }

    fun eliminarCancion(albumId : Int, cancionId : Int){
        val baseDatosEscritura = this.writableDatabase
        val query = "$COLUMN_ALBUMID_FK = ? AND $COLUMN_CANCION_ID = ?"
        val parametrosConsultaDelete = arrayOf(albumId.toString(), cancionId.toString())
        val resultadoEliminacion = baseDatosEscritura
            .delete(
                TABLE_CANCION,
                query,
                parametrosConsultaDelete
            )
        baseDatosEscritura.close()
    }

    fun actualizarCancion(
        cancion : BCancion

    ):Boolean{
        val conexionEscritura = this.writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put(COLUMN_CANCION_NOMBRE, cancion.nombreCancion)
        valoresAActualizar.put(COLUMN_CANCION_ARTISTA,cancion.artista)
        valoresAActualizar.put(COLUMN_CANCION_DURACION,cancion.duracion)
        valoresAActualizar.put(COLUMN_CANCION_ANIOLANZAMIENTO,cancion.añoLanzamiento)

        val selection = "$COLUMN_CANCION_ID = ?"
        val parametrosConsultaActualizar = arrayOf(cancion.id.toString())

        val resultadoActualizar = conexionEscritura
            .update(
                TABLE_CANCION,
                valoresAActualizar,
                selection,
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if (resultadoActualizar.toInt() === -1)false else true
    }

    fun obtenerCancionYAlbumPorId(cancionId: Int): BAlbumCancion?{
        val conexionLectura = this.readableDatabase
        var cancionAlbum : BAlbumCancion? = null
        val query = """
            SELECT * FROM $TABLE_CANCION WHERE $COLUMN_CANCION_ID = $cancionId 
        """.trimIndent()
        val resultadoConsulta = conexionLectura.rawQuery(
            query,null
        )
        if(resultadoConsulta.moveToFirst()){
            val cancion = BCancion(
                id = resultadoConsulta.getInt(0),
                nombreCancion =  resultadoConsulta.getString(1),
                artista = resultadoConsulta.getString(2),
                duracion = resultadoConsulta.getDouble(3),
                añoLanzamiento = resultadoConsulta.getString(4)
            )
            val albumId = resultadoConsulta.getInt(5)
            cancionAlbum = BAlbumCancion(cancion,albumId)
        }
        resultadoConsulta.close()
        return cancionAlbum
    }

    fun obtenerCancionPorAlbumId(albumId : Int?): List<BCancion>{
        val canciones = mutableListOf<BCancion>()
        val conexionLectura = this.readableDatabase
        val query = """
            SELECT * FROM $TABLE_CANCION WHERE $COLUMN_ALBUMID_FK = ?
        """.trimIndent()
        val resultadoConsulta = conexionLectura.rawQuery(
            query,
            arrayOf(albumId.toString())
        )
        if(resultadoConsulta.moveToFirst()){
            do{
                val id = resultadoConsulta.getInt(0)
                val nombreCancion = resultadoConsulta.getString(1)
                val artista = resultadoConsulta.getString(2)
                val duracion = resultadoConsulta.getDouble(3)
                val anioLanzamiento = resultadoConsulta.getString(4)

                val cancion = BCancion(
                    id, nombreCancion,artista,duracion,anioLanzamiento
                )
                canciones.add(cancion)
            } while (resultadoConsulta.moveToNext())
        }
        resultadoConsulta.close()
        conexionLectura.close()
        return canciones
    }





    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val borrarCancion = "DROP TABLE IF EXISTS $TABLE_CANCION"
        val borrarAlbum = "DROP TABLE IF EXISTS $TABLE_ALBUM"

        db!!.execSQL(borrarCancion)
        db!!.execSQL(borrarAlbum)

        onCreate(db)
    }


}