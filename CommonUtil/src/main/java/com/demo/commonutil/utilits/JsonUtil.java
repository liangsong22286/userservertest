package com.demo.commonutil.utilits;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String convertObj2String(Object object) {
        String s = null;
        try {
            s = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static <T> T convertString2Obj(String s, Class<T> clazz) {
        T t = null;
        try {
            t = objectMapper.readValue(s, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }
    public static <T> List<T> string2List(String s, Class<T> clazz) {
    	List<Object> list = convertString2Obj(s,List.class);
    	List<T> returnList = new ArrayList<T>();
        for (int i = 0; i < list.size(); i++) {
        	Object object = list.get(i);
        	try {
        		String convertObj2String = convertObj2String(object);
        		T element = convertString2Obj(convertObj2String,clazz);
        		returnList.add(element);
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
		}
        return returnList;
    }
}