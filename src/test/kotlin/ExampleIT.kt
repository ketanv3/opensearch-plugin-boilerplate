import org.hamcrest.Matchers.equalTo
import org.opensearch.test.OpenSearchIntegTestCase

class ExampleIT : OpenSearchIntegTestCase() {

    fun `test demo`() {
        assertThat(true, equalTo(true))
    }

}