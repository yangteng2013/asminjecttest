package com.tyty.cusplugin


import org.gradle.api.Plugin
import org.gradle.api.Project

class CustomPluginB implements Plugin<Project> {


    @Override
    void apply(Project project) {
        println("Hello this is CustomePluginB for U")

        def extension = project.extensions.create("customB", CustomePluginBExt)
        /**
         * 建议接收外部的参数 是在afterEvaluate
         */
        project.afterEvaluate {
            project.task('cbcb') {
                doLast {
                    println("接收外部的参数：${extension.extensionArgs}")
                }
            }
        }
    }
}