import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

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
    sendHelpCommandViaTelnet(host, port)
    while (true) {
        sendPixelGraphicViaTelnet(host, port, pixelGraphic)
    }
}

fun loadPixelGraphic(filePath: String): Array<Array<Int>> {
    // Lade die Bilddatei
    val file =
        object {}
            .javaClass.classLoader
            .getResource(filePath)
            ?.file
            ?: throw IllegalArgumentException("File not found: $filePath")
    val image: BufferedImage = ImageIO.read(File(file))

    // Konvertiere die Bilddaten in ein 2D-Array aus Integers
    val width = image.width
    val height = image.height
    val pixelGraphic = Array(height) { Array(width) { 0 } }

    for (y in 0 until height) {
        for (x in 0 until width) {
            pixelGraphic[y][x] = image.getRGB(x, y) and 0xFFFFFF // Extrahiere RGB-Wert (24-Bit)
        }
    }

    return pixelGraphic
}
