import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.mansi.guardianangel"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.mansi.guardianangel"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // ✅ Load API key from local.properties safely
        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            localProperties.load(localPropertiesFile.inputStream())
            val openAiKey = localProperties.getProperty("OPENAI_API_KEY")
            if (openAiKey != null) {
                buildConfigField("String", "OPENAI_API_KEY", "\"$openAiKey\"")
            } else {
                throw GradleException("OPENAI_API_KEY not found in local.properties")
            }
        } else {
            throw GradleException("local.properties file not found!")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    // ✅ AndroidX + Compose core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // ✅ Jetpack Compose UI & Tooling
    implementation("androidx.compose.ui:ui:1.6.0")
    implementation("androidx.compose.material:material:1.6.0")
    implementation("androidx.compose.material:material-icons-extended:1.6.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.0")
    debugImplementation("androidx.compose.ui:ui-tooling:1.6.0")

    // ✅ Lifecycle + ViewModel Compose
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.compose.runtime:runtime-livedata:1.5.1")

    // ✅ Firebase (auth, firestore, database)
    implementation(platform("com.google.firebase:firebase-bom:32.7.2"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-database-ktx")

    // ✅ Navigation
    implementation("androidx.navigation:navigation-compose:2.7.6")

    // ✅ Location services
    implementation("com.google.android.gms:play-services-location:21.0.1")

    // ✅ Permissions
    implementation("com.google.accompanist:accompanist-permissions:0.28.0")

    // ✅ Accompanist System UI & Animation
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.33.1-alpha")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.34.0")

    // ✅ DataStore
    implementation("androidx.datastore:datastore-preferences:1.1.0-alpha05")

    // ✅ Appcompat
    implementation("androidx.appcompat:appcompat:1.6.1")

    // ✅ Networking
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("org.json:json:20210307")

    // ✅ Other utilities
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("androidx.compose.animation:animation:1.6.0")
}
