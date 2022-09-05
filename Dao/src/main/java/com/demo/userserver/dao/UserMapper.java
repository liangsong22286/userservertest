package com.demo.userserver.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.demo.userserver.entity.User;

import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {
	public void updateUsers(@Param("userName") String userName, @Param("updateUserIds") List<Long> updateUserIds);
	public void deleteUsers(@Param("status") String status, @Param("deleteUserIds") List<Long> deleteUserIds);
}