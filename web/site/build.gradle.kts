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
        implementation(kotlin("stdlib-js"))

        implementation(npm("firebase", "7.5.0"))
        implementation(npm("firebaseui", "4.3.0"))
    }
}

apply(from = "tmp-dukat-crash-hack.gradle")
