package examples

import com.github.ivan_osipov.clabo.bot
import com.github.ivan_osipov.clabo.extensions.personal
import com.github.ivan_osipov.clabo.props

class SimpleUsing

fun main(args: Array<String>) {
    bot(props(SimpleUsing::class, "bot.properties")) personal {

        configure {
            helloMessage("Hello! I'm Bot")
            updates {
                timeout = 3000
            }
        }

        commands {
            register("share_price") {
                it.update.message answer "No!"
            }
            registerForUnknown {
                it.update.message answer "Unknown command"
            }
        }

        onStart {
            println("Start with ${it.update.message?.from?.username}")
            it.update.message answer "I'm starting"
        }

        onHelp {
            println("Help with ${it.update.message?.from?.username}")
            it.update.message answer "I cannot help you"
        }

        onSettings {
            println("Settings with ${it.update.message?.from?.username}")
            it.update.message answer "I don't have settings"
        }
    }
}