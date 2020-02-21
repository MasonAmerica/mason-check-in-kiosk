plugins {
    `gradle-enterprise`
}

include(
        ":app",
        ":web:site",
        ":web:server",

        ":auth"
)
