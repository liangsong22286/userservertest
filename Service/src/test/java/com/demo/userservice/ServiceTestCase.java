package com.demo.userservice;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.demo.commonutil.dto.TableResultResponse;
import com.demo.commonutil.utilits.Query;
import com.demo.userserver.bo.UserBo;
import com.demo.userserver.service.UserService;

import tk.mybatis.spring.annotation.MapperScan;  

//指定bean注入的配置文件  
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })  
//使用标准的JUnit @RunWith注释来告诉JUnit使用Spring TestRunner  
@RunWith(SpringJUnit4ClassRunner.class)  
@MapperScan(basePackages = "com.demo.userserver.dao")
public class ServiceTestCase extends AbstractJUnit4SpringContextTests {
	@Autowired
	UserService userService;
	@Test
	public void testQueryUser(){
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("username", "1");
        Query query = new Query(params);
        TableResultResponse<UserBo> result = userService.selectByQuery(query);
        System.out.println(result);
	}
	@Test
	public void testInsertUser() throws InstantiationException, IllegalAccessException{
		UserBo entityBo = new UserBo();
		entityBo.setUsername("2");
		entityBo.setName("你好");
        userService.insertSelective(entityBo);
	}
}
