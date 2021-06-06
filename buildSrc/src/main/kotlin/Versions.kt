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

object Versions {

    const val kotlinVersion = "1.4.30"

    // Plugin version "<major>.<minor>.<patch>[-<qualifier>]
    private const val version = "1.0.0"

    // Version of OpenSearch to build the plugin against
    private const val openSearchVersion = "1.0.0-rc1"

    fun getVersion(): String {
        return when(System.getProperty("build.snapshot", "true")) {
            "true" -> "$version-SNAPSHOT"
            else -> version
        }
    }

    fun getOpenSearchVersion() = System.getProperty("opensearch.version", openSearchVersion)!!

}