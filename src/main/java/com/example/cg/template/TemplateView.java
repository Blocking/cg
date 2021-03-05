package com.example.cg.template;

import com.example.cg.bean.TemplateResource;

import java.util.List;

/**
 * @author zhangxiaoyu
 * @date 2021/3/5
 */
public interface TemplateView {

    /**
     * 获取模版资源信息集合
     * @return 资源信息集合
     */
    List<TemplateResource> getTemplateResource();

}
