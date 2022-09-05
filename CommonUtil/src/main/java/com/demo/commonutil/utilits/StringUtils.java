package com.demo.commonutil.utilits;

public class StringUtils {

	public static boolean isNullStr(String str) {
    	if(str!=null && !str.toString().trim().equals("")){
    		return false;
    	}
		return true;
	}

}
