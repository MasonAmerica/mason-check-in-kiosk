package com.bymason.build.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import org.gradle.kotlin.dsl.submit
import org.gradle.kotlin.dsl.support.serviceOf
import org.gradle.process.ExecOperations
import org.gradle.workers.WorkAction
import org.gradle.workers.WorkParameters
import org.gradle.workers.WorkerExecutor
import javax.inject.Inject

internal abstract class DeployWeb : DefaultTask() {
    @set:Option(option = "dev", description = "Skip sanity checks.")
    @get:Input
    var dev: Boolean = false
    @set:Option(option = "only", description = "See firebase help documentation")
    @get:Optional
    @get:Input
    var only: String? = null

    @TaskAction
    fun deploy() {
        project.serviceOf<WorkerExecutor>().noIsolation().submit(Deployer::class) {
            projectDir.set(project.projectDir)
            buildDir.set(project.buildDir)

            devArg.set(dev)
            onlyArgs.set(only)
        }
    }

    abstract class Deployer @Inject constructor(
            private val fileOps: FileSystemOperations,
            private val execOps: ExecOperations
    ) : WorkAction<Deployer.Params> {
        override fun execute() {
            fileOps.copy {
                from(parameters.buildDir.dir(
                        "js/packages/mason-check-in-kiosk-server/kotlin/mason-check-in-kiosk-server.js"))
                into(parameters.projectDir.dir("web/server/functions"))
                rename { "index.js" }
            }

            if (!parameters.devArg.get()) {
                execOps.exec {
                    commandLine("npm", "i", "-g", "firebase-tools")
                }
                execOps.exec {
                    commandLine("npm", "ci")
                    workingDir = parameters.projectDir.dir("web/server/functions").get().asFile
                }
            }
            execOps.exec {
                var command = "firebase deploy --non-interactive"
                parameters.onlyArgs.orNull?.let {
                    command += " --only ${parameters.onlyArgs.get()}"
                }

                commandLine("sh", "-c", "\$(npm bin -g)/$command")
                workingDir = parameters.projectDir.dir("web").get().asFile
            }
        }

        interface Params : WorkParameters {
            val projectDir: DirectoryProperty
            val buildDir: DirectoryProperty

            val devArg: Property<Boolean>
            val onlyArgs: Property<String>
        }
    }
}
