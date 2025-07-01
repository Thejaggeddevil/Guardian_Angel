package com.mansi.guardianangel



import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull

object OpenAIHelper {

    // 🟢 Paste your API Key here
    private const val apiKey = "BuildConfig.CHATBOT_API_KEY"

    // ✅ OpenRouter DeepSeek Endpoint
    private const val apiUrl = "https://openrouter.ai/api/v1/chat/completions"

    suspend fun getOpenAIResponse(prompt: String): String = withContext(Dispatchers.IO) {
        try {
            val client = OkHttpClient()

            val requestBody = JSONObject()
                .put("model", "deepseek/deepseek-r1:free") // ✅ Sahi model name
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
                .addHeader("HTTP-Referer", "https://guardian-gpt.app") // 🔥 Required by OpenRouter
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
