plugins {
    java
    kotlin("jvm")
}

group = "com.ef"

repositories {
    mavenCentral()
}

val jacksonVersion: String by project
val logbackVersion: String by project

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
}
