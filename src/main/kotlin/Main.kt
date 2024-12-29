import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

const val HOST = "151.217.60.77"
const val PORT = 1337
const val RGB_MASK = 0xFFFFFF // Maske zur Extraktion von RGB-Werten (24-Bit)

fun main() {
    val filePath = "dezentrale2.png" // Bilddatei
    val pixelGraphic = loadPixelGraphics(filePath)
    processPixelGraphic(HOST, PORT, pixelGraphic)
}

fun loadPixelGraphics(imagePath: String): Array<Array<Int>> {
    val image = loadBufferedImage(imagePath)
    val pixelData = image.getRGB(0, 0, image.width, image.height, null, 0, image.width)
    return Array(image.height) { y ->
        Array(image.width) { x ->
            pixelData[y * image.width + x] and RGB_MASK
        }
    }
}

private fun loadBufferedImage(imagePath: String): BufferedImage {
    val filePath =
        object {}
            .javaClass.classLoader
            .getResource(imagePath)
            ?.file
            ?: throw IllegalArgumentException("File not found: $imagePath")
    return ImageIO.read(File(filePath))
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
