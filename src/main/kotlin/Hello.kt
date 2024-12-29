import java.io.IOException
import java.net.Socket
import java.net.SocketException

fun sendPixelGraphicViaTelnet(
    host: String,
    port: Int,
    pixelGraphic: Array<Array<Int>>,
) {
    try {
        Socket(host, port).use { socket ->
            val output = socket.getOutputStream()
            pixelGraphic.forEach { row ->
                row.forEach { pixel ->
                    output.write(pixel)
                }
                output.write("\n".toByteArray()) // Move to next line
                output.flush()
            }
        }
    } catch (e: SocketException) {
        println("SocketException: ${e.message}")
    } catch (e: IOException) {
        println("IOException: ${e.message}")
    }
}
