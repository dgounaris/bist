plugins {
    java
    kotlin("jvm")
}

group = "com.ef"

val unirestVersion: String by project
val testContainersVersion: String by project

dependencies {
    implementation(project(":base"))
    implementation("com.konghq:unirest-java:$unirestVersion")
    implementation("com.konghq:unirest-objectmapper-jackson:$unirestVersion")
    implementation("org.jooq:jooq:3.14.4")
    implementation("org.testcontainers:testcontainers:$testContainersVersion")
}
