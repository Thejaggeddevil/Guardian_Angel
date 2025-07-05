package com.mansi.guardianangel

import com.mansi.guardianangel.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull

object OpenAIHelper {

    private val apiKey = BuildConfig.OPENAI_API_KEY
    private const val apiUrl = "https://openrouter.ai/api/v1/chat/completions"

    // 🔍 Intelligent query filter
    private fun isNameQuery(input: String): Boolean {
        val lowerInput = input.lowercase()
        val nameQueries = listOf(
            "what is your name", "who are you", "your name", "tumhara naam", "tera naam",
            "tum kaun ho", "tu kaun hai", "aap kaun ho", "tum kisne banaya", "kisne banaya",
            "who created you", "who made you", "what's your purpose", "tere creator", "creator name"
        )
        return nameQueries.any { it in lowerInput }
    }

    suspend fun getOpenAIResponse(prompt: String): String = withContext(Dispatchers.IO) {
        // ✨ Custom GPT Intro if name-related query
        if (isNameQuery(prompt)) {
            return@withContext """
                👋 Hello! I'm Guardian — your AI-powered safety assistant.
                I was created with care by Indian Android developer!. 🇮🇳
                My mission is to assist, guide, and protect you — whether you're in danger or just curious. 🛡️
            """.trimIndent()
        }

        try {
            val client = OkHttpClient()

            val requestBody = JSONObject()
                .put("model", "deepseek/deepseek-r1:free")
                .put("messages", JSONArray().put(JSONObject().put("role", "user").put("content", prompt)))
                .put("temperature", 0.7)

            val body = RequestBody.create(
                "application/json".toMediaTypeOrNull(),
                requestBody.toString()
            )

            val request = Request.Builder()
                .url(apiUrl)
                .addHeader("Authorization", apiKey)
                .addHeader("Content-Type", "application/json")
                .addHeader("HTTP-Referer", "https://guardian-gpt.app")
                .addHeader("X-Title", "GuardianGPT")
                .post(body)
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()

            if (response.isSuccessful && responseBody != null) {
                val jsonResponse = JSONObject(responseBody)
                val message = jsonResponse
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content")

                return@withContext message.trim()
            } else {
                return@withContext "⚠️ API Error: ${response.code} - ${response.message}"
            }

        } catch (e: Exception) {
            return@withContext "❌ Exception: ${e.localizedMessage}"
        }
    }
}
