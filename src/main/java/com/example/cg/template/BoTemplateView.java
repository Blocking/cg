package com.example.cg.template;

import com.example.cg.bean.TemplateResource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangxiaoyu
 * @date 2021/3/5
 */
public class BoTemplateView extends AbstractTemplateView {

    public BoTemplateView(TemplateProp sc) {
        super(sc);
    }

    @Override
    public List<TemplateResource> getTemplateResource() {
        List<TemplateResource> resources = new ArrayList<>();
        final TemplateResource re = TemplateResource.builder().template(ve.getTemplate("bo.vm"))
                .writePath(writePath + ctx.get("className") + "BO.java").build();
//        final TemplateResource re1 = TemplateResource.builder().template(ve.getTemplate("bo_complex.vm"))
//                .writePath(writePath + ctx.get("className") + "ComplexBO.java").build();
        resources.add(re);
//        resources.add(re1);
        return resources;
    }

}
