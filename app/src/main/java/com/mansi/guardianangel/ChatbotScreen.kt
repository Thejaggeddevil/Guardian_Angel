package com.mansi.guardianangel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val LightGreen = Color(0xFFE8F5E9)
val MidnightBlue = Color(0xFF1A1B41)
val RedButton = Color(0xFFEF5350)

data class Message(val text: String, val isUser: Boolean, val isTyping: Boolean = false)

@Composable
fun ChatbotScreen(navController: NavController) {
    var userInput by remember { mutableStateOf(TextFieldValue("")) }
    var messages by remember { mutableStateOf(listOf<Message>()) }
    var isTyping by remember { mutableStateOf(false) }
    var animatedReply by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGreen)
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = MidnightBlue)
            }

            Text(
                text = "🤖 Guardian GPT",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MidnightBlue,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            TextButton(onClick = { navController.navigate("chatbot_history") }) {
                Text("History", color = RedButton, fontWeight = FontWeight.SemiBold)
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            if (messages.isEmpty() && !isTyping) {
                Text(
                    text = "What can I help with?",
                    fontSize = 18.sp,
                    color = MidnightBlue,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(reverseLayout = true, modifier = Modifier.fillMaxSize()) {
                    val displayMessages = messages + if (isTyping) listOf(
                        Message(animatedReply, isUser = false, isTyping = true)
                    ) else emptyList()

                    items(displayMessages.reversed()) { message ->
                        MessageBubble(message)
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
                .shadow(4.dp, RoundedCornerShape(50))
                .background(Color.White, shape = RoundedCornerShape(50)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = userInput,
                onValueChange = { userInput = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp),
                placeholder = { Text("Ask anything") },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = MidnightBlue,
                    textColor = MidnightBlue,
                    placeholderColor = Color.Gray
                )
            )

            IconButton(
                onClick = {
                    val inputText = userInput.text.trim()
                    if (inputText.isNotEmpty()) {
                        messages = messages + Message(inputText, true)
                        isTyping = true
                        animatedReply = ""

                        CoroutineScope(Dispatchers.Main).launch {
                            val reply = if (isInternetAvailable(context)) {
                                OpenAIHelper.getOpenAIResponse(inputText)
                            } else {
                                OfflineChatbot.getFallbackReply(inputText)
                            }

                            for (i in 1..reply.length) {
                                animatedReply = reply.substring(0, i)
                                delay(30L)
                            }

                            messages = messages + Message(animatedReply, isUser = false)
                            isTyping = false
                            animatedReply = ""
                        }

                        userInput = TextFieldValue("")
                    }
                },
                enabled = !isTyping
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send",
                    tint = RedButton
                )
            }
        }
    }
}

@Composable
fun MessageBubble(message: Message) {
    val bubbleColor = Color.White
    val alignment = if (message.isUser) Arrangement.End else Arrangement.Start

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        horizontalArrangement = alignment,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!message.isUser && message.isTyping) {
            CircularProgressIndicator(
                color = RedButton,
                modifier = Modifier
                    .size(18.dp)
                    .padding(end = 6.dp)
            )
        }

        // ✅ Bot ke message ke liye selectable
        if (!message.isUser && !message.isTyping) {
            SelectionContainer {
                Text(
                    text = message.text,
                    modifier = Modifier
                        .background(bubbleColor, shape = RoundedCornerShape(10.dp))
                        .padding(10.dp)
                        .widthIn(max = 280.dp),
                    fontSize = 16.sp,
                    color = MidnightBlue
                )
            }
        } else {
            Text(
                text = message.text,
                modifier = Modifier
                    .background(bubbleColor, shape = RoundedCornerShape(10.dp))
                    .padding(10.dp)
                    .widthIn(max = 280.dp),
                fontSize = 16.sp,
                color = MidnightBlue
            )
        }
    }
}

fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}
