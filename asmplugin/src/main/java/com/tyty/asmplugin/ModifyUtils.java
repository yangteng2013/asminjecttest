package com.tyty.asmplugin;


import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

/***
 * 修改jar包中的class文件
 */
public class ModifyUtils {


    /**
     * @param jarFile
     * @param tempDir
     * @return
     * @throws IOException
     */
    public static File modifyJar(File jarFile, File tempDir) throws IOException {
        JarFile curFile = new JarFile(jarFile);

        //创建输出的文件，用于存放修改后的数据
        String hexName = DigestUtils.md5Hex(jarFile.getAbsolutePath().substring(0, 8));
        File outputJar = new File(tempDir, hexName + jarFile.getName());
        JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(outputJar));

        //遍历jar包 需要修改的文件,然后保存到jarOutputStream中
        Enumeration enumeration = curFile.entries();
        while (enumeration.hasMoreElements()) {
            //获取jar中的所有元素，其中包括class文件
            JarEntry jarEntry = (JarEntry) enumeration.nextElement();
            //读取元素的字节流
            InputStream inputStream = curFile.getInputStream(jarEntry);
            String entryName = jarEntry.getName();
            System.out.println("entryName:"+entryName);
            ZipEntry zipEntry = new ZipEntry(entryName);
            jarOutputStream.putNextEntry(zipEntry);

            byte[] sourceClassBytes = IOUtils.toByteArray(inputStream);
            byte[] modifiedClassBytes = null;
            if (entryName.endsWith(".class") && !entryName.endsWith("R.class") && !entryName.endsWith("BuildConfig.class")) {
                modifiedClassBytes = modifyClasses(sourceClassBytes);
            }
            if (modifiedClassBytes == null) {
                jarOutputStream.write(sourceClassBytes);
            } else {
                jarOutputStream.write(modifiedClassBytes);
            }
            jarOutputStream.closeEntry();
        }
        jarOutputStream.close();
        curFile.close();
        return outputJar;
    }

    static byte[] modifyClasses(byte[] sourceClassBytes) {
        byte[] classBytesCodes = null;

        classBytesCodes = modifyClass(sourceClassBytes);
        if (classBytesCodes == null) {
            classBytesCodes = sourceClassBytes;
        }

        return classBytesCodes;
    }

    private static byte[] modifyClass(byte[] sourceClassBytes) {
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassVisitor classVisitor = new CustomClassVisitor(classWriter);
        ClassReader classReader = new ClassReader(sourceClassBytes);
        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);
        return classWriter.toByteArray();
    }

}
