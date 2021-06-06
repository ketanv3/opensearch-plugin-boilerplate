package com.example.advanced

import org.opensearch.action.ActionResponse
import org.opensearch.common.ParseField
import org.opensearch.common.io.stream.StreamInput
import org.opensearch.common.io.stream.StreamOutput
import org.opensearch.common.xcontent.ConstructingObjectParser
import org.opensearch.common.xcontent.ConstructingObjectParser.constructorArg
import org.opensearch.common.xcontent.ToXContent
import org.opensearch.common.xcontent.ToXContentObject
import org.opensearch.common.xcontent.XContentBuilder
import org.opensearch.common.xcontent.XContentParser
import java.util.function.Function

class GreetResponse(val name: String): ActionResponse(), ToXContentObject {

    companion object {
        val GREET_FIELD = ParseField("hello")

        private val PARSER = ConstructingObjectParser<GreetResponse, Void>(
            "greet_response",
            true,
            Function { args -> GreetResponse(args[0] as String) }
        )

        init {
            PARSER.declareString(constructorArg(), GREET_FIELD)
        }

        fun fromXContent(parser: XContentParser): GreetResponse {
            return PARSER.apply(parser, null)
        }
    }

    constructor(input: StreamInput) : this(input.readString())

    override fun writeTo(output: StreamOutput) {
        output.writeString(name)
    }

    override fun toXContent(builder: XContentBuilder, params: ToXContent.Params): XContentBuilder {
        return builder
            .startObject()
            .field(GREET_FIELD.preferredName, name)
            .endObject()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GreetResponse

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

}