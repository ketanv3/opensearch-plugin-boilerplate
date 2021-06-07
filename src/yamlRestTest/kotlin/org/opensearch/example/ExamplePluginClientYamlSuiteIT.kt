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

package org.opensearch.example

import com.carrotsearch.randomizedtesting.annotations.Name
import com.carrotsearch.randomizedtesting.annotations.ParametersFactory
import org.opensearch.test.rest.yaml.ClientYamlTestCandidate
import org.opensearch.test.rest.yaml.OpenSearchClientYamlSuiteTestCase
import java.lang.Exception

class ExamplePluginClientYamlSuiteIT(
    @Name("yaml") testCandidate: ClientYamlTestCandidate
) : OpenSearchClientYamlSuiteTestCase(testCandidate) {

    companion object {
        @JvmStatic
        @ParametersFactory
        @Throws(Exception::class)
        fun parameters(): Iterable<Array<Any>> {
            return createParameters()
        }
    }

}