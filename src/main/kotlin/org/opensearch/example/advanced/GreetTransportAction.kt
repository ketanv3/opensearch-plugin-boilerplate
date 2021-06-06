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

import org.opensearch.action.ActionListener
import org.opensearch.action.support.ActionFilters
import org.opensearch.action.support.HandledTransportAction
import org.opensearch.common.inject.Inject
import org.opensearch.tasks.Task
import org.opensearch.transport.TransportService

class GreetTransportAction: HandledTransportAction<GreetRequest, GreetResponse> {

    @Inject
    constructor(
        transportService: TransportService,
        actionFilters: ActionFilters
    ) : super(GreetAction.NAME, transportService, actionFilters, ::GreetRequest)

    override fun doExecute(task: Task, request: GreetRequest, listener: ActionListener<GreetResponse>) {
        listener.onResponse(GreetResponse(request.name))
    }

}