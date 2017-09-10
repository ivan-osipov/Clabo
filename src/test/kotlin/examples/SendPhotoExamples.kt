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
                sendPhoto(it.message.chat.id, "https://cdn.pixabay.com/photo/2015/10/18/18/07/android-994910_960_720.jpg")
            }
        }
    }
}