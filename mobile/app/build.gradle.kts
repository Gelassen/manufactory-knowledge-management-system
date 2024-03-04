import com.android.build.gradle.internal.tasks.factory.dependsOn
import java.util.Locale

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    /* alias(libs.plugins.kotlin.jvm) seems it is already added despite on project level explicitly set 'apply false' clause */
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
    lint {
        abortOnError = true
//        disable 'ContentDescription'
    }
    applicationVariants.configureEach {
        val variantName =
            name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ENGLISH) else it.toString() }
        val lintTaskName = "lint$variantName"
        val lintTask = tasks.named("lint$variantName")
        assembleProvider.dependsOn(lintTask, tasks.named(lintTaskName))
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.constraintlayout.appcompat)
    implementation(libs.android.material)
    implementation(libs.androidx.constraintlayout)

    // network
    implementation(libs.retrofit2.retrofit)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // dependency injection
    implementation(libs.dagger)
    implementation(libs.dagger.android.support)
    implementation(libs.dagger.android)


    // storage
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)

    ksp(libs.androidx.room.compiler)
    ksp(libs.ksp.dagger.compiler)
    ksp(libs.ksp.dagger.android.processor)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.core.ktx)
    androidTestImplementation(libs.kotlinx.coroutines.test)
}