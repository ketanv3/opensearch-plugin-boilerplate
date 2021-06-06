package com.example.advanced

import org.opensearch.action.ActionRequest
import org.opensearch.action.ActionRequestValidationException
import org.opensearch.common.io.stream.StreamInput

class GreetRequest(val name: String): ActionRequest() {

    constructor(input: StreamInput) : this(input.readString())

    override fun validate(): ActionRequestValidationException? {
        var errors: ActionRequestValidationException? = null

        if (name.isBlank()) {
            errors = ActionRequestValidationException()
            errors.addValidationError("'name' cannot be blank")
        }

        return errors
    }

}