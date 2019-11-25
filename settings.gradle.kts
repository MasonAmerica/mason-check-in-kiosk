plugins {
    `gradle-enterprise`
}

include(
        ":app",
        ":web:site",
        ":web:server"
)

sourceControl {
    gitRepository(uri("https://github.com/MasonAmerica/mason-apps-build-system.git")) {
        producesModule("com.bymason.build:mason-apps")
    }
}
