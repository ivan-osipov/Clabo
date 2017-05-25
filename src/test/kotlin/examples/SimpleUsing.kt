package examples

import com.github.ivan_osipov.clabo.auth.extensions.askContact
import com.github.ivan_osipov.clabo.bot
import com.github.ivan_osipov.clabo.extensions.personal
import com.github.ivan_osipov.clabo.props

class SimpleUsing

fun main(args: Array<String>) {
    bot(props(SimpleUsing::class, "bot.properties")) personal {

        askContact { contact ->
            say("Nice to meet you ${contact.firstName}")
        }

    }
}