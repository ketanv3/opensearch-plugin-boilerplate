/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 *
 * Modifications Copyright OpenSearch Contributors. See
 * GitHub history for details.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.opensearch.gradle.testclusters.OpenSearchCluster
import org.opensearch.gradle.testclusters.RunTask
import org.opensearch.gradle.testclusters.TestClustersPlugin

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

group = "org.opensearch.example"
version = Versions.getVersion()

plugins {
    kotlin("jvm") version Versions.kotlinVersion
    java
    idea
}

apply(plugin = "opensearch.opensearchplugin")
apply(plugin = "opensearch.testclusters")
apply(plugin = "opensearch.rest-test")
apply(plugin = "opensearch.yaml-rest-test")
apply(plugin = "opensearch.internal-cluster-test")

configure<org.opensearch.gradle.plugin.PluginPropertiesExtension> {
    name = "example-opensearch-plugin"
    description = "OpenSearch plugin template written in Kotlin"
    classname = "org.opensearch.example.ExamplePlugin"
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

project.tasks.named("licenseHeaders") {
    enabled = true
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

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

/**
 * Temporary workaround for - https://github.com/opensearch-project/OpenSearch/issues/766
 * Due to a bug, the 'run' task doesn't launch a test cluster. Moreover, the test cluster launched by the
 * 'integTest' task doesn't have the plugin pre-installed.
 *
 * Workaround:
 * 1. Manually create a test cluster for the 'run' task.
 * 2. Manually install the bundled plugin zip in the test clusters 'run' and 'integTest' tasks.
 */

val bundlePlugin: Zip = project.tasks.named<Zip>("bundlePlugin").get()
val testClusters = project.extensions.getByName<NamedDomainObjectContainer<OpenSearchCluster>>(TestClustersPlugin.EXTENSION_NAME)

testClusters.create("runTask")
testClusters["runTask"].plugin(bundlePlugin.archiveFile)
testClusters["integTest"].plugin(bundlePlugin.archiveFile)

// Installing the plugin on every test cluster is not recommended but can be done by uncommenting this line:
// testClusters.forEach { cluster -> cluster.plugin(bundlePlugin.archiveFile) }

project.tasks.named<RunTask>("run") {
    useCluster(testClusters["runTask"])
}