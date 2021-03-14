plugins {
    java
    kotlin("jvm")
}

group = "com.ef"

dependencies {
    implementation(project(":base"))
    implementation(project(":external"))
}

