package com.example.cg.bean;


import lombok.Data;
import lombok.experimental.Delegate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangxiaoyu
 * @date 2021/3/3
 */
@Data
public class ModelDoc {

    private String name;

    private String comment;

    private String packageName;

    @Delegate
    List<FieldEntry> fields = new ArrayList<>(16);

}
