package com.example.cg.parse;

import com.example.cg.bean.FieldEntry;
import com.example.cg.bean.ModelClassDoc;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author zhangxiaoyu
 * @date 2021/3/3
 */
public class ClassDocParse {

    private String className;

    private Class<?> aClass;
    private String classString;

    public ClassDocParse(String className) {
        this.className = className;
        init();
    }

    public ModelClassDoc parse() {
        ModelClassDoc  modelClassDoc = new ModelClassDoc();
        final String simpleName = aClass.getSimpleName();
        modelClassDoc.setName(simpleName);
        modelClassDoc.setComment(getCommentAndClear(simpleName));
        final Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            final String fieldName = field.getName();
            final FieldEntry fieldEntry = new FieldEntry(fieldName, field.getType().getSimpleName(), getCommentAndClear(fieldName));
            modelClassDoc.add(fieldEntry);
        }
        return modelClassDoc;
    }

    private String getCommentAndClear(String simpleName) {
        final int fromIndex = classString.indexOf(simpleName);
        final int index = classString.lastIndexOf("/**",fromIndex);
        final int index1 = classString.lastIndexOf("*/",fromIndex);
        final String substring = classString.substring(index, index1 + 2);
        final String s = substring.replaceAll("\\*", "").replaceAll("/", "");
        final int index2 = s.indexOf("@");
        if(index2 > -1){
            return s.substring(1,index2).trim();
        }
        return s.trim();
    }

    private void init() {
        try {
            aClass = Class.forName(className);
            classString = getClassString(className);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    String getClassString(String fullyClassName) throws IOException, ClassNotFoundException {
        final Class<?> aClass = Class.forName(fullyClassName);
        String classFilePath = aClass.getResource("").getPath();
        final String replace = classFilePath.replace("target", "src")
                .replace("classes","main"+File.separator+"java");
        final String realPath = replace + aClass.getSimpleName() + ".java";
        final Path path = Paths.get(realPath);
        final byte[] bytes = Files.readAllBytes(path);
        final String s = new String(bytes);
        return s.substring(s.indexOf("/**"));
    }

    public static void main(String[] args) {
        ClassDocParse classDocParse = new ClassDocParse("com.example.cg.bean.Example");
        ModelClassDoc modelClassDoc = classDocParse.parse();
        System.out.println(modelClassDoc);
    }

}
