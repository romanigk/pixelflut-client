import java.io.IOException
import java.net.Socket
import java.net.SocketException
import kotlin.text.Charsets.UTF_8

const val HELP_COMMAND = "HELP\n"
const val TIMEOUT_NANOS = 3_000_000_000L // 3 Sekunden

fun sendHelpCommandViaTelnet(
    host: String,
    port: Int,
) {
    try {
        Socket(host, port).use { client ->
            sendCommand(client, HELP_COMMAND)
            readServerResponse(client)
        }
    } catch (e: SocketException) {
        println("SocketException: ${e.message}")
    } catch (e: IOException) {
        println("IOException: ${e.message}")
    }
}

private fun sendCommand(
    client: Socket,
    command: String,
) {
    client.outputStream.apply {
        write(command.toByteArray())
        flush()
    }
}

private fun readServerResponse(client: Socket) {
    val reader = client.inputStream.bufferedReader(UTF_8)
    val timeoutDeadline = System.nanoTime() + TIMEOUT_NANOS

    while (System.nanoTime() <= timeoutDeadline) {
        if (reader.ready()) {
            println("Server response:")
            val line = reader.readLine() ?: break
            println(line)
        }
    }
    println("Connection closed")
}

fun sendPixelGraphicViaTelnet(
    host: String,
    port: Int,
    pixelGraphic: Array<Array<Int>>,
) {
    try {
        Socket(host, port).use { socket ->
            val output = socket.getOutputStream()

            pixelGraphic.forEachIndexed { y, row ->
                row.forEachIndexed { x, pixel ->
                    val rgbValue = String.format("%06X", pixel)
                    output.write("PX $x $y $rgbValue\n".toByteArray())
                }
                output.flush()
            }
        }
    } catch (e: SocketException) {
        println("SocketException: ${e.message}")
    } catch (e: IOException) {
        println("IOException: ${e.message}")
    }
}
