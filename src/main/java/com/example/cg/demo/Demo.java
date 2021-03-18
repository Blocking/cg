package com.example.cg.demo;

import com.example.cg.generate.SourceGenerate;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author zhangxiaoyu
 * @date 2021/3/5
 */
public class Demo {

    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException {
        final String modelClassName = "com.example.cg.bean.Example";
        SourceGenerate sourceGenerate = new SourceGenerate(modelClassName);
        sourceGenerate.setOut(SourceGenerate.Out.FILE);
        //文件输出路径 默认是 tmp 下
        sourceGenerate.setWritePath("/tmp");
        sourceGenerate.generateDTO();
        sourceGenerate.generateQuery();
        sourceGenerate.generateConverter();
        sourceGenerate.generateManager();
        sourceGenerate.generateService();
        sourceGenerate.generateController();



    }
/*    public static void main(String[] args) {
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            // Compiling the code
            int result = compiler.run(null, null, null,
                    "/tmp/ExampleDTO.java");


            System.out.println("result " + result);
//             Giving the path of the class directory where class file is generated..
            File classesDir = new File("/tmp/");
//             Load and instantiate compiled class.
            URLClassLoader classLoader;
            try {
                // Loading the class
                classLoader = URLClassLoader.newInstance(new URL[] { classesDir.toURI().toURL() });
                Class<?> cls = Class.forName("com.example.cg.dto.ExampleDTO", true, classLoader);
                System.out.println(cls.getSimpleName());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }*/

}
