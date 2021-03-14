import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.1"
	id("io.spring.dependency-management") version "1.0.10.RELEASE"
	kotlin("jvm") version "1.4.21"
	kotlin("plugin.spring") version "1.4.21"
	kotlin("plugin.jpa") version "1.4.21"
	id("io.gitlab.arturbosch.detekt") version "1.15.0"
}

group = "com.ef"
java.sourceCompatibility = JavaVersion.VERSION_11

allprojects {
	repositories {
		mavenCentral()
		maven(url = "https://www.jitpack.io")
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "11"
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}

buildscript {
	repositories {
		jcenter()
	}
}

repositories {
	mavenCentral()
	jcenter()
}

val junitVersion: String by project
val flywayVersion: String by project
val javaJWTVersion: String by project
val springSecurityVersion: String by project
val testContainersVersion: String by project
val detektVersion: String by project

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.auth0:java-jwt:$javaJWTVersion")
	implementation("org.springframework.security:spring-security-core:$springSecurityVersion")
	implementation("org.springframework.security:spring-security-web:$springSecurityVersion")
	implementation("org.springframework.security:spring-security-config:$springSecurityVersion")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.postgresql:postgresql")
	implementation("org.flywaydb:flyway-core:$flywayVersion")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.4")
	implementation("com.github.tomakehurst:wiremock:2.27.2")
	implementation(project(":base"))
	implementation(project(":testing"))
	implementation(project(":client"))
	implementation(project(":preprocess"))

	testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
	testImplementation("org.mockito:mockito-inline:3.6.28")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
	testImplementation("org.testcontainers:testcontainers:$testContainersVersion")
	testImplementation("org.awaitility:awaitility:4.0.3")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

sourceSets {
	create("integrationTest") {
		withConvention(org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class) {
			kotlin.srcDir("src/integrationTest/kotlin")
			java.srcDir("src/integrationTest/java")
			resources.srcDir("src/integrationTest/resources")
			compileClasspath += sourceSets["main"].output + configurations["testRuntimeClasspath"]
			runtimeClasspath += output + compileClasspath + sourceSets["test"].runtimeClasspath
		}
	}
}

task<Test>("integrationTest") {
	description = "Runs the integration tests"
	group = "verification"
	testClassesDirs = sourceSets["integrationTest"].output.classesDirs
	classpath = sourceSets["integrationTest"].runtimeClasspath
}

tasks.register<CreateFlywayMigration>("flywayCreate")

detekt {
	toolVersion = detektVersion
	config = files("detekt-config.yml")
	buildUponDefaultConfig = true
	reports {
		xml {
			enabled = true
			destination = file("build/reports/detekt/detekt.xml")
		}
		html {
			enabled = true
			destination = file("build/reports/detekt/detekt.html")
		}
		txt {
			enabled = true
			destination = file("build/reports/detekt/detekt.txt")
		}
	}
}