package com.example.cg.parse;

import com.example.cg.bean.FieldEntry;
import com.example.cg.bean.ModelDoc;

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
public class ClassDocParser implements Parser {

    private String className;

    private Class<?> aClass;
    private String classString;

    private String packageName;

    public ClassDocParser(String className) {
        this.className = className;
        init();
    }

    @Override
    public ModelDoc parse() {
        ModelDoc modelDoc = new ModelDoc();
        final String simpleName = aClass.getSimpleName();
        modelDoc.setName(simpleName);
        modelDoc.setComment(getCommentAndClear(simpleName));
        modelDoc.setPackageName(getPackageName());
        final Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            final String fieldName = field.getName();
            final FieldEntry fieldEntry = new FieldEntry(fieldName, field.getType().getSimpleName(), getCommentAndClear(fieldName));
            modelDoc.add(fieldEntry);
        }
        return modelDoc;
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
        ClassDocParser classDocParse = new ClassDocParser("com.example.cg.bean.Example");
        ModelDoc modelDoc = classDocParse.parse();
        System.out.println(modelDoc);
    }

    public String getPackageName() {
        final String packageName0 = aClass.getPackage().getName();
        return packageName0.substring(0, packageName0.lastIndexOf("."));
    }
}
