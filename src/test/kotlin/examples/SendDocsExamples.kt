package examples

import com.github.ivan_osipov.clabo.dsl.bot
import java.io.File

object SendLocalDocumentAsyncExample {

    @JvmStatic
    fun main(args: Array<String>) {
        val file = File(Main::class.java.getResource("test.txt").file)

        bot(testProperties) longPolling {
            onStart {
                sendDocument(it.message.chat.id, file)
            }
        }
    }

}