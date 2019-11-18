import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.version
import org.gradle.plugin.use.PluginDependenciesSpec

object Config {
    private const val kotlinVersion = "1.3.50"
    private const val navVersion = "2.2.0-rc02"

    fun RepositoryHandler.deps() {
        google().content {
            includeGroup("com.android")
            includeGroupByRegex("com\\.android\\..*")
            includeGroupByRegex("com\\.google\\..*")
            includeGroupByRegex("androidx\\..*")

            includeGroup("com.crashlytics.sdk.android")
            includeGroup("io.fabric.sdk.android")
        }

        maven("https://dl.bintray.com/kotlin/kotlin-dev/").content {
            includeGroup("org.jetbrains.kotlin")
        }

        maven("https://maven.fabric.io/public").content {
            includeGroup("io.fabric.tools")
        }

        jcenter()
    }

    object SdkVersions {
        const val compile = 29
        const val target = 29
        const val min = 23
    }

    object Plugins {
        const val android = "com.android.tools.build:gradle:4.0.0-alpha03"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
        const val mason = "com.bymason.build:mason-apps:1.0.2"
        const val navArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion"
        const val google = "com.google.gms:google-services:4.3.3"
        const val fabric = "io.fabric.tools:gradle:1.31.2"

        const val ktlint = "com.pinterest:ktlint:0.33.0"
        val PluginDependenciesSpec.versionChecker
            get() = id("com.github.ben-manes.versions") version "0.27.0"
    }

    object Libs {
        object Kotlin {
            private const val coroutinesVersion = "1.3.2"
            private const val ankoVersion = "0.10.8"

            const val jvm = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
            const val coroutinesAndroid =
                    "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"

            const val common = "org.jetbrains.anko:anko-common:$ankoVersion"
            const val appCompat = "org.jetbrains.anko:anko-appcompat-v7-commons:$ankoVersion"
            const val design = "org.jetbrains.anko:anko-design:$ankoVersion"
        }

        object Jetpack {
            private const val lifecycleVersion = "2.2.0-rc02"

            const val core = "androidx.core:core-ktx:1.2.0-beta02"
            const val appCompat = "androidx.appcompat:appcompat:1.1.0"
            const val fragment = "androidx.fragment:fragment-ktx:1.2.0-rc02"
            const val rvSelection = "androidx.recyclerview:recyclerview-selection:1.1.0-alpha06"
            const val constraint = "androidx.constraintlayout:constraintlayout:1.1.3"

            const val material = "com.google.android.material:material:1.1.0-beta02"

            const val common = "androidx.lifecycle:lifecycle-common-java8:$lifecycleVersion"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:$lifecycleVersion"
            const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"

            const val navFragments = "androidx.navigation:navigation-fragment-ktx:$navVersion"
            const val navUi = "androidx.navigation:navigation-ui-ktx:$navVersion"
        }

        object Firebase {
            const val analytics = "com.google.firebase:firebase-analytics:17.2.1"
            const val crashlytics = "com.crashlytics.sdk.android:crashlytics:2.10.1"
        }

        object Misc {
            private const val retrofitVersion = "2.6.2"

            const val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.0-beta-3"
            const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
            const val retrofitGson = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
            const val gson = "com.google.code.gson:gson:2.8.6"
            const val glide = "com.github.bumptech.glide:glide:4.10.0"

            const val permissions = "pub.devrel:easypermissions:3.0.0"
            const val gauth = "com.google.auth:google-auth-library-oauth2-http:0.18.0"
            const val gsuiteSdk =
                    "com.google.apis:google-api-services-admin-directory:directory_v1-rev20190806-1.30.3"
        }
    }
}
