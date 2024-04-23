plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlinx.kover") version "0.7.5"
    id("org.jlleitschuh.gradle.ktlint") version "11.6.1"
}

android {
    namespace = "com.example.studentportal"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.studentportal"
        minSdk = 29
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
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    koverReport {
        androidReports("debug") {
            filters {
                excludes {
                    annotatedBy(
                        "androidx.compose.ui.tooling.preview.Preview",
                        "androidx.compose.runtime.Composable"
                    )
                    packages("com.example.studentportal.databinding")
                }
            }
            verify {
                rule {
                    isEnabled = true
                    bound {
                        minValue = 80 // Minimum coverage percentage
                    }
                }
            }
        }
    }
    testOptions {
        unitTests {
            this.isIncludeAndroidResources = true
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.5")
    implementation("androidx.compose.ui:ui:1.6.5")
    implementation("androidx.compose.ui:ui-tooling:1.6.5")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.5")
    implementation("androidx.test.ext:junit-ktx:1.1.5")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.moshi:moshi:1.14.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")
    implementation("com.squareup.moshi:moshi-adapters:1.14.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    // Koin
    implementation("io.insert-koin:koin-core:3.5.3")
    implementation("io.insert-koin:koin-android:3.5.3")
    debugImplementation("androidx.fragment:fragment-testing:1.6.2")
    testImplementation("io.insert-koin:koin-test:3.5.3")
    androidTestImplementation("io.insert-koin:koin-test:3.5.3")
    testImplementation("io.insert-koin:koin-test:3.5.3")
    testImplementation("io.insert-koin:koin-test-junit4:3.5.3")

    // Unit Tests
    testImplementation("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:1.13.9")
    testImplementation("org.robolectric:robolectric:4.11.1")
    testImplementation("com.google.truth:truth:1.2.0")
    testImplementation("androidx.test:core-ktx:1.5.0")
    testImplementation("androidx.compose.ui:ui-test-junit4:1.6.5")
    testImplementation("org.mockito:mockito-core:5.11.0")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.5")

    // UI tests
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation("com.google.truth:truth:1.2.0")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
