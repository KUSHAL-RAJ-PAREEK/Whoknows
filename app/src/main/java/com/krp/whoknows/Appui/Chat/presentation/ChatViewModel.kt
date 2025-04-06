package com.krp.whoknows.Appui.Chat.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.krp.whoknows.Utils.TypewriteText
import com.krp.whoknows.ktorclient.KtorClient
import com.krp.whoknows.model.Message
import com.krp.whoknows.model.NotificationModel
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Created by Kushal Raj Pareek on 24-03-2025 00:20
 */


class ChatViewModel :ViewModel(),KoinComponent{
    private val ktorClient: KtorClient by inject()

    data class Status(val chatId : String,val count: Int)


    private var socket : Socket = IO.socket("https://whoknowschatbackendrailway-production.up.railway.app")

    private val _state = MutableStateFlow(SendMessageState())
    val state: StateFlow<SendMessageState> = _state

    private val _deleteState = MutableStateFlow(ChatDeleteStatus())
    val deleteState: StateFlow<ChatDeleteStatus> = _deleteState

    private val _map = MutableStateFlow<Map<String, Set<String>>>(emptyMap())
    val map: StateFlow<Map<String, Set<String>>> = _map

    private val _Liststate = MutableStateFlow(ChatState())
    val Liststate: StateFlow<ChatState> = _Liststate


    private val _statusState = MutableStateFlow<Status?>(null)
    val statusState: StateFlow<Status?> = _statusState


    private val _matchRemoved = MutableStateFlow<Boolean>(false)
    val matchRemoved: StateFlow<Boolean> = _matchRemoved

    private val _chatRemoved = MutableStateFlow<String?>(null)
    val chatRemoved: StateFlow<String?> = _chatRemoved

    init {
        socket.connect()
        socket.on("newMessage") { it?.let { arrayData ->
            val messageJson = arrayData[0]
            val message = Gson().fromJson(messageJson.toString(), Message::class.java)
            val newMessageList = Liststate.value.messageList.toMutableList()
            newMessageList.add(message)
            Log.d("messagedekhlo",message.toString())
            _Liststate.value = Liststate.value.copy(messageList = newMessageList)
        }}

        socket.on("messageDeleted") { it?.let { arrayData ->
            val messageId = arrayData[0].toString()
            val updatedList = Liststate.value.messageList.filterNot { it._id == messageId }
            _Liststate.value = Liststate.value.copy(messageList = updatedList.toMutableList())
        }}

        socket.on("messageUpdated") { it?.let { arrayData ->
            Log.d("dasdsadasdasdasdasdas",arrayData[0].toString())

            val jsonArray = arrayData[0] as JSONArray

                val messageId = jsonArray.getString(0)
                val newText = jsonArray.getString(1)

                val updatedList = Liststate.value.messageList.map { msg ->
                if (msg._id == messageId) msg.copy(message = newText, imgStr = null, imgUrl = null)  else msg
            }

            _Liststate.value = Liststate.value.copy(messageList = updatedList.toMutableList())
        }}

        socket.on("userTyping") { args ->
            Log.d("insidesocketr", "Received: ${args[0].toString()}")

            val jsonObject = JSONObject(args[0].toString())
            val chatRoomId = jsonObject.getString("chatRoomId")
            val typingUsersArray = jsonObject.getJSONArray("typingUsers")

            val typingUsers = (0 until typingUsersArray.length())
                .map { typingUsersArray.getString(it) }
                .toSet()

            Log.d("insidesocketr", "Chat Room: $chatRoomId, Typing Users: $typingUsers")

            _map.update { currentMap ->
                currentMap.toMutableMap().apply {
                    this[chatRoomId] = typingUsers
                }
            }
            // Log the updated map after the update
            Log.d("sadasdasdasdsadsadasdas", "Updated Map: ${_map.value}")
        }

        socket.on("countUpdate") { args ->
            val data = args[0] as JSONObject
            val chatRoomId = data.getString("chatRoomId")
            val count = data.getInt("count")

            Log.d("Socketssssssssssssssss", "ChatRoomId: $chatRoomId, Count: $count")
            _statusState.value = Status(chatRoomId,count)
        }



        socket.on("matchStatusResponse") { args ->
            val response = args[0] as JSONObject
            val receivedChatId = response.getString("chatId")
            Log.d("Socketrmmmmmmmmm", "Received chatId: $receivedChatId")
            _chatRemoved.value = receivedChatId
        }
    }

    fun updateMatchRm() {
        viewModelScope.launch {

            _matchRemoved.value = false
            Log.d("updateMatchRm", "matchRemoved updated: ${matchRemoved.value}")
        }
    }

    fun updateMatchRmm() {
        viewModelScope.launch {
            _chatRemoved.value = null
            Log.d("updateMatchRmm", "chatRemoved updated: ${chatRemoved.value}")
        }
    }

