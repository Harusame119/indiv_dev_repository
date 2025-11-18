package com.expenses.springboot.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.expenses.springboot.entity.TblPayerEntity;

@Mapper
public interface TblPayerMapper {

	// 全検索
	List<TblPayerEntity> findAll();
	
//	// 主キー検索
//	TblPayerEntity findByPayerId(int payerId);
}
