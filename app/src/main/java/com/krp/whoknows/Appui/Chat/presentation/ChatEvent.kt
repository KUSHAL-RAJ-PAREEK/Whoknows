package com.krp.whoknows.Appui.Chat.presentation

import com.krp.whoknows.model.Message

/**
 * Created by Kushal Raj Pareek on 24-03-2025 00:27
 */

sealed class ChatEvent{
    data class SendMessage(val message : Message) :ChatEvent()
    data class EditMessage(val id : String) :ChatEvent()
}