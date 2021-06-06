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

package org.opensearch.example.advanced

import org.opensearch.example.ExamplePlugin
import org.opensearch.client.node.NodeClient
import org.opensearch.rest.BaseRestHandler
import org.opensearch.rest.RestHandler
import org.opensearch.rest.RestRequest
import org.opensearch.rest.action.RestToXContentListener

class GreetRestAction: BaseRestHandler() {

    override fun routes() = listOf(
        RestHandler.Route(RestRequest.Method.GET, "${ExamplePlugin.BASE_PATH}/advanced/"),
        RestHandler.Route(RestRequest.Method.GET, "${ExamplePlugin.BASE_PATH}/advanced/{name}")
    )

    override fun getName() = "advanced_greet_action"

    override fun prepareRequest(request: RestRequest, client: NodeClient): RestChannelConsumer {
        val name = request.param("name", "anonymous")

        return RestChannelConsumer { channel ->
            client.execute(GreetAction.INSTANCE, GreetRequest(name), RestToXContentListener(channel))
        }
    }

}