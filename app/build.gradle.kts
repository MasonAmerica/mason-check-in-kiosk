import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("mason-apps")
    id("io.fabric")

    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.android.extensions")

    id("androidx.navigation.safeargs.kotlin")
}
crashlytics.alwaysUpdateBuildId = isReleaseBuild

android {
    compileSdkVersion(Config.SdkVersions.compile)

    defaultConfig {
        minSdkVersion(Config.SdkVersions.min)
        targetSdkVersion(Config.SdkVersions.target)

        vectorDrawables.useSupportLibrary = true
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
                    file("proguard-rules.pro")
            ))
        }
    }

    buildFeatures {
        viewBinding = true
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
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

masonApps {
    appName = "mason-check-in-kiosk"
}

dependencies {
    implementation(Config.Libs.Kotlin.jvm)
    implementation(Config.Libs.Kotlin.coroutinesAndroid)
    implementation(Config.Libs.Kotlin.coroutinesTasks)

    implementation(Config.Libs.Jetpack.core)
    implementation(Config.Libs.Jetpack.appCompat)
    implementation(Config.Libs.Jetpack.fragment)
    implementation(Config.Libs.Jetpack.rvSelection)
    implementation(Config.Libs.Jetpack.constraint)
    implementation(Config.Libs.Jetpack.material)
    implementation(Config.Libs.Jetpack.common)
    implementation(Config.Libs.Jetpack.runtime)
    implementation(Config.Libs.Jetpack.extensions)
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
    implementation(Config.Libs.Misc.retrofitGson)
    implementation(Config.Libs.Misc.gson)
    implementation(Config.Libs.Misc.glide)
    implementation(Config.Libs.Misc.guava)

    implementation(Config.Libs.Misc.permissions)

    testImplementation(Config.Libs.Testing.junit)
    testImplementation(Config.Libs.Testing.truth)
    testImplementation(Config.Libs.Testing.espresso)
    testImplementation(Config.Libs.Testing.core)
    testImplementation(Config.Libs.Testing.arch)
    debugImplementation(Config.Libs.Testing.fragment)
    testImplementation(Config.Libs.Testing.robolectric)
    testImplementation(Config.Libs.Testing.mockito)
    testImplementation(Config.Libs.Testing.coroutines)
}

tasks.matching {
    it.name.contains("ReleaseUnitTest")
}.configureEach {
    enabled = false
}

tasks.withType<KotlinCompile>().matching {
    it.name.contains("UnitTest")
}.configureEach {
    kotlinOptions {
        freeCompilerArgs += "-Xno-call-assertions"
    }
}

apply(plugin = "com.google.gms.google-services")
