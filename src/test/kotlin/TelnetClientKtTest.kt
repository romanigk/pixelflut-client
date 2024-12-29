import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.verify
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.Socket
import java.net.SocketException

class TelnetClientKtTest :
    StringSpec({

        "should send HELP command and print server response" {
            val mockSocket = mockk<Socket>(relaxed = true)
            val mockOutputStream = ByteArrayOutputStream()
            val mockInputStream = ByteArrayInputStream("Mocked server response".toByteArray())

            every { mockSocket.getOutputStream() } returns mockOutputStream
            every { mockSocket.getInputStream() } returns mockInputStream

            // Replace the Socket constructor with a mocked version
            mockkConstructor(Socket::class)
            every { anyConstructed<Socket>() } returns mockSocket

            sendHelpCommandViaTelnet("localhost", 23)

            verify { mockSocket.getOutputStream() }
            verify { mockSocket.getInputStream() }
            mockOutputStream.toString() shouldBe "HELP\n"
        }

        "should handle SocketException gracefully" {
            mockkConstructor(Socket::class)
            every { anyConstructed<Socket>() } throws SocketException("Socket error occurred")

            sendHelpCommandViaTelnet("localhost", 23)
            // No exception should propagate
        }

        "should handle IOException gracefully" {
            mockkConstructor(Socket::class)
            every { anyConstructed<Socket>() } throws IOException("IO error occurred")

            sendHelpCommandViaTelnet("localhost", 23)
            // No exception should propagate
        }
    })
