object Versions {

    const val kotlinVersion = "1.4.30"

    // Plugin version "<major>.<minor>.<patch>[-<qualifier>]
    private const val version = "1.0.0"

    // Version of OpenSearch to build the plugin against
    private const val openSearchVersion = "1.0.0-rc1"

    fun getVersion(): String {
        return when(System.getProperty("build.snapshot", "true")) {
            "true" -> "$version-SNAPSHOT"
            else -> version
        }
    }

    fun getOpenSearchVersion() = System.getProperty("opensearch.version", openSearchVersion)!!

}