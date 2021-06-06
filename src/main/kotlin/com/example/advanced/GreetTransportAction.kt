package com.example.advanced

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