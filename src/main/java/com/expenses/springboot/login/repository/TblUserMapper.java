package com.expenses.springboot.login.repository;

import org.apache.ibatis.annotations.Mapper;

import com.expenses.springboot.entity.TblUserEntity;

@Mapper
public interface TblUserMapper {
	TblUserEntity findByUserId(String userId);
}
