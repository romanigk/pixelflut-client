import java.net.Socket

private val name = "World"

fun greet() = "Hello, $name!"

fun sendPixelGraphicViaTelnet(
    host: String,
    port: Int,
    pixelGraphic: Array<Array<Int>>,
) {
    Socket(host, port).use { socket ->
        val output = socket.getOutputStream()
        pixelGraphic.forEach { row ->
            row.forEach { pixel ->
                output.write(pixel)
            }
            output.write("\n".toByteArray()) // Move to next line
        }
    }
}
