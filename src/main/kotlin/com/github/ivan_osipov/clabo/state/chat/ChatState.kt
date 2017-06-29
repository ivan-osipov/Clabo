package examples.buyartbot

import com.github.ivan_osipov.clabo.state.chat.ChatContext
import com.github.ivan_osipov.clabo.utils.ChatId

open class ChatState<CC>(val chatId: ChatId,
                     var context: CC)
    where CC : ChatContext