package examples

import com.github.ivan_osipov.clabo.dsl.bot
import com.github.ivan_osipov.clabo.dsl.props
import java.io.File

private val photoFile = File(Main::class.java.getResource("test.png").file)

fun main_sync(args: Array<String>) {
    bot(props(Main::class, "bot.properties")) longPolling {
        onStart {
            sendPhotoSync(it.message.chat.id, photoFile /*or file id or url*/)
        }
    }
}

fun main_anync(args: Array<String>) {
    bot(props(Main::class, "bot.properties")) longPolling {
        onStart {
            sendPhoto(it.message.chat.id, photoFile /*or file id or url*/)
        }
    }
}