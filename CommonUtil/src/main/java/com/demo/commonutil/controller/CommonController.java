package com.demo.commonutil.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.demo.commonutil.dto.ObjectRestResponse;
import com.demo.commonutil.dto.TableResultResponse;
import com.demo.commonutil.service.ICommon2Service;
import com.demo.commonutil.utilits.Query;


public class CommonController<Service extends ICommon2Service,Entity>  {
    @Autowired
    protected Service baseService;
	public Logger logger = LoggerFactory.getLogger("samplelog");	
	

    public ObjectRestResponse<Entity> add(@RequestBody Entity entity){
        baseService.insertSelective(entity);
        return new ObjectRestResponse<Entity>(1,true,"成功");
    }

    public ObjectRestResponse<Entity> get(@PathVariable long id){
    	Entity entity = (Entity) baseService.selectById(id);
        return new ObjectRestResponse<Entity>(1,true,"成功").data(entity);
    }

    public ObjectRestResponse<Entity> update(@RequestBody Entity entity){
        baseService.updateSelectiveById(entity);
        return new ObjectRestResponse<Entity>(1,true,"成功");
    }
    
    public ObjectRestResponse<Entity> remove(@PathVariable long id){
        baseService.deleteById(id);
        return new ObjectRestResponse<Entity>(1,true,"成功");
    }

    public List<Entity> list(){
        return baseService.selectListAll();
    }
    public TableResultResponse<Entity> list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);
        return baseService.selectByQuery(query);
    }


    public String getRuntimeClassName(){
    	return this.getClass().getName();
	}
    public void loggerInfo(String mess){
    	// 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
    	String requestIdAndUrl = (String) request.getAttribute("requestIdAndUrl");
    	logger.info(requestIdAndUrl+"("+getRuntimeClassName()+"):::"+mess);
    }
    public void loggerWarn(String mess){
    	// 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
    	String requestIdAndUrl = (String) request.getAttribute("requestIdAndUrl");
    	logger.warn(requestIdAndUrl+"("+getRuntimeClassName()+"):::"+mess);
    }
    public void loggerError(String mess){
    	// 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
    	String requestIdAndUrl = (String) request.getAttribute("requestIdAndUrl");
    	logger.error(requestIdAndUrl+"("+getRuntimeClassName()+"):::"+mess);
    }
    
   
}
