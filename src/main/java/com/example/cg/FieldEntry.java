package com.example.cg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangxiaoyu
 * @date 2020/12/1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldEntry {
    /**
     * 参数名
     */
    private String fName;
    /**
     * 类型
     */
    private String fType;
    /**
     * 说明
     */
    private String fExplain;
}
