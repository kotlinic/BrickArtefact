package com.demo.plugin;

import com.android.build.api.transform.Format;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.gradle.internal.pipeline.TransformManager;
import com.android.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Set;


public class TestTransform extends Transform {

  //用于指明本Transform的名字，也是代表该Transform的task的名字
  @Override public String getName() {
    return "TestTransform";
  }

  //用于指明Transform的输入类型，可以作为输入过滤的手段。
  @Override public Set<QualifiedContent.ContentType> getInputTypes() {
    return TransformManager.CONTENT_CLASS;
  }

  //用于指明Transform的作用域
  @Override public Set<? super QualifiedContent.Scope> getScopes() {
    return TransformManager.SCOPE_FULL_PROJECT;
  }

  //是否增量编译
  @Override public boolean isIncremental() {
    return false;
  }

  @Override public void transform(TransformInvocation invocation) {
    System.out.println("TestTransform transform");
    for (TransformInput input : invocation.getInputs()) {
      //遍历jar文件 对jar不操作，但是要输出到out路径
      input.getJarInputs().parallelStream().forEach(jarInput -> {
        File src = jarInput.getFile();
        System.out.println("input.getJarInputs fielName:" + src.getName());
        File dst = invocation.getOutputProvider().getContentLocation(
            jarInput.getName(), jarInput.getContentTypes(), jarInput.getScopes(),
            Format.JAR);
        try {
          FileUtils.copyFile(src, dst);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      });
      //遍历文件，在遍历过程中
      input.getDirectoryInputs().parallelStream().forEach(directoryInput -> {
        File src = directoryInput.getFile();
        System.out.println("input.getDirectoryInputs fielName:" + src.getName());
        File dst = invocation.getOutputProvider().getContentLocation(
            directoryInput.getName(), directoryInput.getContentTypes(),
            directoryInput.getScopes(), Format.DIRECTORY);
        try {
          scanFilesAndInsertCode(src.getAbsolutePath());
          FileUtils.copyDirectory(src, dst);
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
      });
    }
  }

  private void scanFilesAndInsertCode(String path) throws Exception {
    /*ClassPool classPool = ClassPool.getDefault();
    classPool.appendClassPath(path);//将当前路径加入类池,不然找不到这个类
    CtClass ctClass = classPool.getCtClass("com.example.testplugin.PluginTestClass");
    if (ctClass == null) {
      return;
    }
    if (ctClass.isFrozen()) {
      ctClass.defrost();
    }
    CtMethod ctMethod = ctClass.getDeclaredMethod("init");

    String insetStr = "System.out.println(\"我是插入的代码\");";
    ctMethod.insertAfter(insetStr);//在方法末尾插入代码
    ctClass.writeFile(path);
    ctClass.detach();//释放*/
  }
}