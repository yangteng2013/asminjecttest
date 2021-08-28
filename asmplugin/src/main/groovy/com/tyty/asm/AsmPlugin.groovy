package com.tyty.asm

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class AsmPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        println("Hello tyty ASM Plugin")

        def android = project.extensions.getByType(AppExtension)
        android.registerTransform(new AsmTransform(project))


    }
}