package com.expenses.springboot.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.expenses.springboot.entity.TblStoreEntity;

@Mapper
public interface TblStoreMapper {

	// 全検索
	List<TblStoreEntity> findAll();

//	// 主キー検索
//	TblStoreEntity findByStoreId(int storeId);
}
