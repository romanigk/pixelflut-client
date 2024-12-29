import java.io.File

fun main() {
    val host = "151.217.60.77"
    val port = 1337
    // Read dezentrale2.png from resources folder
    val pixelGraphic = readPixelGraphicFromFile("resources/dezentrale2.png")
    while (true) {
        sendPixelGraphicViaTelnet(host, port, pixelGraphic)
    }
}

fun readPixelGraphicFromFile(filePath: String): Array<Array<Int>> =
    File(filePath)
        .readLines()
        .map { line ->
            line.split(",").map { it.trim().toInt() }.toTypedArray()
        }.toTypedArray()
