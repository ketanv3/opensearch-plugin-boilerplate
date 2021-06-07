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
}

configurations.all {
    resolutionStrategy {
        preferProjectModules()
    }
}

tasks.named("licenseHeaders") {
    enabled = true
}

tasks.named("dependencyLicenses") {
    enabled = false
}

tasks.named("loggerUsageCheck") {
    enabled = false
}

tasks.named("validateNebulaPom") {
    enabled = false
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

/**
 * Temporary workaround for - https://github.com/opensearch-project/OpenSearch/issues/766
 * 1. The 'run' task doesn't launch a test cluster.
 * 2. The test cluster launched by the 'integTest' task doesn't have the plugin pre-installed.
 *
 * Workaround:
 * 1. Create and register a new test cluster for the 'run' task.
 * 2. For the test clusters launched by 'run' and 'integTest' tasks, pre-install the bundled plugin ZIP.
 */
val bundlePlugin = tasks.getByName<Zip>("bundlePlugin")
val testClusters = extensions.getByName<NamedDomainObjectContainer<OpenSearchCluster>>(TestClustersPlugin.EXTENSION_NAME)

listOf("runTask", "integTest").forEach { name -> testClusters.maybeCreate(name).plugin(bundlePlugin.archiveFile) }

tasks.named<RunTask>("run") {
    useCluster(testClusters["runTask"])
}