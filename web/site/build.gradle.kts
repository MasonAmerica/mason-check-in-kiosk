plugins {
    id("org.jetbrains.kotlin.js")
}

kotlin {
    target {
        useCommonJs()
        nodejs()
        browser()
    }

    sourceSets["main"].dependencies {
        implementation(Config.Libs.Kotlin.js)
        implementation(Config.Libs.Kotlin.coroutinesJs)

        implementation(npm("kotlinx-coroutines-core", "1.3.2"))
        implementation(npm("firebase", "7.9.3"))
        implementation(npm("firebaseui", "4.4.0"))
    }
}

apply(from = "tmp-dukat-crash-hack.gradle")
