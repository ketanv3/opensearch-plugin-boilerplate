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

package org.opensearch.example.integTest

import org.apache.http.util.EntityUtils
import org.hamcrest.Matchers.containsString
import org.hamcrest.Matchers.hasItem
import org.opensearch.action.admin.cluster.node.info.NodesInfoAction
import org.opensearch.action.admin.cluster.node.info.NodesInfoRequest
import org.opensearch.action.admin.cluster.node.info.PluginsAndModules
import org.opensearch.action.admin.cluster.state.ClusterStateAction
import org.opensearch.action.admin.cluster.state.ClusterStateRequest
import org.opensearch.client.Request
import org.opensearch.test.OpenSearchIntegTestCase
import org.opensearch.test.OpenSearchIntegTestCase.ClusterScope
import org.opensearch.test.OpenSearchIntegTestCase.Scope

@ClusterScope(scope = Scope.SUITE, numDataNodes = 0, numClientNodes = 0)
class PluginInstalledIT: OpenSearchIntegTestCase() {

    fun `test plugin is installed simple`() {
        val request = Request("GET", "/_cat/plugins")
        val response = getRestClient().performRequest(request)
        val body = EntityUtils.toString(response.entity)

        assertThat(body, containsString("example-opensearch-plugin"))
    }

    fun `test plugin is installed advanced`() {
        val clusterStateRequest = ClusterStateRequest()
        val clusterStateResponse = client().execute(ClusterStateAction.INSTANCE, clusterStateRequest).get()

        val nodesInfoRequest = NodesInfoRequest()
        nodesInfoRequest.clear().addMetric(NodesInfoRequest.Metric.PLUGINS.metricName())
        val nodesInfoResponse = client().execute(NodesInfoAction.INSTANCE, nodesInfoRequest).get()

        val plugins = mutableListOf<String>()
        for (node in clusterStateResponse.state.nodes) {
            val info = nodesInfoResponse.nodesMap[node.id] ?: continue
            val pluginsAndModules = info.getInfo(PluginsAndModules::class.java) ?: continue

            for (pluginInfo in pluginsAndModules.pluginInfos) {
                plugins.add(pluginInfo.name)
            }
        }

        assertThat(plugins, hasItem("example-opensearch-plugin"))
    }

}