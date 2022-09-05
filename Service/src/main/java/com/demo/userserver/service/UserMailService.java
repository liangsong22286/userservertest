package com.demo.userserver.service;

import com.demo.commonutil.dto.TableResultResponse;
import com.demo.commonutil.service.ICommon2Service;
import com.demo.commonutil.utilits.Query;
import com.demo.userserver.bo.UserMailBo;
import com.demo.userserver.entity.UserMail;

/**
 * 用户信息表
 *
 */
public interface UserMailService extends ICommon2Service<UserMail,UserMailBo> {
	//根据查询条件，读1或多个
    public TableResultResponse<UserMailBo> selectByQuery(Query query);

	public void sendEmail(Integer userId, String email);
}
