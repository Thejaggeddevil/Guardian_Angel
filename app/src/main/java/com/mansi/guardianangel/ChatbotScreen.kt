package com.mansi.guardianangel


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
import kotlinx.coroutines.launch

val LightGreen = Color(0xFFE8F5E9)
val MidnightBlue = Color(0xFF1A1B41)
val RedButton = Color(0xFFEF5350)

data class Message(val text: String, val isUser: Boolean)

@Composable
fun ChatbotScreen(navController: NavController) {
    var userInput by remember { mutableStateOf(TextFieldValue("")) }
    var messages by remember { mutableStateOf(listOf<Message>()) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGreen)
            .padding(8.dp)
    ) {
        // Header
        Text(
            text = "🤖 Guardian GPT",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MidnightBlue,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Chat area
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            if (messages.isEmpty()) {
                Text(
                    text = "What can I help with?",
                    fontSize = 18.sp,
                    color = MidnightBlue,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    reverseLayout = true,
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(messages.reversed()) { message ->
                        MessageBubble(message)
                    }
                }
            }
        }

        // Input field and send button
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
                placeholder = {
                    Text("Ask anything")
                },
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

                        CoroutineScope(Dispatchers.Main).launch {
                            val reply = if (isInternetAvailable(context)) {
                                OpenAIHelper.getOpenAIResponse(inputText)
                            } else {
                                OfflineChatbot.getFallbackReply(inputText)
                            }

                            messages = messages + Message(reply, false)
                        }

                        userInput = TextFieldValue("")
                    }
                }
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
        horizontalArrangement = alignment
    ) {
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

// ✅ Internet check helper
fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}
