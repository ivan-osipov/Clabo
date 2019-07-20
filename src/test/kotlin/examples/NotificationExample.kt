package examples

import com.github.ivan_osipov.clabo.api.output.dto.SendParams
import com.github.ivan_osipov.clabo.dsl.Bot
import com.github.ivan_osipov.clabo.dsl.bot
import java.util.*
import kotlin.concurrent.fixedRateTimer

fun main(args: Array<String>) {

    /* put a valid chat id here */
    val chatId = ""

    val myBot: Bot = bot(exampleProperties)

    fixedRateTimer("default", false, 0L, 10000){
        if(chatId.isNotEmpty()) {
            myBot.notification {
                val notificationSendParams = SendParams(chatId = chatId, text = Date().time.toString())
                sendMessageAsync(notificationSendParams) {
                    println("Sent a message for $chatId")
                }
            }
        }
    }

}
