package com.example.cg.demo;

import com.example.cg.generate.SourceGenerate;

/**
 * @author zhangxiaoyu
 * @date 2021/3/5
 */
public class Demo {

    public static void main(String[] args) {
        final String modelClassName = "com.ikongjian.dim.model.PaintWallpaperGoodsRelation";
        SourceGenerate sourceGenerate = new SourceGenerate(modelClassName);
        sourceGenerate.setOut(SourceGenerate.Out.FILE);
        //文件输出路径 默认是 tmp 下
        sourceGenerate.setWritePath("/tmp");
        sourceGenerate.generateDTO();
        sourceGenerate.generateQuery();
        sourceGenerate.generateConverter();
        sourceGenerate.generateManager();
        sourceGenerate.generateService();
        sourceGenerate.generateController();
    }

}
