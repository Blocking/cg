package com.example.cg.generate;

import com.example.cg.bean.FieldEntry;
import com.example.cg.bean.ModelClassDoc;
import com.example.cg.parse.ClassDocParse;
import com.example.cg.template.*;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    private VelocityContext ctx ;

    private VelocityEngine ve;

    @Setter
    private String writePath = "/tmp/";

    final static String date = "Date";

    final static String bigDecimal = "BigDecimal";

    private ModelClassDoc classDoc;

    private TemplateProp prop;

    @Setter
    private Out out = Out.CONSOLE;

    public enum Out{
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
        prop = new TemplateProp();
        prop.setVe(ve);
        prop.setWritePath(writePath);
        prop.setVelocityContext(ctx);
    }

    public VelocityEngine initVe() {
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        ve.init();
        return ve;
    }

    public void generateController() {
        ControllerTemplateView controller  =  new ControllerTemplateView(prop);
        merge(controller);
    }

    public void generateService() {
        ServiceTemplateView service = new ServiceTemplateView(prop);
        merge(service);
    }

    public void generateConverter() {
        ConvertorTemplateView convertor = new ConvertorTemplateView(prop);
        merge(convertor);
    }
    public void generateManager() {
        ManagerTemplateView manager = new ManagerTemplateView(prop);
        merge(manager);
    }

    public void generateQuery() {
        QueryTemplateView query = new QueryTemplateView(prop);
        merge(query);
    }

    public void generateDTO() {
        DtoTemplateView view = new DtoTemplateView(prop);
        merge(view);
    }

    protected void merge(TemplateView templateView) {
        templateView.getTemplateResource().forEach(template -> {
            switch (out){
                case FILE:
                    try(PrintWriter writer =new PrintWriter(template.getWritePath())){
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
        });

    }


}


