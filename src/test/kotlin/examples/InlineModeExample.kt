package examples

import com.github.ivan_osipov.clabo.dsl.bot
import com.github.ivan_osipov.clabo.dsl.props

fun main(args: Array<String>) {
    bot(props(Main::class, "bot.properties")) launch {

        configure {
            helloMessage("Hello! I'm Bot")
            updates {
                timeout = 3000
            }
        }

        inlineMode {

        }
    }
}