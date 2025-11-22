package com.expenses.springboot.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.expenses.springboot.entity.TblCategoryEntity;

@Mapper
public interface TblCategoryMapper {

	// 検索(ユーザID)
	List<TblCategoryEntity> findByUserId(String userId);

//	// 主キー検索
//	TblCategoryEntity findByCategoryId(int categoryId);
}
