import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("com.supercilex.gradle.versions") version "0.4.0"
    id("org.gradle.test-retry") version "1.1.3"
    id("io.fabric")
    id("shot")

    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.android.extensions")
    id("org.jetbrains.kotlin.plugin.serialization")

    id("androidx.navigation.safeargs.kotlin")
}
crashlytics.alwaysUpdateBuildId = isReleaseBuild

android {
    compileSdkVersion(Config.SdkVersions.compile)

    defaultConfig {
        minSdkVersion(Config.SdkVersions.min)
        targetSdkVersion(Config.SdkVersions.target)
        base.archivesBaseName = "mason-check-in-kiosk"

        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "support.bymason.kiosk.checkin.helpers.ScreenshotTestRunner"
    }

    signingConfigs {
        register("release") {
            val keystoreProperties = Properties()
            val keystorePath: String? = System.getenv("KEYSTORE_PATH")
            val propsFile = if (keystorePath.isNullOrBlank()) {
                rootProject.file("ci-dummies/keystore.properties")
            } else {
                File(keystorePath)
            }

            keystoreProperties.load(FileInputStream(propsFile))
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = File(propsFile.parentFile, keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }
    }

    buildTypes {
        named("debug") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-DEBUG"
        }

        named("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            setProguardFiles(listOf(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    file("app.pro")
            ))
        }
    }

    lintOptions {
        baseline(file("lint-baseline.xml"))
    }

    buildFeatures {
        viewBinding = true
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
        animationsDisabled = true
    }

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

androidExtensions {
    features = setOf("parcelize")
}

dependencies {
    implementation(Config.Libs.Kotlin.jvm)
    implementation(Config.Libs.Kotlin.coroutinesAndroid)
    implementation(Config.Libs.Kotlin.coroutinesTasks)
    implementation(Config.Libs.Kotlin.serialization)

    implementation(Config.Libs.Jetpack.core)
    implementation(Config.Libs.Jetpack.appCompat)
    implementation(Config.Libs.Jetpack.fragment)
    implementation(Config.Libs.Jetpack.rv)
    implementation(Config.Libs.Jetpack.constraint)
    implementation(Config.Libs.Jetpack.material)
    implementation(Config.Libs.Jetpack.common)
    implementation(Config.Libs.Jetpack.runtime)
    implementation(Config.Libs.Jetpack.liveData)
    implementation(Config.Libs.Jetpack.viewModel)
    implementation(Config.Libs.Jetpack.navFragments)
    implementation(Config.Libs.Jetpack.navUi)
    implementation(Config.Libs.Jetpack.webkit)

    implementation(Config.Libs.Firebase.analytics)
    implementation(project(":auth"))
    implementation(Config.Libs.Firebase.functions)
    implementation(Config.Libs.Firebase.crashlytics)
    debugImplementation(Config.Libs.Misc.leakCanary)

    implementation(Config.Libs.Misc.retrofit)
    implementation(Config.Libs.Misc.glide)
    implementation(Config.Libs.Misc.guava)

    testImplementation(Config.Libs.Testing.junit)
    testImplementation(Config.Libs.Testing.truth)
    testImplementation(Config.Libs.Testing.espresso)
    testImplementation(Config.Libs.Testing.espressoWeb)
    testImplementation(Config.Libs.Testing.espressoContrib)
    testImplementation(Config.Libs.Testing.core)
    testImplementation(Config.Libs.Testing.arch)
    debugImplementation(Config.Libs.Testing.fragment)
    testImplementation(Config.Libs.Testing.robolectric)
    testImplementation(Config.Libs.Testing.mockito)
    testImplementation(Config.Libs.Testing.coroutines)

    androidTestImplementation(Config.Libs.Testing.runner)
    androidTestImplementation(Config.Libs.Testing.uiautomator)
    androidTestImplementation(Config.Libs.Testing.junit)
    androidTestImplementation(Config.Libs.Testing.mockitoAndroid)
    androidTestImplementation(Config.Libs.Testing.espresso)
    androidTestImplementation(Config.Libs.Testing.core)
    androidTestImplementation(Config.Libs.Testing.arch)
    androidTestImplementation(Config.Libs.Testing.coroutines)
}

shot {
    appId = "support.bymason.kiosk.checkin.debug"
}

tasks.matching {
    it.name.contains("ReleaseUnitTest")
}.configureEach {
    enabled = false
}

tasks.withType<Test>().configureEach {
    retry {
        maxRetries.set(3)
        maxFailures.set(20)
    }
}

tasks.withType<KotlinCompile>().matching {
    it.name.contains("UnitTest") || it.name.contains("AndroidTest")
}.configureEach {
    kotlinOptions {
        freeCompilerArgs += "-Xno-call-assertions"
    }
}

apply(plugin = "com.google.gms.google-services")
