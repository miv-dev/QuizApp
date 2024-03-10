
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true

            export(libs.decompose.core)
            export(libs.essenty.lifecycle)
            export(libs.koin.core)
            export(libs.mvikotlin.main)
            export(libs.mvikotlin.core)
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            api(libs.decompose.core)
            api(libs.essenty.lifecycle)
            api(libs.koin.core)
            api(libs.mvikotlin.main)

            api(libs.mvikotlin.core)
            implementation(libs.mvikotlin.corotines)
            api(libs.mvikotlin.rx)
            api(libs.coroutines.core)        }
    }
}

android {
    namespace = "ru.miv.dev.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
