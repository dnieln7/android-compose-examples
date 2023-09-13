plugins {
    alias(libs.plugins.library)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.hilt)
}

apply<AndroidLibraryConfigPlugin>()
apply(from = "$rootDir/gradle-scripts/ktlint.gradle")

android {
    namespace = "xyz.dnieln7.portfolio"

    defaultConfig {

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
            }
        }

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.core.ktx)

    implementation(libs.activity.compose)

    implementation(libs.ui.compose)
    implementation(libs.ui.tooling.preview)
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    implementation(libs.material)

    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel.compose)

    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.moshi)
    kapt(libs.moshi.kotlin.codegen)
    implementation(libs.converter.moshi)
    implementation(libs.logging.interceptor)

    implementation(libs.arrow.core)

    implementation(libs.coil.compose)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

    implementation(libs.timber)

    implementation(libs.joda.time)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.ext)

    androidTestImplementation(libs.espresso.core)

    testImplementation(libs.kluent.android)
    androidTestImplementation(libs.kluent.android)

    testImplementation(libs.kotlinx.coroutines.test)

    testImplementation(libs.mockk)

    testImplementation(libs.app.cash.turbine.turbine)

    implementation(project(":core"))
    implementation(project(":core-ui"))
    implementation(project(":core-res"))
}
