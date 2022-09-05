package com.demo.userserver.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.commonutil.dto.TableResultResponse;
import com.demo.commonutil.service.impl.Common2Service;
import com.demo.commonutil.utilits.Query;
import com.demo.commonutil.utilits.StringUtils;
import com.demo.userserver.bo.UserBo;
import com.demo.userserver.constant.UserConstant;
import com.demo.userserver.dao.UserMapper;
import com.demo.userserver.entity.User;
import com.demo.userserver.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @author Administrator
 *
 */
@Service
public class UserServiceImpl extends Common2Service<UserMapper,User,UserBo> implements UserService{
	@Override
	public TableResultResponse<UserBo> selectByQuery(Query query) {
        String equalColumns = ",";//过滤条件是等于的列
        return selectByQuery(query,equalColumns);
	}
	public TableResultResponse<UserBo> selectByQuery(Query query, String equalColumns){
		Example example = new Example(User.class);
    	if(query.entrySet().size()>0) {
    		Example.Criteria criteria = example.createCriteria();
    		for (Map.Entry<String, Object> entry : query.entrySet()) {
    			if(null!=equalColumns && equalColumns.indexOf(","+entry.getKey()+",")>=0){
    				criteria.andEqualTo(entry.getKey(), entry.getValue().toString());
    			}else{
    				criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
    			}
    		}
    	}
    	if(!StringUtils.isNullStr(query.getSortColumn())){
    		String order =  "asc";//ascending,descending
    		if(!StringUtils.isNullStr(query.getSortAsc())){
    			order = query.getSortAsc().replace("ending", "");
    		}
    		example.setOrderByClause(" "+query.getSortColumn()+" "+order);
    	}
    	Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
    	List<User> list = mapper.selectByExample(example);
        List<UserBo> voList=new ArrayList<UserBo>();
        
		try {
	        for (int i = 0; i < list.size(); i++) {
	        	User tempEntity = list.get(i);
	        	UserBo tempBo = getBoFromEntity(tempEntity);
	        	voList.add(tempBo);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
    	return new TableResultResponse<UserBo>(result.getTotal(), voList);
	}


	@Override
	public void updateUsers(UserBo entityBo, List<Long> ids) throws Exception {
		if(null!=ids && ids.size()>0) {
			//此处只更改了，姓名，这一属性
			mapper.updateUsers(entityBo.getUsername(), ids);
		}
	}


	@Override
	public void deleteUsers(List<Long> ids) throws Exception {
		if(null!=ids && ids.size()>0) {
			String status = "0";
			mapper.deleteUsers(status,ids);
		}
	}

	@Override
    public void insertSelective(UserBo entityBo) {
    	User tempEntity = getEntityFromBo(entityBo);

    	if(StringUtils.isNullStr(tempEntity.getPassword())){
    		tempEntity.setPassword(UserConstant.DEFAULT_PASSWORD);
    	}
        String password = new BCryptPasswordEncoder(UserConstant.PW_ENCORDER_SALT).encode(tempEntity.getPassword());
        tempEntity.setPassword(password);
    	entityBo.setStatus("1");
        super.synchronizedInsert(tempEntity);
    }

    /**
    @Override
    public void updateSelectiveById(UserBo entityBo) throws InstantiationException, IllegalAccessException {
    	if(entityBo.getPassword()!=null && !entityBo.getPassword().toString().trim().equals("")){
	        String password = new BCryptPasswordEncoder(UserConstant.PW_ENCORDER_SALT).encode(entityBo.getPassword());
	        entityBo.setPassword(password);
    	}
    	entityBo.setStatus("1");
        super.updateSelectiveById(entityBo);
    }
     */

}
