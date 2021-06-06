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

import org.opensearch.action.ActionRequest
import org.opensearch.action.ActionRequestValidationException
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

class GreetRequest: ActionRequest, ToXContentObject {

    companion object {
        private val NAME_FIELD = ParseField("name")

        private val PARSER = ConstructingObjectParser<GreetRequest, Void>(
            "greet_request",
            true,
            Function { args -> GreetRequest(args[0] as String) }
        )

        init {
            PARSER.declareString(constructorArg(), NAME_FIELD)
        }

        fun fromXContent(parser: XContentParser): GreetRequest {
            return PARSER.apply(parser, null)
        }
    }

    val name: String

    constructor(name: String) {
        this.name = name
    }

    constructor(input: StreamInput) : super(input) {
        this.name = input.readString()
    }

    override fun validate(): ActionRequestValidationException? {
        var errors: ActionRequestValidationException? = null

        if (name.isBlank()) {
            errors = ActionRequestValidationException()
            errors.addValidationError("'name' cannot be blank")
        }

        return errors
    }

    override fun writeTo(out: StreamOutput) {
        super.writeTo(out)
        out.writeString(name)
    }

    override fun toXContent(builder: XContentBuilder, params: ToXContent.Params): XContentBuilder {
        return builder
            .startObject()
            .field(NAME_FIELD.preferredName, name)
            .endObject()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GreetRequest

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

}