fun main() {
    val host = "151.217.60.77"
    val port = 1337
    // Read dezentrale2.png from resources folder
    val pixelGraphic = readPixelGraphicFromFile("dezentrale2.png")
    while (true) {
        sendPixelGraphicViaTelnet(host, port, pixelGraphic)
    }
}

fun readPixelGraphicFromFile(filePath: String): Array<Array<Int>> =
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
