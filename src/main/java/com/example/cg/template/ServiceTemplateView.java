package com.example.cg.template;

import com.example.cg.bean.TemplateResource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangxiaoyu
 * @date 2021/3/5
 */
public class ServiceTemplateView extends AbstractTemplateView {

    public ServiceTemplateView(TemplateProp sc) {
        super(sc);
    }

    @Override
    public List<TemplateResource> getTemplateResource() {
        List<TemplateResource> resources = new ArrayList<>();
        final TemplateResource re = TemplateResource.builder().template(ve.getTemplate("velocityTemplate/java/service.vm"))
                .writePath(writePath + ctx.get("className") + "Service.java").build();
        final TemplateResource re1 = TemplateResource.builder().template(ve.getTemplate("velocityTemplate/java/serviceImpl.vm"))
                .writePath(writePath + ctx.get("className") + "ServiceImpl.java").build();
        resources.add(re);
        resources.add(re1);
        return resources;
    }
}
