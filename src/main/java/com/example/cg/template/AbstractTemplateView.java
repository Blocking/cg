package com.example.cg.template;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
 * @author zhangxiaoyu
 * @date 2021/3/5
 */
public abstract class AbstractTemplateView implements TemplateView {

    protected VelocityContext ctx ;

    protected VelocityEngine ve;


    protected String writePath;



    public AbstractTemplateView(TemplateProp templateProp) {
        this.ctx = templateProp.getVelocityContext();
        this.ve = templateProp.getVe();
        this.writePath = templateProp.getWritePath();
    }


}
