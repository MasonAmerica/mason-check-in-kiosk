plugins {
    id("org.jetbrains.kotlin.js")
}

kotlin {
    target {
        useCommonJs()
        nodejs()
    }

    sourceSets["main"].dependencies {
        implementation(Config.Libs.Kotlin.js)
        implementation(Config.Libs.Kotlin.coroutinesJs)
    }
}
