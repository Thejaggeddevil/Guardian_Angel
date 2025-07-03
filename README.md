# 👼 Guardian_Angel

**Guardian_Angel** is a smart safety and health companion Android app built using **Kotlin** and **Jetpack Compose**.  
This app includes features like:

- 🧠 **Chatbot (AI)** – Powered by OpenRouter/DeepSeek via secure API key using `BuildConfig`
- 🧭 **Location tracking** – with `play-services-location`
- 🔐 **Firebase integration** – for auth, Firestore, and Realtime DB
- 🎨 Beautiful Compose UI and animations
- 📲 Works seamlessly on all Android devices (min SDK 23+)

---

## 📸 Screenshots

> Add screenshots in a `/screenshots` folder and link them below:

| Home Screen | Chatbot |
|-------------|---------|
| ![Home](screenshots/home.png) | ![Chatbot](screenshots/chatbot.png) |

---

## 🚀 Features

- ✅ Jetpack Compose + Material3
- ✅ Firebase Auth & Firestore
- ✅ Realtime Chatbot (online + offline)
- ✅ Clean architecture with Kotlin best practices
- ✅ Secure API key (never exposed in code)

---

## 🔧 Tech Stack

| Layer        | Libraries/Tools                      |
|--------------|--------------------------------------|
| UI           | Jetpack Compose, Material3           |
| Backend      | Firebase (Auth, Firestore, DB)       |
| API          | OpenRouter (DeepSeek-R1)             |
| Networking   | OkHttp, JSON                         |
| Location     | Google Play Location Services        |
| Build System | Gradle + Kotlin DSL                  |
| Security     | API key via `local.properties`       |

---

## 🔐 API Key Management

> Your API key is **NOT hardcoded**. It is:

- Stored safely in `local.properties`
- Accessed using `BuildConfig.OPENAI_API_KEY`
- Excluded from Git using `.gitignore`
