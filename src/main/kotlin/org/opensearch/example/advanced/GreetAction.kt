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

import org.opensearch.action.ActionType

class GreetAction: ActionType<GreetResponse>(NAME, ::GreetResponse) {

    companion object {
        const val NAME = "cluster:admin/greet_action"
        val INSTANCE = GreetAction()
    }

}