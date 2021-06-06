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