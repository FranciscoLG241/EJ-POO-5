

class Tiempo(var hora: Int, var min: Int, var seg: Int) {

    constructor(hora: Int) : this(hora, 0, 0)

    constructor(hora: Int, min: Int) : this(hora, min, 0)

    companion object {
        const val MAX_HORA = 23
        const val MAX_SEGUNDOS = 86399
    }


    init {
        validar()
        ajustar()
    }


    private fun validar() {
        require( min >= 0) { "Los minutos deben ser positivos o cero!" }
        require( seg >= 0) { "Los segundos deben ser positivos o cero!" }
        validarHora()
    }


    private fun validarHora() {
        require(hora in 0..MAX_HORA) { "La hora debe estar entre 0 y 23!" }
    }


    private fun ajustar() {
        val (minutosExtra, segundosAjustados) = ajustarUnidad(seg)
        seg = segundosAjustados
        min += minutosExtra

        val (horasExtra, minutosAjustados) = ajustarUnidad(min)
        min = minutosAjustados
        hora += horasExtra

        validarHora()
    }



    private fun ajustarUnidad(valor: Int): Pair<Int, Int> {
        val incremento = valor / 60
        val ajustado = valor % 60
        return Pair(incremento, ajustado)
    }



    private fun actualizarTiempoConSegundos(totalSegundos: Int) {
        hora = (totalSegundos / 3600) % 24
        min = (totalSegundos % 3600) / 60
        seg = (totalSegundos % 60)
    }


    private fun obtenerSegundos(): Int {
        return hora * 3600 + min * 60 + seg
    }


    fun incrementar(t: Tiempo): Boolean {
        return try {
            val nuevoTiempo = Tiempo(hora + t.hora, min + t.min, seg + t.seg)
            hora = nuevoTiempo.hora
            min = nuevoTiempo.min
            seg = nuevoTiempo.seg
            true
        } catch (e: IllegalArgumentException){
            false
        }
    }


    fun decrementar(t: Tiempo): Boolean {
        return try {
            val nuevoTiempo = Tiempo( hora - t.hora, min - t.min, seg - t.seg)
            hora = nuevoTiempo.hora
            min = nuevoTiempo.min
            seg = nuevoTiempo.seg
            true
        } catch (e: IllegalArgumentException){
            false
        }
    }


    fun comparar(t: Tiempo): Int {
        val segundosActuales = obtenerSegundos()
        val segundosComparar = t.obtenerSegundos()

        return when {
            segundosActuales < segundosComparar -> -1
            segundosActuales > segundosComparar -> 1
            else -> 0
        }
    }



    fun copiar(): Tiempo {
        return Tiempo(hora, min, seg)
    }


    fun copiar(t: Tiempo) {
        hora = t.hora
        min = t.min
        seg = t.seg
    }


    fun sumar(t: Tiempo): Tiempo? {
        val totalSegundos = obtenerSegundos() + t.obtenerSegundos()
        return if (totalSegundos <= MAX_SEGUNDOS) {
            val nuevoTiempo = Tiempo(0)
            nuevoTiempo.actualizarTiempoConSegundos(totalSegundos)
            nuevoTiempo
        } else {
            null
        }
    }


    fun restar(t: Tiempo): Tiempo? {
        val totalSegundos = obtenerSegundos() - t.obtenerSegundos()
        return if (totalSegundos >= 0) {
            val nuevoTiempo = Tiempo(0)
            nuevoTiempo.actualizarTiempoConSegundos(totalSegundos)
            nuevoTiempo
        } else {
            null
        }
    }


    fun esMayorQue(t: Tiempo): Boolean {
        return obtenerSegundos() > t.obtenerSegundos()
    }


    fun esMenorQue(t: Tiempo): Boolean {
        return obtenerSegundos() < t.obtenerSegundos()
    }


    override fun toString(): String {
        return "${"%02d".format(hora)}h ${"%02d".format(min)}m ${"%02d".format(seg)}s"
    }
}






fun main() {
    val tiempo1 = Tiempo(12, 30, 45)
    val tiempo2 = Tiempo(3, 45)
    val tiempo3 = Tiempo(0, 5, 30)

    println("Tiempo 1: $tiempo1")
    println("Tiempo 2: $tiempo2")
    println("Tiempo 3: $tiempo3")

    println("Comparando tiempo1 y tiempo2: ${tiempo1.comparar(tiempo2)}")
    println("Comparando tiempo2 y tiempo3: ${tiempo2.comparar(tiempo3)}")

    val incremento = tiempo1.incrementar(tiempo2)
    println("Incremento: $incremento -> Tiempo 1 después de incrementar: $tiempo1")

    val decremento = tiempo1.decrementar(tiempo2)
    println("Decremento: $decremento -> Tiempo 1 después de decrementar: $tiempo1")

    val tiempoSuma = tiempo2.sumar(tiempo3)
    if (tiempoSuma != null) {
        println("Suma de tiempo2 y tiempo3: $tiempoSuma")
    } else {
        println("La suma de tiempo2 y tiempo3 excede el máximo permitido.")
    }

    val tiempoResta = tiempo1.restar(tiempo3)
    if (tiempoResta != null) {
        println("Resta de tiempo1 y tiempo3: $tiempoResta")
    } else {
        println("La resta de tiempo1 y tiempo3 no es posible (resultado negativo).")
    }

    val copiaTiempo1 = tiempo1.copiar()
    println("Copia de Tiempo 1: $copiaTiempo1")

    println("Tiempo 1 es mayor que Tiempo 2: ${tiempo1.esMayorQue(tiempo2)}")
    println("Tiempo 2 es menor que Tiempo 3: ${tiempo2.esMenorQue(tiempo3)}")
}







