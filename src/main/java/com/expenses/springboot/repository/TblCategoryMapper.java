package com.expenses.springboot.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.expenses.springboot.entity.TblCategoryEntity;

@Mapper
public interface TblCategoryMapper {

	// 全検索
	List<TblCategoryEntity> findAll();

//	// 主キー検索
//	TblCategoryEntity findByCategoryId(int categoryId);
}
