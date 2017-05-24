package examples

import com.github.tebotlib.auth.extensions.askContact
import com.github.tebotlib.bot
import com.github.tebotlib.extensions.personal
import com.github.tebotlib.properties

class SimpleUsing

fun main(args: Array<String>) {
    bot(properties(SimpleUsing::class, "bot.properties")) personal {

        askContact { contact ->
            say("Nice to meet you ${contact.firstName}")
        }

    }
}