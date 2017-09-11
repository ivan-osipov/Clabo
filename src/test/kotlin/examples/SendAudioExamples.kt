package examples

import com.github.ivan_osipov.clabo.dsl.bot
import java.io.File

object SendLocalAudioFile {
    @JvmStatic
    fun main(args: Array<String>) {
        bot(exampleProperties) longPolling {
            onStart {
                val file = File(Main::class.java.getResource("test.mp3").file)
                sendAudio(it.message.chat.id, file /*or file id or url*/, {
                    this.caption = "Audio caption"
                    this.performer = "Test performer"
                })
            }
        }
    }
}
object SendAudioFileByUrl {
    @JvmStatic
    fun main(args: Array<String>) {
        bot(exampleProperties) longPolling {
            onStart {
                sendAudio(it.message.chat.id, "https://github.com/ivan-osipov/Clabo/blob/master/src/test/resources/examples/test.mp3?raw=true")
            }
        }
    }
}