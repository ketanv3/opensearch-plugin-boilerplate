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

package com.example

import com.example.advanced.GreetAction
import com.example.advanced.GreetTransportAction
import org.opensearch.cluster.metadata.IndexNameExpressionResolver
import org.opensearch.cluster.node.DiscoveryNodes
import org.opensearch.common.settings.ClusterSettings
import org.opensearch.common.settings.IndexScopedSettings
import org.opensearch.common.settings.Settings
import org.opensearch.common.settings.SettingsFilter
import org.opensearch.plugins.ActionPlugin
import org.opensearch.plugins.Plugin
import org.opensearch.rest.RestController
import java.util.function.Supplier

class ExamplePlugin : Plugin(), ActionPlugin {

    companion object {
        const val BASE_PATH = "/_plugins/example-plugin"
    }

    override fun getRestHandlers(
        settings: Settings?,
        restController: RestController?,
        clusterSettings: ClusterSettings?,
        indexScopedSettings: IndexScopedSettings?,
        settingsFilter: SettingsFilter?,
        indexNameExpressionResolver: IndexNameExpressionResolver?,
        nodesInCluster: Supplier<DiscoveryNodes>?
    ) = listOf(
        com.example.simple.GreetRestAction(),
        com.example.advanced.GreetRestAction()
    )

    override fun getActions() = listOf(
        ActionPlugin.ActionHandler(GreetAction.INSTANCE, GreetTransportAction::class.java)
    )

}