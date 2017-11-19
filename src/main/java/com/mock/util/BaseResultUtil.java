package com.mock.util;

import com.mock.dto.BaseResult;

import java.io.Serializable;
import java.lang.reflect.Field;

public class BaseResultUtil {
    public static Object toEntity(BaseResult baseResult,Class<?> clazz){

        baseResult.getData();
        Field[] fields= clazz.getFields();
        for (Field field:fields){
            String jsonKey=field.getName();
            
        }
        return null;
    }
}
