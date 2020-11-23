package com.yyh.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Clazz implements Serializable {
    private String id;
    private String deptName;    //学院
    private String majorName;   //专业名称
    private String clazzName;  //班级
}
