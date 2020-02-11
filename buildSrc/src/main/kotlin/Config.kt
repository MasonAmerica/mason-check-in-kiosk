import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.version
import org.gradle.plugin.use.PluginDependenciesSpec

fun RepositoryHandler.deps() {
    google().content {
        includeGroup("com.android")
        includeGroupByRegex("com\\.android\\..*")
        includeGroupByRegex("com\\.google\\..*")
        includeGroupByRegex("androidx\\..*")

        includeGroup("com.crashlytics.sdk.android")
        includeGroup("io.fabric.sdk.android")
    }

    maven("https://maven.fabric.io/public").content {
        includeGroup("io.fabric.tools")
    }

    jcenter()
}

object Config {
    private const val kotlinVersion = "1.3.61"
    private const val navVersion = "2.2.0"
    private const val coroutinesVersion = "1.3.3"

    object SdkVersions {
        const val compile = 29
        const val target = 29
        const val min = 23
    }

    object Plugins {
        const val android = "com.android.tools.build:gradle:4.0.0-alpha09"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
        const val kotlinSerialization = "org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion"
        const val mason = "com.bymason.build:mason-apps:1.0.4"
        const val navArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion"
        const val google = "com.google.gms:google-services:4.3.3"
        const val fabric = "io.fabric.tools:gradle:1.31.2"

        val PluginDependenciesSpec.versionChecker
            get() = id("com.github.ben-manes.versions") version "0.27.0"
    }

    object Libs {
        object Kotlin {
            const val jvm = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
            const val js = "org.jetbrains.kotlin:kotlin-stdlib-js:$kotlinVersion"

            const val coroutinesAndroid =
                    "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"
            const val coroutinesTasks =
                    "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$coroutinesVersion"
            const val coroutinesJs =
                    "org.jetbrains.kotlinx:kotlinx-coroutines-core-js:$coroutinesVersion"

            const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.14.0"
        }

        object Jetpack {
            private const val lifecycleVersion = "2.2.0"

            const val core = "androidx.core:core-ktx:1.2.0-rc01"
            const val appCompat = "androidx.appcompat:appcompat:1.2.0-alpha01"
            const val fragment = "androidx.fragment:fragment-ktx:1.2.0"
            const val rv = "androidx.recyclerview:recyclerview:1.2.0-alpha01"
            const val constraint = "androidx.constraintlayout:constraintlayout:2.0.0-beta4"

            const val material = "com.google.android.material:material:1.2.0-alpha04"

            const val common = "androidx.lifecycle:lifecycle-common-java8:$lifecycleVersion"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
            const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"

            const val navFragments = "androidx.navigation:navigation-fragment-ktx:$navVersion"
            const val navUi = "androidx.navigation:navigation-ui-ktx:$navVersion"

            const val webkit = "androidx.webkit:webkit:1.2.0-alpha01"
        }

        object Firebase {
            const val analytics = "com.google.firebase:firebase-analytics:17.2.2"
            const val functions = "com.google.firebase:firebase-functions-ktx:19.0.1"
            const val crashlytics = "com.crashlytics.sdk.android:crashlytics:2.10.1"

            const val identityToolkit =
                    "com.google.apis:google-api-services-identitytoolkit:v3-rev20180723-1.30.1"
            const val jwt = "com.auth0.android:jwtdecode:1.4.0"
            const val authInterop = "com.google.firebase:firebase-auth-interop:19.0.0"
        }

        object Misc {
            private const val retrofitVersion = "2.7.1"

            const val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.1"
            const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
            const val glide = "com.github.bumptech.glide:glide:4.11.0"
            const val guava = "com.google.guava:guava:28.2-android"
        }

        object Testing {
            const val junit = "androidx.test.ext:junit-ktx:1.1.2-alpha03"
            const val truth = "androidx.test.ext:truth:1.3.0-alpha03"
            const val espresso = "androidx.test.espresso:espresso-core:3.3.0-alpha03"
            const val espressoWeb = "androidx.test.espresso:espresso-web:3.3.0-alpha03"
            const val espressoContrib = "androidx.test.espresso:espresso-contrib:3.3.0-alpha03"
            const val core = "androidx.test:core-ktx:1.3.0-alpha03"
            const val arch = "androidx.arch.core:core-testing:2.1.0"
            const val fragment = "androidx.fragment:fragment-testing:1.2.0"
            const val robolectric = "org.robolectric:robolectric:4.3.1"
            const val mockito = "org.mockito:mockito-core:3.2.4"
            const val coroutines =
                    "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion"
        }
    }
}
