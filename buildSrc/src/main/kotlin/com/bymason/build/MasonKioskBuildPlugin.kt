package com.bymason.build

import com.bymason.build.tasks.DeployWeb
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

class MasonKioskBuildPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        check(project === project.rootProject)

        project.tasks.register<DeployWeb>("deployWeb") {
            dependsOn(":web:site:browserProductionWebpack")
            dependsOn(":web:server:assemble")
            dependsOn(":kotlinNpmInstall")
        }
    }
}
