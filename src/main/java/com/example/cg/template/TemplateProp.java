package com.example.cg.template;

import lombok.Data;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
 * @author zhangxiaoyu
 * @date 2021/3/5
 */
@Data
public class TemplateProp {

    private VelocityContext velocityContext;

    private VelocityEngine ve;

    private String writePath;

}
