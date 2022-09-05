package com.demo.userserver.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.demo.commonutil.dto.TableResultResponse;
import com.demo.commonutil.service.impl.Common2Service;
import com.demo.commonutil.utilits.Query;
import com.demo.commonutil.utilits.StringUtils;
import com.demo.userserver.bo.UserMailBo;
import com.demo.userserver.dao.UserMailMapper;
import com.demo.userserver.entity.UserMail;
import com.demo.userserver.service.UserMailService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @author Administrator
 *
 */
@Service
public class UserMailServiceImpl extends Common2Service<UserMailMapper,UserMail,UserMailBo> implements UserMailService{
	@Override
	public TableResultResponse<UserMailBo> selectByQuery(Query query) {
        String equalColumns = ",";//过滤条件是等于的列
        return selectByQuery(query,equalColumns);
	}
	public TableResultResponse<UserMailBo> selectByQuery(Query query, String equalColumns){
		Example example = new Example(UserMail.class);
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
    	List<UserMail> list = mapper.selectByExample(example);
        List<UserMailBo> voList=new ArrayList<UserMailBo>();
        
		try {
	        for (int i = 0; i < list.size(); i++) {
	        	UserMail tempEntity = list.get(i);
	        	UserMailBo tempBo = getBoFromEntity(tempEntity);
	        	voList.add(tempBo);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
    	return new TableResultResponse<UserMailBo>(result.getTotal(), voList);
	}
	@Override
	public void sendEmail(Integer userId, String email) {
		String content="恭喜你，用户注册成功。";

		//邮件发送部分
		SimpleMailMessage message = new SimpleMailMessage();
	       message.setFrom("xxxx@qq.com");
	       message.setSubject("通知邮件");
	       message.setText(content);

	     String[] mailto = {email};
	       message.setTo(mailto);
	       
    	UserMail entity = new UserMail();
		//发送邮件处理
    	entity.setUserId(userId);
    	entity.setState("1");
		entity.setContent(content);
    	super.synchronizedInsert(entity);
	}

}
