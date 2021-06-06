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

import org.opensearch.common.io.stream.Writeable
import org.opensearch.common.xcontent.XContentParser
import org.opensearch.test.AbstractSerializingTestCase

class GreetRequestTests: AbstractSerializingTestCase<GreetRequest>() {

    override fun createTestInstance(): GreetRequest {
        return GreetRequest(randomAlphaOfLength(8))
    }

    override fun instanceReader(): Writeable.Reader<GreetRequest> {
        return Writeable.Reader { input -> GreetRequest(input) }
    }

    override fun doParseInstance(parser: XContentParser): GreetRequest {
        return GreetRequest.fromXContent(parser)
    }

}