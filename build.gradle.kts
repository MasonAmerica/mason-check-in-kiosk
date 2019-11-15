import org.apache.commons.io.output.TeeOutputStream

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
}

buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
}

allprojects {
    Config.run { repositories.deps() }

    configureKtlint()
}

tasks.wrapper {
    distributionType = Wrapper.DistributionType.ALL
}

tasks.register<Delete>("clean") {
    delete("build")
}

fun Project.configureKtlint() {
    val ktlintConfig = configurations.create("ktlint")
    val ktlint = tasks.register<JavaExec>("ktlint") {
        if (!file("src").exists()) {
            isEnabled = false
            return@register
        }

        main = "com.pinterest.ktlint.Main"
        classpath = ktlintConfig
        args = listOf("src/**/*.kt")

        val output = File(buildDir, "reports/ktlint/log.txt")
        inputs.dir(fileTree("src").apply { include("**/*.kt") })
        outputs.file(output)
        outputs.cacheIf { true }

        doFirst { standardOutput = TeeOutputStream(standardOutput, output.outputStream()) }
    }
    tasks.matching { it.name == "check" }.configureEach { dependsOn(ktlint) }

    dependencies { "ktlint"(Config.Plugins.ktlint) }
}
