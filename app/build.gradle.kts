plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id(libs.plugins.kotlin.kapt.get().pluginId)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.nur.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.nur.myapplication"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures.viewBinding = true
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.uiautomator)

    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.espresso.contrib)
    androidTestImplementation(libs.espresso.intents)
    androidTestImplementation(libs.hamcrest)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.fragment.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.navigation.testing)
    androidTestImplementation(libs.mockito.core)
    androidTestImplementation(libs.mockito.android)

    implementation(libs.viewBindingDelegate.viewBinding)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    // Lifecycle
    implementation(libs.bundles.lifecycle)
    // Coroutines
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin.coroutines.android)
    //hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    // Retrofit
    implementation(libs.bundles.retrofit)
    // okHttp
    implementation(libs.okhttp3)
    // Implementation project
    implementation(project(":domain"))
    implementation(project(":data"))
}