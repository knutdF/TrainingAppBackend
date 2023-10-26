val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "1.9.10"
    id("io.ktor.plugin") version "2.3.5"
    kotlin("plugin.serialization") version "1.5.10" // Verwenden Sie die aktuelle Version
}

group = "com.trainingsapp"
version = "0.0.1"

application {
    mainClass.set("com.trainingsapp.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
   // implementation("io.ktor:ktor-serialization-jackson-jvm")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation("io.ktor:ktor-server-cors-jvm")
    implementation("io.ktor:ktor-server-websockets-jvm")
    implementation("io.ktor:ktor-server-host-common-jvm")
    implementation("io.ktor:ktor-server-status-pages-jvm")
    implementation("io.ktor:ktor-server-call-logging-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.lettuce:lettuce-core:6.2.6.RELEASE")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    implementation ("redis.clients:jedis:4.3.1")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-client-logging:$ktor_version")
    implementation("io.ktor:ktor-server-call-logging:$ktor_version")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
