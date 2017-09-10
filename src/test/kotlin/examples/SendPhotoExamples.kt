package examples

import com.github.ivan_osipov.clabo.dsl.bot
import java.io.File

private val photoFile = File(Main::class.java.getResource("test.png").file)

object SendPhotoSyncFromLocalFile {
    @JvmStatic
    fun main(args: Array<String>) {
        bot(testProperties) longPolling {
            onStart {
                sendPhotoSync(it.message.chat.id, photoFile /*or file id or url*/) {
                    caption = "Photo is sent by bot"
                }
            }
        }
    }
}

object SendPhotoAsyncByUrl {
    @JvmStatic
    fun main(args: Array<String>) {
        bot(testProperties) longPolling {
            onStart {
                sendPhoto(it.message.chat.id, "https://github.com/ivan-osipov/Clabo/blob/master/src/test/resources/examples/test.png?raw=true")
            }
        }
    }
}