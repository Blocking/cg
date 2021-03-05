package com.example.cg;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.List;

/**
 * 代码生成工具
 * @author zhangxiaoyu
 * @date 2021/3/5
 */
@Slf4j
public class SourceGenerate {

    VelocityContext ctx ;

     VelocityEngine ve;

    @Setter
    private String writePath = "/tmp/";

    final String date = "Date";

    final String bigDecimal = "BigDecimal";

    ModelClassDoc classDoc;

    @Setter
    private Out out = Out.CONSOLE;

    enum Out{
        FILE,
        CONSOLE
    }

    public SourceGenerate(){}

    public SourceGenerate(String modelClassName) {
        init(modelClassName);
    }

    public void init(String modelClassName){
        ctx = new VelocityContext();
        ve = initVe();
        Class c = null;
        try {
            c = Class.forName(modelClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //base
        final String packageName = c.getPackage().getName();
        final String parentPackageName = packageName.substring(0, packageName.lastIndexOf("."));
        ctx.put("date", LocalDate.now().toString());
        ctx.put("author", System.getProperty("user.name"));
        ctx.put("package", parentPackageName);
        final String simpleName = c.getSimpleName();
        ctx.put("className", simpleName);
        // 首字母小写
        final String initials = simpleName.substring(0, 1);
        final String other = simpleName.substring(1);
        ctx.put("lowerClassName", StringUtils.lowerCase(initials).concat(other));
        ctx.put("oldProject", false);

        ClassDocParse classDocParse = new ClassDocParse(modelClassName);
         classDoc = classDocParse.parse();
        ctx.put("classComments", classDoc.getComment());
        final List<FieldEntry> fields = classDoc.getFields();
        fields.forEach(field -> {
            if(date.equals(field.getFType())){
                ctx.put("hasDate", true);
            }
            if(bigDecimal.equals(field.getFType())){
                ctx.put("hasBigDecimal", true);
            }
        });
        ctx.put("fields", fields);
    }

    private void generateController() {
        System.out.println("///////////////////==CONTROLLER==//////////////////////////////");
        Template template = ve.getTemplate("velocityTemplate/java/controller.vm");
        merge(template,ctx, writePath +ctx.get("className")+"Controller.java");
        System.out.println("///////////////////==CONTROLLER==//////////////////////////////");
    }

    private void generateService() {
        System.out.println("///////////////////==Service==//////////////////////////////");
        Template template = ve.getTemplate("velocityTemplate/java/service.vm");
        merge(template,ctx, writePath +ctx.get("className")+"Service.java");

        Template template1 = ve.getTemplate("velocityTemplate/java/serviceImpl.vm");
        merge(template1,ctx, writePath +ctx.get("className")+"ServiceImpl.java");
        System.out.println("///////////////////==Service==//////////////////////////////");
    }

    private void generateConverter() {
        System.out.println("///////////////////==Converter==//////////////////////////////");
        Template template = ve.getTemplate("velocityTemplate/java/converter.vm");
        merge(template,ctx, writePath +ctx.get("className")+"Converter.java");
        System.out.println("///////////////////==Converter==//////////////////////////////");
    }
    private void generateManager() {
        System.out.println("///////////////////==Manager==//////////////////////////////");
        Template template = ve.getTemplate("velocityTemplate/java/manager.vm");
        merge(template,ctx, writePath +ctx.get("className")+"Manager.java");
        Template template1 = ve.getTemplate("velocityTemplate/java/managerImpl.vm");
        merge(template1,ctx, writePath +ctx.get("className")+"ManagerImpl.java");
        System.out.println("///////////////////==Manager==//////////////////////////////");
    }

    private void generateQuery() {
        System.out.println("///////////////////==Query==//////////////////////////////");
        Template template = ve.getTemplate("velocityTemplate/java/edit_query.vm");
        merge(template,ctx, writePath +ctx.get("className")+"EditQuery.java");

        Template template1 = ve.getTemplate("velocityTemplate/java/add_query.vm");
        merge(template1,ctx, writePath +ctx.get("className")+"AddQuery.java");

        Template template2 = ve.getTemplate("velocityTemplate/java/page_query.vm");
        merge(template2,ctx, writePath +ctx.get("className")+"PageQuery.java");
        System.out.println("///////////////////==Query==//////////////////////////////");
    }

    private void generateDTO() {
        System.out.println("///////////////////==DTO==//////////////////////////////");
        Template template = ve.getTemplate("velocityTemplate/java/dto.vm");
        merge(template,ctx, writePath +ctx.get("className")+"DTO.java");
        System.out.println("///////////////////==DTO==//////////////////////////////");
    }


    public VelocityEngine initVe() {
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        ve.init();
        return ve;
    }

    public void merge(Template template, VelocityContext ctx, String path) {

        switch (out){
            case FILE:
                try(PrintWriter writer =new PrintWriter(path)){
                    template.merge(ctx, writer);
                    writer.flush();
                } catch (ResourceNotFoundException e) {
                    e.printStackTrace();
                } catch (MethodInvocationException e) {
                    e.printStackTrace();
                } catch (ParseErrorException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case CONSOLE:
                try(StringWriter writer = new StringWriter()){
                    template.merge(ctx, writer);
                    System.out.println(writer);
                } catch (ResourceNotFoundException e) {
                    e.printStackTrace();
                } catch (MethodInvocationException e) {
                    e.printStackTrace();
                } catch (ParseErrorException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                throw new RuntimeException("未知的输出方式");
        }


    }


    public static void main(String[] args) {
        final String modelClassName = "com.example.cg.bean.Example";
        SourceGenerate sourceGenerate = new SourceGenerate(modelClassName);
        sourceGenerate.setOut(Out.FILE);
        //文件输出路径 默认是 tmp 下
        sourceGenerate.setWritePath("/tmp");
        sourceGenerate.generateDTO();
//        generateQuery();
//        generateConverter();
//        generateManager();
//        generateService();
//        generateController();
    }



}


