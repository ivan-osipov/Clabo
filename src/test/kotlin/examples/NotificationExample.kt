package examples

import com.github.ivan_osipov.clabo.api.output.dto.SendParams
import com.github.ivan_osipov.clabo.dsl.bot
import java.util.*
import kotlin.concurrent.fixedRateTimer

fun main(args: Array<String>) {

    val chatIds: MutableList<String> = mutableListOf()

    fixedRateTimer("default", false, 0L, 10000){
        chatIds.forEach { chatId ->
            bot(exampleProperties).notification {
                val notificationSendParams = SendParams(chatId = chatId, text = Date().time.toString())
                sendMessageAsync(notificationSendParams) {
                    println("Sent a message for $chatId")
                }
            }
        }
    }

    bot(exampleProperties).longPolling {
        onStart { command ->
            println("${command.update.message?.from?.username} subscribed for notifications")
            command.update.message answer "Sending notifications"
            val newSubscriberId = command.update.message?.chat?.id
            if (newSubscriberId != null && !chatIds.contains(newSubscriberId)) {
                chatIds.add(newSubscriberId)
            }
        }

        commands {
            register("stop") { command ->
                println("${command.update.message?.from?.username} unsubscribed")
                val subscriberId = command.update.message?.chat?.id
                if (subscriberId != null) {
                    chatIds.remove(subscriberId)
                }
            }
        }
    }

}
