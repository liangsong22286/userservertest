package com.demo.commonutil.service.impl;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.demo.commonutil.dto.TableResultResponse;
import com.demo.commonutil.service.ICommon2Service;
import com.demo.commonutil.utilits.EntityUtils;
import com.demo.commonutil.utilits.Query;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;



public abstract class Common2Service<M extends Mapper<T>,T,TBo> implements ICommon2Service<T,TBo>{
	public Logger logger = LoggerFactory.getLogger("samplelog");
	

    @Autowired
    protected M mapper;

    public void setMapper(M mapper) {
        this.mapper = mapper;
    }
    
    public TBo getBoFromEntity(T entity){
    	if(entity==null){return null;}
        Class<TBo> clazzBo = (Class<TBo>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[2];
        TBo entityBo = null;
        try {
        	entityBo = clazzBo.newInstance();
	        BeanUtils.copyProperties(entity,entityBo);
        }catch(Exception e) {
        	e.printStackTrace();
        }
    	return entityBo;
    }
    
    public T getEntityFromBo(TBo entityBo){
    	if(entityBo==null){return null;}
        Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        T entity = null;
        try {
        	entity = clazz.newInstance();
            BeanUtils.copyProperties(entityBo,entity);
        }catch(Exception e) {
        	e.printStackTrace();
        }
    	return entity;
    }

    public TBo selectOne(TBo entityBo) {
    	T entity = getEntityFromBo(entityBo);
        T tempEntity = mapper.selectOne(entity);
    	TBo tempEntityBo = getBoFromEntity(tempEntity);
        
        return tempEntityBo; 
    }


    public TBo selectById(Object id) {
    	T entity = mapper.selectByPrimaryKey(id);
    	TBo tempEntityBo = getBoFromEntity(entity);
        return tempEntityBo; 
    }


    public List<TBo> selectList(TBo entityBo) {
    	T entity = getEntityFromBo(entityBo);
        List<T> list = mapper.select(entity);
        List<TBo> voList=new ArrayList<TBo>();
        for (int i = 0; i < list.size(); i++) {
        	T tempEntity = list.get(i);
        	TBo tempEntityBo = getBoFromEntity(tempEntity);
        	voList.add(tempEntityBo);
		}
        return voList; 
    }


    public List<TBo> selectListAll() {
        List<T> list = mapper.selectAll();
        List<TBo> voList=new ArrayList<TBo>();
        for (int i = 0; i < list.size(); i++) {
        	T tempEntity = list.get(i);
        	TBo tempEntityBo = getBoFromEntity(tempEntity);
        	voList.add(tempEntityBo);
		}
        return voList; 
    }


    public Long selectCount(TBo entityBo) {
    	T entity = getEntityFromBo(entityBo);
        return new Long(mapper.selectCount(entity));
    }


	public void insert(TBo entityBo) {
    	T entity = getEntityFromBo(entityBo);
        EntityUtils.setCreatAndUpdatInfo(entity);
    	//防止生成的逐渐重复，改为同步,此处还可以使用redis锁来进行控制
    	synchronized (this.getClass()) {
    		mapper.insert(entity);
    		BeanUtils.copyProperties(entity,entityBo);
		}
    }
    public void synchronizedInsert(T entity){
        EntityUtils.setCreatAndUpdatInfo(entity);
    	//防止生成的逐渐重复，改为同步,此处还可以使用redis锁来进行控制
    	synchronized (this.getClass()) {
    		mapper.insert(entity);
		}
    }
    public void insertSelective(TBo entityBo) {
    	T entity = getEntityFromBo(entityBo);
        EntityUtils.setCreatAndUpdatInfo(entity);
    	//防止生成的逐渐重复，改为同步,此处还可以使用redis锁来进行控制
    	synchronized (this.getClass()) {
            mapper.insertSelective(entity);
    		BeanUtils.copyProperties(entity,entityBo);
		}
    }
    public void synchronizedInsertSelective(T entity){
        EntityUtils.setCreatAndUpdatInfo(entity);
    	//防止生成的逐渐重复，改为同步,此处还可以使用redis锁来进行控制
    	synchronized (this.getClass()) {
    		mapper.insertSelective(entity);
		}
    }


    public void delete(TBo entityBo) {
    	T entity = getEntityFromBo(entityBo);
        mapper.delete(entity);
    }


    public void deleteById(Object id) {
        mapper.deleteByPrimaryKey(id);
    }


    public void updateById(TBo entityBo) {
    	T entity = getEntityFromBo(entityBo);
        EntityUtils.setUpdatedInfo(entity);
        mapper.updateByPrimaryKey(entity);
    }
    public void updateEntityById(T entity){
        EntityUtils.setUpdatedInfo(entity);
        mapper.updateByPrimaryKey(entity);
    }
    public void updateSelectiveById(TBo entityBo) {
    	T entity = getEntityFromBo(entityBo);
        EntityUtils.setUpdatedInfo(entity);
        mapper.updateByPrimaryKeySelective(entity);

    }
    public void updateEntitySelectiveById(T entity){
        EntityUtils.setUpdatedInfo(entity);
        mapper.updateByPrimaryKeySelective(entity);

    }
    
    public List<TBo> selectByExample(Object example) {
    	List<T> list = mapper.selectByExample(example);
        List<TBo> voList=new ArrayList<TBo>();
        for (int i = 0; i < list.size(); i++) {
        	T tempEntity = list.get(i);
        	TBo tempEntityBo = getBoFromEntity(tempEntity);
        	voList.add(tempEntityBo);
		}
        return voList;
    }

    public int selectCountByExample(Object example) {
        return mapper.selectCountByExample(example);
    }

    public TableResultResponse<TBo> selectByQuery(Query query) {
        Class<TBo> clazz = (Class<TBo>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Example example = new Example(clazz);
        if(query.entrySet().size()>0) {
            Example.Criteria criteria = example.createCriteria();
            for (Map.Entry<String, Object> entry : query.entrySet()) {
                criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
            }
        }
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        List<TBo> list = selectByExample(example);
        return new TableResultResponse<TBo>(result.getTotal(), list);
    }

    public String getRuntimeClassName(){
    	return this.getClass().getName();
	}
    
    public void loggerInfo(String mess){
    	String requestIdAndUrl = "";
    	try{
    		// 接收到请求，记录请求内容
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            
	        HttpServletRequest request = attributes.getRequest();
	    	requestIdAndUrl = (String) request.getAttribute("requestIdAndUrl");
        }catch(Exception e){}
    	logger.info(requestIdAndUrl+"("+getRuntimeClassName()+"):::"+mess);
    }
    public void loggerWarn(String mess){
    	String requestIdAndUrl = "";
    	try{
    		// 接收到请求，记录请求内容
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            
	        HttpServletRequest request = attributes.getRequest();
	    	requestIdAndUrl = (String) request.getAttribute("requestIdAndUrl");
        }catch(Exception e){}
    	logger.warn(requestIdAndUrl+"("+getRuntimeClassName()+"):::"+mess);
    }
    public void loggerError(String mess){
    	String requestIdAndUrl = "";
    	try{
    		// 接收到请求，记录请求内容
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            
	        HttpServletRequest request = attributes.getRequest();
	    	requestIdAndUrl = (String) request.getAttribute("requestIdAndUrl");
        }catch(Exception e){}
    	logger.error(requestIdAndUrl+"("+getRuntimeClassName()+"):::"+mess);
    }
    
}
