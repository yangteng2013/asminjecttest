package com.tyty.cusplugin

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

class CustomPluginB implements Plugin<Project>{


    @Override
    void apply(Project project) {
        println("Hello this is CustomePluginB for U")

        def extension = project.extensions.create("customB",CustomePluginBExt)

        project.task('cbcb'){
            doLast {
                println("接手外部的参数：${extension.extensionArgs}")
            }
        }
    }
}