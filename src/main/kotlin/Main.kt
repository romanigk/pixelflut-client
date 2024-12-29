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

    // Verwende getRGB, um die Pixelmappe direkt zu erstellen
    val pixelData = image.getRGB(0, 0, image.width, image.height, null, 0, image.width)
    return Array(image.height) { y ->
        Array(image.width) { x ->
            pixelData[y * image.width + x] and 0xFFFFFF // Extrahiere RGB-Wert (24-Bit)
        }
    }
}
