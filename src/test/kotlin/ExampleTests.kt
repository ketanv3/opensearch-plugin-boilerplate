import org.hamcrest.Matchers.equalTo
import org.opensearch.test.OpenSearchSingleNodeTestCase

class ExampleTests : OpenSearchSingleNodeTestCase() {

    fun `test demo`() {
        assertThat(true, equalTo(true))
    }

}