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

        implementation(npm("firebase-admin", "8.8.0"))
        implementation(npm("firebase-functions", "3.3.0"))
        implementation(npm("@google-cloud/firestore", "2.6.0"))
        implementation(npm("googleapis", "45.0.0"))
        implementation(npm("superagent", "5.1.1"))
        implementation(npm("docusign-esign", "5.1.0"))
        implementation(npm("md5", "2.2.1"))
    }
}

apply(from = "tmp-dukat-crash-hack.gradle")
