package examples

import com.github.ivan_osipov.clabo.dsl.bot
import com.github.ivan_osipov.clabo.dsl.props
import java.io.File

fun main(args: Array<String>) {
    val photoFile = File(Main::class.java.getResource("test.png").file)

    bot(props(Main::class, "bot.properties")) longPolling {
        onStart {
            sendPhotoSync(it.message.chat.id, photoFile /*or file id or url*/)
        }
    }
}