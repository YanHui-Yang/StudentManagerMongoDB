package com.yyh.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//负责存储模糊查询的关键字
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FuzzyQuery {
    private String deptName;
    private String majorName;
    private String clazzName;
    private String stuNumber;
    private String stuName;
    private String courseName;
}
