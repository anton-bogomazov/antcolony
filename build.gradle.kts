import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.2.21"
    application
}

group = "com.abogomazov.antsim"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("com.abogomazov.antsim.MainKt")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("io.kotest:kotest-runner-junit5-jvm:6.0.7")
    testImplementation("io.kotest:kotest-assertions-core-jvm:6.0.7")
    testImplementation("io.kotest:kotest-property-jvm:6.0.7")
}

kotlin {
    jvmToolchain(23)
}

tasks.test {
    useJUnitPlatform()
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.compilerOptions {
    freeCompilerArgs.set(listOf("-Xcontext-parameters"))
}