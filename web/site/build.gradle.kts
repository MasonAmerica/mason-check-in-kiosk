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

    implementation(npm("firebase", "7.15.1"))
    implementation(npm("firebaseui", "4.5.1"))
}
