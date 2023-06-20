import java.io.File
import java.io.PrintWriter
import java.text.SimpleDateFormat


data class Song(
    val id: Int,
    var songName: String,
    var artist: String,
    var duration: Double,
    var releaseYear: String,
    var musicVideo: Boolean,
    var BPM: Int
)

data class Album(
    val id: Int,
    var title: String,
    var artist: String,
    var releaseYear: String,
    var isExplicit: Boolean,
    var songId: Int
)

fun main(args: Array<String>) {
    val songsFile = File("src/main/resources/songs.txt")
    val albumFile = File("src/main/resources/album.txt")
    if (!songsFile.exists() || albumFile.exists()) {
        songsFile.createNewFile()
        albumFile.createNewFile()
        println("Files created")
    } else {
        println("Files already exist")
    }

    var songs = mutableListOf<Song>()
    var albums = mutableListOf<Album>()
    val desiredDateFormat = SimpleDateFormat("dd-MM-yyyy")


    if(songsFile.exists()){
        songsFile.forEachLine{line ->
            val splits = line.split(",")
            val song = Song(
                splits[0].toInt(),
                splits[1],
                splits[2],
                splits[3].toDouble(),
                splits[4],
                splits[5].toBoolean(),
                splits[6].toInt()
            )
            songs.add(song)
        }
    }

    if(albumFile.exists()){
        albumFile.forEachLine{line ->
            val splits = line.split(",")
            val album = Album(
                splits[0].toInt(),
                splits[1],
                splits[2],
                (splits[3]),
                splits[4].toBoolean(),
                splits[5].toInt()
            )
            albums.add(album)
        }
    }

    var option = 0
    do {
        option = menu()!!
        when(option){
            1->createSong(songs,songsFile)
            2->createAlbum(albums,songs,albumFile)
            3->readSongs(songs)
            4->readAlbum(albums,songs)
            5->updateSong(songs,songsFile)
            6->updateAlbum(albums,songs,albumFile)
            7->deleteSong(albums,songs,albumFile,songsFile)
            8->deleteAlbum(albums,albumFile)
            9->break
        }
    }while (option != 9)


}

fun menu(): Int? {
    println("Album - song aplication")
    println("Menu")
    println("1. Create a song")
    println("2. Create an Album")
    println("3. Show songs")
    println("4. Show albums")
    println("5. Update song")
    println("6. Update album")
    println("7. Delete song")
    println("8. Delete album")
    println("9. Exit")
    println("Choose an option: ")
    val option = readLine()?.toInt()
    return option
}






//ALBUM

fun createAlbum(albums: MutableList<Album>, songs: MutableList<Song>, file: File) {
    print("Enter the ID of the album: ")
    val id = readLine()?.toInt()
    print("\n")
    print("Enter the Title of the album: ")
    val title = readLine()
    print("\n")
    print("Enter the Artist of the album: ")
    val artist = readLine()
    print("\n")
    print("Enter the Release year of the album (dd-mm-yyyy): ")
    val releaseYear = readLine()
    print("\n")
    print("Is the album cataloged as explicit?: ")
    val isExplicit = readLine()?.toBoolean()
    print("\n")
    print("Enter the Song Id related to this album: ")
    val songId = readLine()?.toInt()
    println("\n")
    if (id != null &&
        title != null &&
        artist != null &&
        releaseYear != null &&
        isExplicit != null &&
        songId != null
    ) {

        val songExist = songs.find { it.id == songId }
        if (songExist != null) {
            val album = Album(id, title, artist, releaseYear.toString(), isExplicit, songId)
            albums.add(album)
            saveAlbum(file, albums)
            println("Album created successfully")
            println()
        } else {
            println("Song ID not found")
        }
    } else {
        println("The data entered is not okay, try again")
    }
}

fun readAlbum(albums: MutableList<Album>, songs: MutableList<Song>) {
    if (!albums.isEmpty()) {
        println()
        println("Albums:")
        albums.forEach { album ->
            val song = songs.find { it.id == album.songId }
            println("Id: ${album.id}, Title: ${album.title}, Artist: ${album.artist}, Release year: ${album.releaseYear}, Explicit: ${album.isExplicit}")
            println("Song: ${song?.songName}")
        }
        println()
    } else {
        println("Did not find any albums")
    }
}


fun updateAlbum(albums: MutableList<Album>, songs: List<Song>, file: File) {
    println("Enter the ID of the album")
    var albumID = readLine()?.toInt()
        val album = albums.find { it.id == albumID }
        if (album != null) {
            println("Enter the new title")
            val data1 = readLine()
            val title = if (data1 != "") data1 else album.title
            println("Enter the Artist name")
            val data2 = readLine()
            val artist = if (data2 != "") data2 else album.artist
            print("Enter the release year")
            val data4 = readLine()
            val releaseYear = if (data4 != "") data4 else album.releaseYear

            println("Enter de new explicit category")
            val data5 = readLine()
            val isExplicit = if (data5 != "") data5.toBoolean() else album.isExplicit
            print("Enter the new song id")
            val songId = readLine()?.toIntOrNull() ?: album.songId

            val existSong = songs.find { it.id == songId }
            if (existSong != null && songId != null) {
                album.artist = artist.toString()
                album.title = title.toString()
                album.releaseYear = releaseYear.toString()
                album.isExplicit = isExplicit
                album.songId = songId

                saveAlbum(file, albums)
                println("Album updated")
                println()
            } else {
                println("Song id not found")
                println()
            }
        } else {
            println("Album not found")
            println()
        }
    }



