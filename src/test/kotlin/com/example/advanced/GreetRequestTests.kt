package com.example.advanced

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