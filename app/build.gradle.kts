plugins {
    alias(libs.plugins.android.application)
}

import java.util.Properties

val releaseProps = Properties().apply {
    val f = rootProject.file("local.properties")
    if (f.exists()) f.inputStream().use { load(it) }
}
val keystorePass = releaseProps.getProperty("RELEASE_STORE_PASSWORD", "android")
val releaseKeyAlias = releaseProps.getProperty("RELEASE_KEY_ALIAS", "android")
val keyPass = releaseProps.getProperty("RELEASE_KEY_PASSWORD", "android")


android {
    namespace = "com.krscripts.app"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.krscripts.next"
        minSdk = 23
        targetSdk = 28
        versionCode = 20260915
        versionName = "26.9.15"
        buildConfigField("String", "FRAMEWORK_VERSION", "\"0.2.0\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    signingConfigs {
        create("release") {
            storeFile = releaseProps.getProperty("RELEASE_STORE_FILE")?.let { file(it) }
                ?: rootProject.file("keystore/testkey/testkey.p12")
            storePassword = keystorePass
            keyAlias = releaseKeyAlias
            keyPassword = keyPass
            enableV1Signing = true
            enableV2Signing = true
            enableV3Signing = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.material)
    implementation(project(":core"))
}
configurations.all {
    exclude(group = "androidx.profileinstaller", module = "profileinstaller")
}
tasks.configureEach {
    val n = name.lowercase()
    if (n.contains("artprofile") || n.contains("startupprofile")) {
        enabled = false
    }
}
