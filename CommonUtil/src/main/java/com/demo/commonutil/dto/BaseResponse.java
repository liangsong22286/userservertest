package com.demo.commonutil.dto;

/**
 * 
 * @author Administrator
 *
 */
public class BaseResponse {
    private int code=200;//返回的请求状态码，没有实际作用，排错用
    private boolean success=true;//false失败，success成功
    private String msg;//返回的失败或成功信息

    public BaseResponse(boolean success, int code, String msg) {
    	this.success = success;
    	this.code = code;
    	this.msg = msg;
    }
    public BaseResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResponse() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
