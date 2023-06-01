import java.util.ArrayList
import java.util.Date

fun main(args: Array<String>) {

    //Inmutables (No se reasignan "=')
    val inmutable: String = "Adrian";

    //Mutable (Re asignar)
    var mutable: String = "Vicente";
    mutable = "Adrian";

    //Se prefiere utilizar las variables inmutables sobre las variables mutables
    //val > var

    //Duck typing
    var ejemploVariable = "Bryan Andrade";
    val edadEjemplo: Int = 12;
    ejemploVariable.trim();

    //Variables primitivas
    val nombreProfesor: String = "Bryan Andrade"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true

    //Clases java
    val fechaNacimiento: Date = Date();

    //Switch
    val estadoCivilWhen = "C"
    when (estadoCivilWhen) {
        ("C") -> {
            println("Casado")
        }

        "S" -> {
            println("Soltero")
        }

        else -> {
            println("No sabemos")
        }
    }

    val esSoltero = (estadoCivilWhen == "S")
    val coqueteo = if (esSoltero) "Si" else "No"

    //void -> unit
    fun imprimirNombre(nombre: String): Unit {
        println("Nombre: ${nombre}") //Template strings
    }

    fun calcularSueldo(
        sueldo: Double, //Requerido
        tasa: Double = 12.00, //Opcional (defecto)
        bonoEspecial: Double? = null //Opcion null -> nullable
    ): Double {
        //Int -> Int? (nullable)
        //String -> String? (nullable)
        //Date -> Date? (nullable)
        if (bonoEspecial == null) {
            return sueldo * (100 / tasa)
        } else {
            return sueldo * (100 / tasa) + bonoEspecial
        }
    }

    calcularSueldo(10.00)
    calcularSueldo(10.00, bonoEspecial = 20.00) //Paramtros nombrados
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)
    val sumaUno = Suma(1, 1)
    val sumaDos = Suma(1, null)
    val sumaTres = Suma(null, 1)
    val sumaCuatro = Suma(null, null)
    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)

    //Arreglos

    //Tipos de arreglos

    //Arreglo Estatico
    var arregloEstatico: Array<Int> = arrayOf<Int>(1, 2, 3)
    println(arregloEstatico)

    //Arreglos Dinamicos
    var arregloDinamico: ArrayList<Int> = arrayListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    //Operadores -> Sirven para los arreglos estaticos y dinamicos

    //FOR EACH -> Unit
    //iterar un arreglo
    val respuestaForEach: Unit = arregloDinamico
        .forEach { valorActual: Int ->
            println("Valor actual: ${valorActual}")
        }

    arregloDinamico.forEach { println(it) }

    arregloEstatico
        .forEachIndexed { indice: Int, valorActual: Int ->
            println("Valor ${valorActual} Indice: ${indice}")
        }
    println(respuestaForEach)


    //MAP -> muta el arreglo (cambia el arreglo)
    // 1) Enviemos el nuevo valor de la iteracion
    // 2) Nos devuelve es un NUEVO ARREGLO con los valores modificados

    val respuestaMap: List<Double> = arregloDinamico
        .map { valorActual: Int ->
            return@map valorActual.toDouble() + 100.00
        }

    println(respuestaMap)
    val respuestaMapDos = arregloDinamico.map { it + 15 }


    //Filter -> FILTRAR EL ARREGLO
    // 1) Devolver una expresion (True o False)
    // 2) Nuevo arreglo filtrado
    val respuestaFilter: List<Int> = arregloDinamico
        .filter { valorActual: Int ->
            val mayoresACinco : Boolean = valorActual > 5 //Expresion condicion
            return@filter mayoresACinco

        }
    val respuestaFilterDos = arregloDinamico.filter { it <= 5 }
    println(respuestaFilter)
    println(respuestaFilterDos)

    //OR AND
    //OR -> ANY (alguno cumple?)
    //AND -> ALL(todos cumplen?)

    val  respuestaAny: Boolean = arregloDinamico
        .any{valorActual: Int ->
            return@any (valorActual > 5)
        }
    println(respuestaAny) //true

    val respuestaAll: Boolean = arregloDinamico
        .all { valorActual: Int ->
            return@all (valorActual > 5)
        }
    println(respuestaAll)

    //REDUCE -> valor acumulado
    //Valor acumulado = 0 (Siempre 0 en lenguaje Kotlin)
    // {1, 2, 3, 4, 5} -> Sumeme todos los valores del arreglo
    //valorIteracion1 = valorEmpieza + 1 = 0 + 1 =  1  -> Iteracion 1
    //valorIteracion2 = valorIteracion1 + 2 = 1 + 2 = 3  -> Iteracion 2
    //valorIteracion3 = valorIteracion2 + 3 = 3 + 3 =  6  -> Iteracion 3
    //valorIteracion4 = valorIteracion3 + 4 = 6 + 4 =  10  -> Iteracion 4
    //valorIteracion5 = valorIteracion4 + 5 = 10 + 5 =  15  -> Iteracion 5



    val respuestaReduce: Int = arregloDinamico
        .reduce{ //acumulado = 0 -> SIEMPRE EMPIEZA EN 0
            acumulado: Int, valorActual: Int ->
            return@reduce (acumulado + valorActual) // -> logica negocio
        }



}


abstract class NumerosJava {
    protected val numeroUno: Int
    protected val numeroDos: Int

    constructor(
        uno: Int,
        dos: Int
    ) {
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}

abstract class Numeros(
    //Constructor primario
    //Ejemplo
    //uno: Int, (Parametro (Sin modificador de acceso))
    //private var uno:Int //Propiedad publica clase numero.uno
    //var uno: Int //Propiedad de la clase (por defecto es public)
    //public var uno: Int
    protected val numeroUno: Int, //Propiedad de la clase protected numeros.numeroUno
    protected val numeroDos: Int, //Propiedad de la clase protected numeros.numeroDos
) {
    //var cedula: String = "" (public es por defecto)
    //private valorCalculado: Int = 0 (private)
    init { //bloque constructor primario
        this.numeroUno; this.numeroDos;
        numeroUno;numeroDos;
        println("Inicializando")
    }
}

class Suma(
    //Constructor Primario Suma
    unoParametro: Int,
    dosParametro: Int,
) : Numeros(unoParametro, dosParametro) {//Extendiendo y mandando los parametros

    init {
        this.numeroUno
        this.numeroDos
    }

    constructor(
        //Segundo Constructor
        uno: Int?,
        dos: Int,
    ) : this(
        if (uno == null) 0 else uno,
        dos,
    )

    constructor(
        //Tercer Constructor
        uno: Int,
        dos: Int?,
    ) : this(
        uno,
        if (dos == null) 0 else dos,
    )

    constructor(
        //Cuarto Constructor
        uno: Int?,
        dos: Int?,
    ) : this(
        if (uno == null) 0 else uno,
        if (dos == null) 0 else dos,
    )


    fun sumar(): Int {
        val total = numeroUno + numeroDos
        agregarHistorial(total)
        return total
    }


    companion object {
        //Atributos y metodos "compartidos" singletons o static de esta clase
        //Todas las instancias de esta clase comparten estos atributos y metodos
        //dentro del companion object
        val pi = 3.14

        fun elevarAlCuadrado(num: Int): Int {
            return num * num
        }

        val historialSumas = ArrayList<Int>()


        fun agregarHistorial(valorNuevaSuma: Int) {
            historialSumas.add(valorNuevaSuma)
        }

    }
}
