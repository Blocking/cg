package com.example.cg.template;

import com.example.cg.bean.TemplateResource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangxiaoyu
 * @date 2021/3/5
 */
public class QueryTemplateView extends AbstractTemplateView {

    public QueryTemplateView(TemplateProp sc) {
        super(sc);
    }

    @Override
    public List<TemplateResource> getTemplateResource() {
        List<TemplateResource> resources = new ArrayList<>();
        final TemplateResource re = TemplateResource.builder().template(ve.getTemplate("velocityTemplate/java/edit_query.vm"))
                .writePath(writePath + ctx.get("className") + "EditQuery.java").build();
        final TemplateResource re1 = TemplateResource.builder().template(ve.getTemplate("velocityTemplate/java/add_query.vm"))
                .writePath(writePath + ctx.get("className") + "AddQuery.java").build();
        final TemplateResource re2 = TemplateResource.builder().template(ve.getTemplate("velocityTemplate/java/page_query.vm"))
                .writePath(writePath + ctx.get("className") + "PageQuery.java").build();
        resources.add(re);
        resources.add(re1);
        resources.add(re2);
        return resources;
    }

}
