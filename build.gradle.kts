plugins {
    kotlin("multiplatform") version "1.8.20"
}

group = "me.markus"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    sourceSets {
        val okioVersion = "3.3.0"
        val commonMain by getting {
            dependencies {
                implementation("com.squareup.okio:okio:$okioVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation("com.squareup.okio:okio-fakefilesystem:$okioVersion")
            }
        }
    }
    macosArm64("native") {
        binaries {
            executable()
        }
    }
}
