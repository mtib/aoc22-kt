import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.20"
    application
}

group = "dev.mtib"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val okioVersion = "3.3.0"
dependencies {
    implementation("com.squareup.okio:okio:$okioVersion")
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}
