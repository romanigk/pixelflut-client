const val HOST = "151.217.60.77"
const val PORT = 1337
const val FILE_PATH = "dezentrale2.png"

fun main() {
    val pixelGraphic = loadPixelGraphic(FILE_PATH)
    processPixelGraphic(HOST, PORT, pixelGraphic)
}

fun processPixelGraphic(
    host: String,
    port: Int,
    pixelGraphic: Array<Array<Int>>,
) {
    while (true) {
        sendPixelGraphicViaTelnet(host, port, pixelGraphic)
    }
}

fun loadPixelGraphic(filePath: String): Array<Array<Int>> =
    object {}
        .javaClass.classLoader
        .getResource(filePath)
        ?.readText()
        ?.lineSequence()
        ?.map { line ->
            line
                .split(",")
                .map { it.trim().toInt() }
                .toList()
                .toTypedArray()
        }?.toList()
        ?.toTypedArray()
        ?: throw IllegalArgumentException("File not found: $filePath")
