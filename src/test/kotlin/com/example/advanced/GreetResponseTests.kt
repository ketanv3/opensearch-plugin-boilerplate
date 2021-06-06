package com.example.advanced

import org.opensearch.common.io.stream.Writeable
import org.opensearch.common.xcontent.XContentParser
import org.opensearch.test.AbstractSerializingTestCase

class GreetResponseTests: AbstractSerializingTestCase<GreetResponse>() {

    override fun createTestInstance(): GreetResponse {
        return GreetResponse(randomAlphaOfLength(8))
    }

    override fun instanceReader(): Writeable.Reader<GreetResponse> {
        return Writeable.Reader { input -> GreetResponse(input) }
    }

    override fun doParseInstance(parser: XContentParser): GreetResponse {
        return GreetResponse.fromXContent(parser)
    }

}