fun saveAlbum(file: File, albums: List<Album>) {
    try {
        PrintWriter(file).use { printWriter ->
            albums.forEach { album ->
                printWriter.println("${album.id},${album.title},${album.artist},${album.releaseYear},${album.isExplicit},${album.songId}")
            }
        }
    } catch (ex: Exception) {
        println("We had a problem saving the data")
    }

}

fun deleteAlbum(albums: MutableList<Album>,file: File) {
    println("Enter the album id")
    val albumID = readLine()?.toInt()
    val album = albums.find { it.id == albumID }
    if(album != null){
        albums.remove(album)
        saveAlbum(file, albums)
        println("Album removed")
        println()
    }else{
        println("Album not found")
    }
}





//SONG
fun createSong(songs: MutableList<Song>, file: File) {
    print("Enter the ID of the song: ")
    val id = readLine()?.toInt()
    print("\n")
    print("Enter the name of the song: ")
    val songName = readLine()
    print("\n")
    print("Enter the Artist of the song: ")
    val artist = readLine()
    print("\n")
    print("Enter the duration of the song: ")
    val duration = readLine()?.toDouble()
    print("\n")
    print("Enter the Release year of the song (dd-mm-yyyy): ")
    val releaseYear = readLine()
    print("\n")
    print("Has the song a music video?: ")
    val musicVideo = readLine()?.toBoolean()
    print("\n")
    print("Enter the BPM of the song: ")
    val BPM = readLine()?.toInt()
    println("\n")
    if (id != null &&
        duration != null &&
        artist != null &&
        releaseYear != null &&
        musicVideo != null &&
        BPM != null &&
        songName != null
    ) {
        val song = Song(id, songName, artist, duration, releaseYear.toString(), musicVideo, BPM)
        songs.add(song)
        saveSong(file, songs)
        println("Song created successfully")
        println()

    } else {
        println("The data entered is not okay, try again")
    }


}


fun readSongs(songs: List<Song>) {
    if (!songs.isEmpty()) {
        println()
        println("Songs:")
        songs.forEach { song ->
            println("Id: ${song.id}, Song name: ${song.songName}, Artist: ${song.artist}, Duration: ${song.duration}, Release year: ${song.releaseYear}, Music video: ${song.musicVideo}, BPM: ${song.BPM}")
        }
        println()
    } else {
        println("Did not find any songs")
        println()
    }
}


fun updateSong(songs: List<Song>, file: File) {
    println("Enter the ID of the song")
    val songID = readLine()?.toInt()
    val song = songs.find { it.id == songID }
    if (song != null) {
        println("Enter the new name")
        val data1 = readLine()
        val songName = if (data1 != "") data1 else song.songName
        println("Enter the Artist name")
        val data2 = readLine()
        val artist = if (data2 != "") data2 else song.artist
        println("Enter the duration of the song")
        val data3 = readLine()
        val duration = if (data3 != "") data3?.toDouble() else song.duration
        println("Enter the release year")
        val data4 = readLine()
        val releaseYear = if (data4 != "") data4 else song.releaseYear
        println("Enable music video")
        val data5 = readLine()
        val musicVideo = if (data5 != "") data5.toBoolean() else song.musicVideo
        print("Enter the new BPM")
        val BPM = readLine()?.toIntOrNull() ?: song.BPM

        song.songName = songName.toString()
        song.artist = artist.toString()
        if (duration != null) {
            song.duration = duration
        }
        song.releaseYear = releaseYear.toString()
        song.musicVideo = musicVideo
        song.BPM = BPM
        println("Song updated")
        println()
        saveSong(file,songs)

    } else {
        println("Song not found")
    }

}


fun deleteSong(albums: MutableList<Album>, songs: MutableList<Song>, albumfile: File, songFile : File){
    println("Enter the id of the song")
    val id = readLine()?.toInt()
    val song = songs.find { it.id == id }
    if(song != null){
        val deleteFromAlbum = albums.filter { it.songId == song.id }
        albums.removeAll(deleteFromAlbum)

        songs.remove(song)
        saveSong(songFile, songs)
        saveAlbum(albumfile,albums)
        println("Song deleted")
        println()
    }
}


fun saveSong(file: File, songs: List<Song>) {
    try {
        PrintWriter(file).use { printWriter ->
            songs.forEach { song ->
                printWriter.println("${song.id},${song.songName},${song.artist},${song.duration},${song.releaseYear},${song.musicVideo},${song.BPM}")
            }
        }
    } catch (ex: Exception) {
        println("We had a problem saving the data")
    }

}




