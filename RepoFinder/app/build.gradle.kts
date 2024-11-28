plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.repofinder"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.repofinder"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    viewBinding{
        enable = true
    }
}

dependencies {
    // Core dependencies
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.1") // Latest lifecycle version
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.1") // Latest lifecycle version
    implementation("androidx.room:room-runtime:2.6.1") // Latest Room version
    implementation("androidx.compose.material3:material3:1.3.1")  // Latest Material3 UI components
    kapt("androidx.room:room-compiler:2.6.1") // Latest Room compiler version
    implementation("androidx.room:room-ktx:2.6.1") // Latest Room KTX version
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.1") // Latest lifecycle version

    // UI (RecyclerView, CardView)
    implementation("androidx.recyclerview:recyclerview:1.3.1") // Latest RecyclerView version
    implementation("androidx.cardview:cardview:1.0.0") // Latest CardView version
    implementation("androidx.paging:paging-runtime:3.2.1") // Latest Paging version

    // Coroutines (for async tasks)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3") // Latest Kotlin Coroutines version

    // Jetpack Compose (UI Components)
    implementation("androidx.compose.ui:ui:1.5.1") // Latest Jetpack Compose UI version
    implementation("androidx.compose.material3:material3:1.3.1")  // Latest Material3 for Compose version
    implementation("androidx.compose.ui:ui-tooling:1.5.1") // Latest UI tooling version
    implementation("androidx.activity:activity-compose:1.8.1") // Latest Activity Compose version
    implementation("io.coil-kt:coil-compose:2.2.2") // Latest Coil Compose version

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.1") // Latest Navigation version
    implementation("androidx.navigation:navigation-ui-ktx:2.7.1") // Latest Navigation version

    // WebView for displaying links
    implementation("androidx.webkit:webkit:1.8.0") // Latest WebView version

    // Image loading (Glide)
    implementation("com.github.bumptech.glide:glide:4.15.1") // Latest Glide version
    kapt("com.github.bumptech.glide:compiler:4.15.1") // Latest Glide compiler version

    // Additional dependencies
    implementation("androidx.activity:activity-ktx:1.8.1") // Latest Activity KTX version
    implementation("androidx.constraintlayout:constraintlayout:2.1.4") // Latest ConstraintLayout version
    implementation("androidx.compose.foundation:foundation:1.5.1") // Latest Foundation version


    // Unit Testing Libraries
    testImplementation("junit:junit:4.13.2") // Latest JUnit version
    androidTestImplementation("androidx.test.ext:junit:1.1.5") // Latest JUnit for Android version
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1") // Latest Espresso version

    // Instrumentation Tests for Jetpack Compose
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.1") // Latest Compose test version
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.1") // Latest Compose tooling version

    // Retrofit core library
    implementation("com.squareup.retrofit2:retrofit:2.9.0") // Retrofit core version

    // Moshi for Retrofit JSON conversion (replaces Gson)
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0") // Moshi converter for Retrofit

    // Optional: Logging Interceptor for debugging network calls
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3") // OkHttp logging version
}
