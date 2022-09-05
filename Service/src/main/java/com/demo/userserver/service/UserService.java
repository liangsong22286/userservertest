package com.demo.userserver.service;

import java.util.List;

import com.demo.commonutil.dto.TableResultResponse;
import com.demo.commonutil.service.ICommon2Service;
import com.demo.commonutil.utilits.Query;
import com.demo.userserver.bo.UserBo;
import com.demo.userserver.entity.User;

/**
 * 用户信息表
 *
 */
public interface UserService extends ICommon2Service<User,UserBo> {
	//新增1个
    public void insertSelective(UserBo entityBo);
    
	//根据查询条件，读1或多个
    public TableResultResponse<UserBo> selectByQuery(Query query);

	//写1或多个
    public void updateUsers(UserBo entityBo, List<Long> ids) throws Exception;

	//删1或多个
	public void deleteUsers(List<Long> ids) throws Exception;
}
