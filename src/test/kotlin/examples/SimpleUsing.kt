package examples

import com.github.ivan_osipov.clabo.bot
import com.github.ivan_osipov.clabo.extensions.personal
import com.github.ivan_osipov.clabo.props

class SimpleUsing

fun main(args: Array<String>) {
    bot(props(SimpleUsing::class, "bot.properties")) personal {

        configure {
            updates {
                timeout = 10000
            }
        }

        onMessage { message ->
            println("New message with id " + message.id)
            println("Message sender: ${message.from?.firstName ?: "undefined"}")
            println("Text: " + (message.text ?: "no text"))

            message answer "Hi! I received your message: ${message.text}"
        }
    }
}