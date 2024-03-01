plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.gradleup.static-analysis") version "1.4"
}

android {
    namespace = "io.github.gelassen.manufactory_knowledge_management"
    compileSdk = libs.versions.compileSdkVersion.get().toInt()

    defaultConfig {
        applicationId = "io.github.gelassen.manufactory_knowledge_management"
        minSdk = libs.versions.minSdkVersion.get().toInt()
        targetSdk = libs.versions.targetSdkVersion.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get().toString()
    }


}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.constraintlayout.appcompat)
    implementation(libs.android.material)
    implementation(libs.androidx.constraintlayout)
    // https://mvnrepository.com/artifact/com.gradleup/plugin
//    implementation("com.gradleup:plugin:1.4")


    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}

apply(from = rootProject.file("team-props/static-analysis.gradle"))

