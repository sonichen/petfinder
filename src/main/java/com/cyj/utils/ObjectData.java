package com.cyj.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjectData {
    private Integer code;
    private String msg;
    private Object data;
    public static ObjectData ok(Object object) {
        return new ObjectData(Constants.OK_CODE, Constants.OK_MSG, object);
    }
    public static ObjectData fail() {
        return new ObjectData(Constants.FAIL_CODE, Constants.FAIL_MSG, null);
    }
    public static ObjectData fail(String msg) {
        return new ObjectData(Constants.FAIL_CODE, msg, null);
    }
}
