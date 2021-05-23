# opensearch-plugin-boilerplate
OpenSearch plugin boilerplate written in Kotlin.

Until OpenSearch and other artifacts are published to Maven Central [OpenSearch#581](https://github.com/opensearch-project/OpenSearch/issues/581), plugins may require building all their dependencies and publishing them to Maven local.

### Publish OpenSearch to Maven Local

Use the `1.0.0-rc1` tag to have a stable release of OpenSearch. Build and publish artifacts to the local Maven repository.

```shell
# Clone the repo and checkout a stable tag.
git clone git@github.com:opensearch-project/OpenSearch.git
cd OpenSearch
git checkout 1.0.0-rc1

# Publish the build artifacts to local maven repository.
./gradlew publishToMavenLocal -Dbuild.version_qualifier=beta1 -Dbuild.snapshot=false
```

### Building, Testing and Running the Plugin
```shell
# To build the plugin distribution.
./gradlew build
./gradlew bundlePlugin

# To run the tests.
./gradlew test
./gradlew integTest

# To run a sample cluster with the plugin pre-installed.
./gradlew run
```