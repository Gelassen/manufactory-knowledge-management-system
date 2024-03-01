plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("com.gradleup.static-analysis") version "1.4"
    id("io.gitlab.arturbosch.detekt") version "1.9.1"
    id("com.github.spotbugs") version "6.0.8"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
}
// seems project has been changed after 3 years https://github.com/JLLeitschuh/ktlint-gradle

apply(from = rootProject.file("team-props/static-analysis.gradle"))