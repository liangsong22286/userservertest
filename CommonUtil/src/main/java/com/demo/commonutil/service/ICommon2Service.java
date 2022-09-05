package com.demo.commonutil.service;

import java.util.List;

import com.demo.commonutil.dto.TableResultResponse;
import com.demo.commonutil.utilits.Query;

/**
 */
public interface ICommon2Service<T,TVo> {
	public TVo selectOne(TVo entityVo);


    public TVo selectById(Object id);


    public List<TVo> selectList(TVo entityVo);


    public List<TVo> selectListAll();


    public Long selectCount(TVo entityVo);


    public void insert(TVo entityVo);
    public void synchronizedInsert(T entity);
    public void insertSelective(TVo entityVo);
    public void synchronizedInsertSelective(T entity);


    public void delete(TVo entityVo);


    public void deleteById(Object id);


    public void updateById(TVo entityVo);
    public void updateEntityById(T entity);
    public void updateSelectiveById(TVo entityVo);
    public void updateEntitySelectiveById(T entity);

    public List<TVo> selectByExample(Object example);

    public int selectCountByExample(Object example);
    
    public TableResultResponse<TVo> selectByQuery(Query query);

    public void loggerInfo(String mess);
    
    public void loggerWarn(String mess);
    
    public void loggerError(String mess);
}