    fun sendTypingStatus(chatRoomId: String, userId: String, isTyping: Boolean) {
        Log.d("TypingStatus", "User: $userId isTyping: $isTyping")

        val typingData = JSONObject().apply {
            put("chatRoomId", chatRoomId)
            put("userId", userId)
        }

        val st = if(isTyping) "typing" else "stopTyping"
        socket.emit(st, typingData)
    }


    fun sendCountUpdate(chatRoomId : String,count : Int){
        val data = JSONObject().apply {
            put("userId", chatRoomId)
            put("count", count)
        }
        socket.emit("status", data)

    }

    fun sendRemoveStatus(chatRoomId : String){
        val data = JSONObject().apply {
            put("chatId", chatRoomId)
            }
        socket.emit("matchStatus", data)

    }

    fun onEvent(event: ChatEvent) {
        when (event) {
            is ChatEvent.SendMessage -> sendMessage(message = event.message)
            is ChatEvent.EditMessage -> editMessage(chatId = event.id)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun sendMessage(message : Message) {
        viewModelScope.launch {
            try {
                val response = ktorClient.sendMessage(message)
             _state.value = SendMessageState(isLoading =  false, isSuccess =  true, statusCode =  response)
                Log.d("GKJADNKJGNDJGNDKJ", state.value.toString())
            } catch (e: Exception) {
                _state.value = SendMessageState(errorMessage = e.message)

            }
        }
    }

    fun getMessages(chatRoomID: String){
        viewModelScope.launch {
            try {
                val response = ktorClient.fetchMessage(chatRoomID)
                response.collect { response ->
                    _Liststate.value = _Liststate.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        messageList = response.toMutableList()
                    )
                }
                Log.d("GKJADNKJGNDJGNDKJ", state.value.toString())
            } catch (e: Exception) {
                _Liststate.value = ChatState(errorMessage = e.message)

            }
        }
    }

    fun editMessage(chatId: String){
        viewModelScope.launch {
            try {
                val response = ktorClient.editMessage(chatId)
                if(response == 200){
//                    _Liststate.value = _Liststate.value.copy(
//                        isLoading = false,
//                        isSuccess = true
//                    )
                    Log.d("edit-message","done")
                }
                Log.d("GKJADNKJGNDJGNDKJ", state.value.toString())
            } catch (e: Exception) {
//                _Liststate.value = ChatState(errorMessage = e.message)

            }
        }
    }

    fun deleteChatRoom(chat_id : String){
        viewModelScope.launch{
            Log.d("INCAHBJSADKJASDSA","afasfasfsafsfs")

            _deleteState.value = ChatDeleteStatus(isLoading = true)
            try{
                val response = ktorClient.deleteChatRoom(chat_id)
                if(response == 200){
                    Log.d("asdasdasdasdasd","afasfasfsafsfs")
                    _deleteState.value = ChatDeleteStatus(isLoading = false,statusCode = 200, isSuccess = true)

                }
            }catch(e : Exception){
                Log.d("exceptionindeletechatroom","${e.message.toString()}")
                _deleteState.value = ChatDeleteStatus(isLoading = false,statusCode = 500, isSuccess = false, errorMessage = e.message)
            }
        }
    }

    fun removeMatch(id : String,jwt : String){
        Log.d("afsfsdfsfdsfsd","sdgdsgdsgssg")
        viewModelScope.launch {
            try{
                val response = ktorClient.removeMatch(id,jwt)
                if(response == 200){
                    _matchRemoved.value= true
                }
            }catch (e : Exception){
                Log.d("exceptioninremoveusers","${e.message.toString()}")
                _matchRemoved.value= false
            }
        }
    }

    fun removeAcc(id : String){
        Log.d("cominaifnaskf0","asasfasf")

        viewModelScope.launch {
            try{
                val response = ktorClient.deleteAcceptation(id)

                if(response == 200){
                    Log.d("useraccremovedsucessornot",response.toString())
                }else{
                    Log.d("useraccremovedsucessornot",response.toString())
                }
            }catch (e : Exception){
                Log.d("useraccremovedsucessornot","${e.message}")
            }
        }
    }

    fun sendNotification(notification : NotificationModel){

        viewModelScope.launch {
            try{
                val response = ktorClient.sendNotification(notification)

                if(response == 200){
                    Log.d("sendinoti",response.toString())
                }else{
                    Log.d("sendinoti",response.toString())
                }
            }catch (e : Exception){
                Log.d("sendinoti","${e.message}")
            }
        }
    }

    suspend fun getWait(id : String): Int{

           return try{
                val response = ktorClient.getWait(id)
                Log.d("sendinoti",response.toString())
                response
            }catch (e : Exception){
                Log.d("sendinoti","${e.message}")
               500
            }
    }

   suspend fun postWait(id : String): Int{

            return try{
                val response = ktorClient.postWait(id)

                if(response == 200){
                    Log.d("postit",response.toString())
                }else{
                    Log.d("postit",response.toString())
                }
                response
            }catch (e : Exception){
                Log.d("postit","${e.message}")
                500
            }

    }


    override fun onCleared() {
        super.onCleared()
        socket.disconnect()
        socket.off()
    }


 }