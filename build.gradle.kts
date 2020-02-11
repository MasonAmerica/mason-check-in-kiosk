import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

buildscript {
    repositories.deps()

    dependencies {
        classpath(Config.Plugins.android)
        classpath(Config.Plugins.kotlin)
        classpath(Config.Plugins.kotlinSerialization)
        classpath(Config.Plugins.mason)
        classpath(Config.Plugins.navArgs)
        classpath(Config.Plugins.google)
        classpath(Config.Plugins.fabric)
    }
}

plugins {
    Config.Plugins.run { versionChecker }
    `lifecycle-base`
    id("com.bymason.build.kiosk")
}

buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
}

allprojects {
    repositories.deps()

    afterEvaluate {
        convention.findByType<KotlinProjectExtension>()?.apply {
            sourceSets.configureEach {
                languageSettings.progressiveMode = true
                languageSettings.enableLanguageFeature("NewInference")
                languageSettings.useExperimentalAnnotation(
                        "kotlinx.coroutines.ExperimentalCoroutinesApi")
            }
        }
    }

    tasks.withType<Test> {
        testLogging {
            events("passed", "failed", "skipped")
            showStandardStreams = true
            setExceptionFormat("full")
        }
    }
}

tasks.wrapper {
    distributionType = Wrapper.DistributionType.ALL
}
