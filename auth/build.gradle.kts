plugins {
    id("com.android.library")
}

android {
    compileSdkVersion(Config.SdkVersions.compile)

    defaultConfig {
        minSdkVersion(Config.SdkVersions.min)
        targetSdkVersion(Config.SdkVersions.target)
    }

    buildTypes {
        named("release") {
            postprocessing {
                consumerProguardFile("auth.pro")
            }
        }
    }

    lintOptions {
        baseline(file("lint-baseline.xml"))
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(Config.Libs.Firebase.authInterop)
    implementation(Config.Libs.Firebase.identityToolkit)
    implementation(Config.Libs.Firebase.jwt)
}
