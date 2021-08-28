package com.tyty.asm

import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.codec.digest.DigestUtils
import org.gradle.api.Project

class AsmTransform extends Transform {
    Project mproject

    AsmTransform(Project project) {
        mproject = project
    }

    /**
     * 自定义任务TASK的名称
     * 为Transform定义一个名字，不过该名字最后生成的文件名，也是拼接上了flavor、buildType等等
     * @return
     */
    @Override
    String getName() {
        //transformClassesWithAsmTransformPluginForRelease
        return "AsmTransformPlugin"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    /**
     * 当前的 Transform 是否支持增量编译
     * @return
     */
    @Override
    boolean isIncremental() {
        return false
    }

    /**
     * 该方法就是实际转换时候调用的方法，
     * 3.0之前将input、outputProvider等传入，
     * 3.0开始直接将一个TransformInvocation对象
     *
     * @param transformInvocation
     * @throws TransformException* @throws InterruptedException* @throws IOException
     */
    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        transformInvocation.inputs.each {
            //存放第三方的 jar 包
            it.jarInputs.each {
                JarInput jarInput ->
                    String destname = jarInput.file.name
                    String absolutePath = jarInput.file.absolutePath
                    println("jarInput destName:${destname}")
                    println("jarInput absolutePath:${absolutePath}")
                    DigestUtils.get

            }
            it.directoryInputs.each {

            }


        }

    }


}
