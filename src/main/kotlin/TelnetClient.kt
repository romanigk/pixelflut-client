import java.io.IOException
import java.net.Socket
import java.net.SocketException

fun sendHelpCommandViaTelnet(
    host: String,
    port: Int,
) {
    try {
        Socket(host, port).use { socket ->
            val output = socket.getOutputStream()
            val input = socket.getInputStream()

            // Send the HELP command
            output.write("HELP\n".toByteArray())
            output.flush()

            // Read and print the server response
            val response = input.bufferedReader().use { it.readText() }
            println("Server response: $response")
        }
    } catch (e: SocketException) {
        println("SocketException: ${e.message}")
    } catch (e: IOException) {
        println("IOException: ${e.message}")
    }
}

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
