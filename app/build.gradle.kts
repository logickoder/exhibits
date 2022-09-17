plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlinx-serialization")
}

val composeVersion = rootProject.extra.get("compose_version") as String
val kotlinVersion = rootProject.extra.get("kotlin_version") as String
val ktorVersion = rootProject.extra.get("ktor_version") as String

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "dev.logickoder.exhibits"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildTypes {
            release {
                isMinifyEnabled = true
                signingConfig = signingConfigs.getByName("debug")
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }

        compileOptions {
            // Flag to enable support for the new language APIs
            isCoreLibraryDesugaringEnabled = true
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }

        kotlinOptions {
            jvmTarget = "11"
            freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
        }

        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = "1.3.0"
        }

        packagingOptions {
            resources {
                excludes.add("/META-INF/{AL2.0,LGPL2.1}")
            }
        }
    }
}


dependencies {
    // for using some java 8 classes like LocalDate with older versions of android
    //noinspection GradleDependency
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.5.1")

    implementation("androidx.core:core-splashscreen:1.0.0")

    // Material Design
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.compose.material:material:$composeVersion")

    // Constraint Layout
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    // Icons
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")

    // Google Accompanist
    val accompanistVersion = "0.25.1"
    implementation("com.google.accompanist:accompanist-swiperefresh:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-placeholder-material:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion")

    // Coil
    implementation("io.coil-kt:coil-compose:2.2.1")

    // Compose
    implementation("androidx.activity:activity-compose:1.5.1")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")

    // Appyx Navigation
    implementation("com.bumble.appyx:core:1.0-alpha08")

    // lifecycle
    val lifecycleVersion = "2.6.0-alpha01"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")

    // preferences datastore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // kotlinx serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.0")

    // ktor
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
    implementation("io.ktor:ktor-client-serialization:$ktorVersion")
    implementation("io.ktor:ktor-client-logging:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.github.aakira:napier:2.6.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
}