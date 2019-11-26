buildscript {
    Config.run { repositories.deps() }

    dependencies {
        classpath(Config.Plugins.android)
        classpath(Config.Plugins.kotlin)
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
    Config.run { repositories.deps() }
}

tasks.wrapper {
    distributionType = Wrapper.DistributionType.ALL
}
