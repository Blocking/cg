package com.example.cg.template;

import com.example.cg.bean.TemplateResource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangxiaoyu
 * @date 2021/3/5
 */
public class DtoTemplateView extends AbstractTemplateView {

    public DtoTemplateView(TemplateProp sc) {
        super(sc);
    }

    @Override
    public List<TemplateResource> getTemplateResource() {
        List<TemplateResource> resources = new ArrayList<>();
        final TemplateResource re = TemplateResource.builder().template(ve.getTemplate("dto.vm"))
                .writePath(writePath + ctx.get("className") + "DTO.java").build();
        resources.add(re);
        return resources;
    }

}
