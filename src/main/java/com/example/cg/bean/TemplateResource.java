package com.example.cg.bean;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Delegate;
import org.apache.velocity.Template;

/**
 * @author zhangxiaoyu
 * @date 2021/3/5
 */
@Data
@Builder
public class TemplateResource {

    @Delegate
    private Template template;

    private String writePath;
}
