package com.tyty.asm

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import com.tyty.asmplugin.ModifyUtils
import groovy.io.FileType
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.IOUtils
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
                    // 重命名输出文件（同目录copyFile会冲突）
                    def md5Name = DigestUtils.md5Hex(absolutePath)
                    if (destname.endsWith(".jar")) {
                        destname = destname.substring(0, destname.length() - 4)
                    }
                    println("jarInput2 destName:${destname}")
                    def modifyJar = null
//                    ModifyUtils.modifyJar(jarInput.file,transformInvocation.getContext().getTemporaryDir())
                    if (modifyJar == null) {
                        modifyJar = jarInput.file
                    }
                    //获取输出的jar
                    File destJar = transformInvocation.outputProvider.getContentLocation(destname + "_" + md5Name, jarInput.contentTypes, jarInput.scopes, Format.JAR)

                    FileUtils.copyFile(modifyJar, destJar)
                    println("destJar.absolutePath:" + destJar.absolutePath)
            }

            it.directoryInputs.each {
                DirectoryInput directoryInput ->
                    def dest = transformInvocation.getOutputProvider().getContentLocation(directoryInput.name,
                            directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                    println("directory output dest:${dest.absolutePath}")
                    File dir = directoryInput.file
                    HashMap<String, File> modufyMap = new HashMap<>()
                    if (dir) {
                        dir.traverse(type: FileType.FILES, nameFilter: ~/.*\.classes/) {
                            File classFile ->
                                if (!classFile.name.endsWith("R.class")
                                        && !classFile.name.endsWith("BuildCOnfig.class")
                                        && !classFile.name.contains("R\$")
                                ) {
                                    File modified = modifyClassFile(dir, classFile, transformInvocation.context.getTemporaryDir())


                                }


                        }
                    }

            }


        }

    }

    private static File modifyClassFile(File dir, File classFile, File tempDir) {
        File modified = null
        println("dir.absolutePath+File.separator:${dir.absolutePath + File.separator}")
        String className = path2ClassName(classFile.absolutePath.replace(dir.absolutePath + File.separator, ""))
        println("className:${className}")

        byte[] sourceClassBytes = IOUtils.toByteArray(new FileInputStream(classFile))
        byte[] modifiedClassBytes = ModifyUtils.modifyClasses(sourceClassBytes)
        if (modifiedClassBytes) {
            modified = new File(tempDir, className.replace(".", "") + ".class")
            if (modified.exists()) {
               modified.delete()
            }
            modified.createNewFile()
            new FileOutputStream(modified).write(modifiedClassBytes)
        }

        return modified

    }

    private static String path2ClassName(String pathName) {
        pathName.replace(File.separator, ".").replace(".class", "")
    }


}
