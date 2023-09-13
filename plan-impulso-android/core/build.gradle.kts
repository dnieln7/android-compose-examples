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
    namespace = "xyz.dnieln7.core"

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
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.androidx.appcompat.appcompat)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.timber)

    implementation(libs.joda.time)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.ext)

    androidTestImplementation(libs.espresso.core)

    testImplementation(libs.kluent.android)

    implementation(project(":core-res"))
}
