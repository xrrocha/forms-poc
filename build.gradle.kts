plugins {
    val kotlinVersion = "1.9.23"

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
}

group = "ricardo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    val jsonSerializationVersion = "1.6.3"
    val kamlVersion = "0.57.0"
    val loggingVersion = "5.1.0"
    val slf4jVersion = "2.0.12"

    runtimeOnly(kotlin("reflect"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json-jvm:$jsonSerializationVersion")
    implementation("com.charleskorn.kaml:kaml-jvm:$kamlVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.11.0")

    implementation("org.slf4j:slf4j-simple:$slf4jVersion")
    implementation("io.github.oshai:kotlin-logging-jvm:$loggingVersion")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}