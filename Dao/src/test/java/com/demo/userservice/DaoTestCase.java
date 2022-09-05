package com.demo.userservice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.demo.userserver.dao.UserMapper;
import com.demo.userserver.entity.User;

import tk.mybatis.spring.annotation.MapperScan;


//指定bean注入的配置文件  
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })  
//使用标准的JUnit @RunWith注释来告诉JUnit使用Spring TestRunner  
@RunWith(SpringJUnit4ClassRunner.class)  
@MapperScan(basePackages = "com.demo.userserver.dao")
public class DaoTestCase{

	@Autowired
	UserMapper userMapper;
	
	@Test
	public void test(){
		System.out.println("开始测试啦");
		User user = userMapper.selectByPrimaryKey(1);
		System.out.println(user);
		System.out.println("结束测试啦");
	}
	
}
