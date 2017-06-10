package examples

import com.github.ivan_osipov.clabo.auth.extensions.askContact
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

        onUpdate { update ->
            println("New update")
            update.message.let { message ->
                println("New message with id " + message?.id)
                println("Message sender: ${message?.from?.firstName ?: "undefined"}")
                println("Text: " + (message?.text ?: "no text"))
            }
        }

        askContact { contact ->
            say("Nice to meet you ${contact.firstName}")
        }

    }
}