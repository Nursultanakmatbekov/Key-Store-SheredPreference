plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id(libs.plugins.kotlin.kapt.get().pluginId)
}

android {
    namespace = "com.nur.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation("junit:junit:4.12")
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso.core)

    // Coroutines
    implementation(libs.kotlin.coroutines.core)
    //hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    // Retrofit
    implementation(libs.bundles.retrofit)
    // okHttp
    implementation(libs.okhttp3.okHttp)
    implementation(libs.okhttp3.logging.interceptor)
    //security
    implementation(libs.security.crypto)

    implementation(libs.safetynet)

    // Implementation project
    implementation(project(":domain"))
}