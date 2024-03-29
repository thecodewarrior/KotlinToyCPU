import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

group = "dev.thecodewarrior.kotlincpu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.apache.logging.log4j:log4j-api:2.12.1")
    implementation("org.apache.logging.log4j:log4j-core:2.12.1")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.12.1")
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = "1.8"
compileKotlin.kotlinOptions.freeCompilerArgs += "-Xuse-experimental=kotlin.ExperimentalUnsignedTypes"
