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
    }
}

dependencies {

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

    dependencies {
        // Jetpack Compose
        implementation("androidx.activity:activity-compose:1.8.1")
        implementation ("androidx.compose.ui:ui:1.6.0")
        implementation ("androidx.compose.material:material:1.6.0")
        implementation ("androidx.compose.ui:ui-tooling-preview:1.6.0")

        // ViewModel with Compose
        implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
        implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

        // Location services (for getting GPS)
        implementation ("com.google.android.gms:play-services-location:21.0.1")

        // Optional - Permissions handling (accompanist)
        implementation ("com.google.accompanist:accompanist-permissions:0.28.0")

        // Debugging
        debugImplementation ("androidx.compose.ui:ui-tooling:1.6.0")
        //location
        implementation ("com.google.android.gms:play-services-location:21.0.1")
        implementation ("com.google.android.gms:play-services-location:21.0.1")

        implementation ("androidx.activity:activity-compose:1.7.2")
        implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
        implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
        implementation ("androidx.core:core-ktx:1.12.0")
        implementation ("androidx.compose.material3:material3:1.1.2")
        implementation( "androidx.compose.ui:ui:1.5.4")
        implementation ("androidx.compose.ui:ui-tooling-preview:1.5.4")
        implementation( "androidx.navigation:navigation-compose:2.7.6")

        implementation ("androidx.activity:activity-compose:1.7.2")
        implementation ("androidx.compose.material3:material3:1.1.0")
        implementation ("com.google.firebase:firebase-auth-ktx:22.3.1")
        implementation ("com.google.firebase:firebase-firestore-ktx:24.10.2")
        implementation ("com.google.android.gms:play-services-location:21.0.1")

        implementation ("androidx.core:core-ktx:1.12.0")
        implementation( "androidx.compose.ui:ui:1.5.1")
        implementation( "androidx.compose.material:material:1.5.1")
        implementation( "androidx.activity:activity-compose:1.7.2")
        implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
        implementation( "androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
        implementation( "com.google.firebase:firebase-auth-ktx:22.3.0")
        implementation ("com.google.firebase:firebase-firestore-ktx:24.10.0")
        implementation( "com.google.firebase:firebase-database-ktx:20.3.0")

        implementation( "androidx.navigation:navigation-compose:2.7.3")

        implementation ("androidx.datastore:datastore-preferences:1.1.0-alpha05")
        implementation ("androidx.compose.runtime:runtime-livedata:1.5.1")
        dependencies {
            implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
            implementation("com.google.firebase:firebase-auth-ktx")
            implementation("com.google.firebase:firebase-firestore-ktx")
        }
        implementation(platform ("com.google.firebase:firebase-bom:32.7.2"))
        implementation ("com.google.firebase:firebase-auth-ktx")
        implementation ("com.google.firebase:firebase-firestore-ktx")

        // SMS + Location
        implementation ("com.google.android.gms:play-services-location:21.0.1")


        // Accompanist for theme/dark mode switch
        implementation ("com.google.accompanist:accompanist-systemuicontroller:0.33.1-alpha")
        //settingutils
        implementation ("androidx.appcompat:appcompat:1.6.1")

        implementation ("androidx.activity:activity-compose:1.8.0")
        implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
        implementation ("androidx.core:core-ktx:1.10.1")

        implementation ("androidx.compose.material3:material3:1.1.2")
        implementation ("androidx.compose.material:material-icons-extended:1.6.1")
        implementation ("androidx.compose.ui:ui-tooling-preview")

        implementation ("androidx.compose.animation:animation:1.6.0")
        implementation ("com.google.code.gson:gson:2.10.1")
 //chatbot
        //implementation("com.some.chatbot:library:1.0.0")
        // ✅ OkHttp for networking
        implementation("com.squareup.okhttp3:okhttp:4.12.0")
        implementation("org.json:json:20210307") // for JSONObject, JSONArray





    }

}
