package com.example

import com.example.advanced.GreetAction
import com.example.advanced.GreetRequest
import org.hamcrest.Matchers.equalTo
import org.opensearch.test.OpenSearchIntegTestCase

class GreetActionIT: OpenSearchIntegTestCase() {

    override fun nodePlugins() = listOf(ExamplePlugin::class.java)

    override fun transportClientPlugins() = listOf(ExamplePlugin::class.java)

    fun `test greet action`() {
        val name = randomAlphaOfLength(8)
        val request = GreetRequest(name)
        val response = client().execute(GreetAction.INSTANCE, request).get()
        assertThat(response.name, equalTo(name))
    }

}