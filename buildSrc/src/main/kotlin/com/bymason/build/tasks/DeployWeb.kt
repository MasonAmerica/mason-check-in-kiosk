package com.bymason.build.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.file.ProjectLayout
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.submit
import org.gradle.kotlin.dsl.support.serviceOf
import org.gradle.process.ExecOperations
import org.gradle.workers.WorkAction
import org.gradle.workers.WorkParameters
import org.gradle.workers.WorkerExecutor
import javax.inject.Inject

abstract class DeployWeb : DefaultTask() {
    @TaskAction
    fun deploy() {
        project.serviceOf<WorkerExecutor>().noIsolation().submit(Deployer::class) {}
    }

    abstract class Deployer @Inject constructor(
            private val layout: ProjectLayout,
            private val fileOps: FileSystemOperations,
            private val execOps: ExecOperations
    ) : WorkAction<WorkParameters.None> {
        override fun execute() {
            fileOps.copy {
                from(layout.buildDirectory.dir(
                        "js/packages/mason-check-in-kiosk-server/kotlin/mason-check-in-kiosk-server.js"))
                into(layout.projectDirectory.dir("web/server/functions"))
                rename { "index.js" }
            }
            fileOps.copy {
                from(layout.projectDirectory.dir("web/site/build/processedResources/Js/main"))
                into(layout.projectDirectory.dir("web/site/build/distributions"))
            }

            execOps.exec {
                commandLine("npm", "i", "-g", "firebase-tools")
            }
            execOps.exec {
                commandLine("npm", "ci")
                workingDir = layout.projectDirectory.dir("web/server/functions").asFile
            }
            execOps.exec {
                commandLine("sh", "-c", "\$(npm bin -g)/firebase deploy --non-interactive")
                workingDir = layout.projectDirectory.dir("web").asFile
            }
        }
    }
}
