import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    val openSearchVersion = Versions.getOpenSearchVersion()

    repositories {
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        classpath("org.opensearch.gradle:build-tools:${openSearchVersion}")
    }
}

group = "com.example"
version = Versions.getVersion()

plugins {
    kotlin("jvm") version Versions.kotlinVersion
    java
    idea
}

apply(plugin = "opensearch.opensearchplugin")
apply(plugin = "opensearch.testclusters")
apply(plugin = "opensearch.rest-test")

configure<org.opensearch.gradle.plugin.PluginPropertiesExtension> {
    name = "example-opensearch-plugin"
    description = "OpenSearch plugin boilerplate written in Kotlin"
    classname = "com.example.ExamplePlugin"
    noticeFile = rootProject.file("NOTICE")
    licenseFile = rootProject.file("LICENSE")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("stdlib-common"))

    val openSearchVersion = Versions.getOpenSearchVersion()
    implementation("org.opensearch:opensearch:${openSearchVersion}")

    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")
}

configurations.all {
    resolutionStrategy {
        preferProjectModules()
    }
}

project.tasks.named("dependencyLicenses") {
    enabled = false
}

project.tasks.named("loggerUsageCheck") {
    enabled = false
}

project.tasks.named("validateNebulaPom") {
    enabled = false
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}