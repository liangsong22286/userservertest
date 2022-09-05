package com.demo.userserver.config;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.demo.commonutil.dto.BaseResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * RestControllerAdvice，统一异常处理
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandlerConfig {
	 /**
     * Exception出错的栈信息转成字符串
	     * 用于打印到日志中
	     */
    public String errorInfoToString(Throwable e) {
        //try-with-resource语法糖 处理机制
        try(StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw)){
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
            return sw.toString();
        }catch (Exception ignored){
            throw new RuntimeException(ignored.getMessage(),ignored);
        }
    }
    /**
     * 未知异常处理
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseResponse exceptionHandler(Exception e) {
        // 把错误信息输入到日志中
        log.error(errorInfoToString(e));
        return new BaseResponse(false, 500, "接口响应异常");
    }

    /**
     * 空指针异常
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public BaseResponse exceptionHandler(NullPointerException e) {
        log.error(errorInfoToString(e));
        return new BaseResponse(false, 500, "接口响应异常");
    }
}