package examples

import com.github.ivan_osipov.clabo.dsl.bot
import com.google.common.net.UrlEscapers
import java.io.File

object SendLocalDocumentAsyncExample {

    @JvmStatic
    fun main(args: Array<String>) {
        val file = File(Main::class.java.getResource("test_doc").file)

        bot(exampleProperties) longPolling {
            onStart {
                sendDocument(it.message.chat.id, file)
            }
        }
    }

}


object SendRemoteDocumentAsyncExample {

    @JvmStatic
    fun main(args: Array<String>) {
        val docUrl = UrlEscapers.urlFormParameterEscaper().escape("http://textfiles.com/internet/31.txt")

        bot(exampleProperties) longPolling {
            onStart {
                sendDocument(it.message.chat.id, docUrl)
            }
        }
    }

}