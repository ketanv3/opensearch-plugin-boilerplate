package com.example.advanced

import org.opensearch.action.ActionType

class GreetAction: ActionType<GreetResponse>(NAME, ::GreetResponse) {

    companion object {
        const val NAME = "cluster:admin/greet_action"
        val INSTANCE = GreetAction()
    }

}