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

package com.example.simple

import com.example.ExamplePlugin.Companion.BASE_PATH
import org.opensearch.client.node.NodeClient
import org.opensearch.common.xcontent.json.JsonXContent
import org.opensearch.rest.BaseRestHandler
import org.opensearch.rest.BytesRestResponse
import org.opensearch.rest.RestHandler
import org.opensearch.rest.RestRequest
import org.opensearch.rest.RestStatus

class GreetRestAction : BaseRestHandler() {

    override fun routes() = listOf(
        RestHandler.Route(RestRequest.Method.GET, "$BASE_PATH/simple"),
        RestHandler.Route(RestRequest.Method.GET, "$BASE_PATH/simple/{name}")
    )

    override fun getName() = "simple_greet_action"

    override fun prepareRequest(request: RestRequest, client: NodeClient): RestChannelConsumer {
        val name = request.param("name", "anonymous")
        return RestChannelConsumer { channel ->
            channel.sendResponse(
                BytesRestResponse(
                    RestStatus.OK,
                    JsonXContent
                        .contentBuilder()
                        .startObject()
                        .field("hello", name)
                        .endObject()
                )
            )
        }
    }

}