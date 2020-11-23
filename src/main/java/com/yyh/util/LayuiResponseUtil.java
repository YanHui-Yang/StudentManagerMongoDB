package com.yyh.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LayuiResponseUtil extends HashMap<String, Object> {

    private Integer code = 0;
    private String msg = "";
    private Long count;
    private Object data;

    public static LayuiResponseUtil data(Long count, Object data) {
        LayuiResponseUtil responseUtil = new LayuiResponseUtil();
        responseUtil.put("code", 0);
        responseUtil.put("msg", "");
        responseUtil.put("count", count);
        responseUtil.put("data", data);
        return responseUtil;
    }
}
