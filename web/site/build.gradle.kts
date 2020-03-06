plugins {
    id("org.jetbrains.kotlin.js")
}

kotlin {
    target {
        useCommonJs()
        browser()
    }
}

dependencies {
    implementation(Config.Libs.Kotlin.js)
    implementation(Config.Libs.Kotlin.coroutinesJs)

    implementation(npm("firebase", "7.9.3"))
    implementation(npm("firebaseui", "4.4.0"))
}
