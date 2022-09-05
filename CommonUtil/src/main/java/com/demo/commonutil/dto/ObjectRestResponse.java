package com.demo.commonutil.dto;

import com.demo.commonutil.utilits.JsonUtil;

/**
 * 
 * @author Administrator
 *
 * @param <T>
 */
public class ObjectRestResponse<T> extends BaseResponse {
    private T data;
    public ObjectRestResponse(){
    	
    }
    public ObjectRestResponse(int code, boolean success, String msg) {
    	super(success,code,msg);
	}
	public static ObjectRestResponse error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static ObjectRestResponse error(String msg) {
        return error(500, msg);
    }

    public static ObjectRestResponse error(int code, String msg) {
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        return objectRestResponse.code(code).success(false).msg(msg);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    @Override
    public String toString()
    {
       return JsonUtil.convertObj2String(this);
    }



    public ObjectRestResponse<T> code(int code) {
    	this.setCode(code);
    	return this;
    }
    public ObjectRestResponse<T> success(boolean success) {
        this.setSuccess(success);
        return this;
    }

    public ObjectRestResponse<T> msg(String msg) {
        this.setMsg(msg);
        return this;
    }
    public ObjectRestResponse<T> data(T data) {
        this.setData(data);
        return this;
    }
}
