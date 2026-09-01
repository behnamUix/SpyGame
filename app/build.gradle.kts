plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlinx.serialization)
    id("org.jetbrains.kotlin.plugin.compose")


}

android {

    namespace = "com.behnamuix.spygame"
    compileSdk = 36

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        applicationId = "com.behnamuix.spygame"
        minSdk = 26
        targetSdk = 36
        versionCode = 6
        versionName = "2.3.0"

        buildConfigField(
            "String",
            "API_USERNAME",
            "\"09304050718\""
        )

        buildConfigField(
            "String",
            "API_PASSWORD",
            "\"540703a2-dae1-4b20-8951-640ef0069f25\""
        )
    }

    signingConfigs {
        create("release") {
            storeFile = file("release.jks")
            storePassword = "12345678"
            keyAlias = "release"
            keyPassword = "12345678"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    kotlin {
        jvmToolchain(17)
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            freeCompilerArgs += listOf(
                "-Xskip-metadata-version-check",
                "-Xallow-kotlin-package"
            )
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.junit.ktx)
    implementation(libs.androidx.ui.test.junit4)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    //Room
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    //Koin
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    //IconExtended
    implementation(libs.androidx.compose.material.icons.extended)

    //Coil
    implementation(libs.coil.compose)

    //Work
    implementation(libs.androidx.work)

    //Navigation
    implementation(libs.androidx.navigation.compose)

    //DataStore
    implementation(libs.androidx.datastore.preferences)


    // KTOR
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)

    //Coil
    implementation(libs.coil3.coil.compose)
    implementation(libs.coil.network.okhttp)

    implementation(libs.androidx.media3.session)
    implementation(libs.androidx.media3.exoplayer)

    implementation(libs.kotlinx.coroutines.android)
    testImplementation(kotlin("test"))

    testImplementation(libs.mockito.kotlin)

    implementation(libs.zxing.android.embedded)


}