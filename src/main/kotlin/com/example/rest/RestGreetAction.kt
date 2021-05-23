package com.example.rest

import com.example.ExamplePlugin.Companion.BASE_PATH
import org.opensearch.client.node.NodeClient
import org.opensearch.common.xcontent.json.JsonXContent
import org.opensearch.rest.*

class RestGreetAction: BaseRestHandler() {

    override fun routes() = listOf(
        RestHandler.Route(RestRequest.Method.GET, "$BASE_PATH/greet"),
        RestHandler.Route(RestRequest.Method.GET, "$BASE_PATH/greet/{name}")
    )

    override fun getName() = "greet_action"

    override fun prepareRequest(request: RestRequest, client: NodeClient): RestChannelConsumer {
        val name = request.param("name", "anonymous")
        return RestChannelConsumer { channel ->
            channel.sendResponse(BytesRestResponse(
                RestStatus.OK,
                JsonXContent
                    .contentBuilder()
                    .startObject()
                    .field("hello", name)
                    .endObject()
            ))
        }
    }

}