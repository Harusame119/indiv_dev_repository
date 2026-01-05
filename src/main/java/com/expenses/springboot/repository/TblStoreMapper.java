package com.expenses.springboot.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.expenses.springboot.entity.TblStoreEntity;

@Mapper
public interface TblStoreMapper {

	// 検索(ユーザID)
	List<TblStoreEntity> findByUserId(String userId);

	// 登録
	void insert(TblStoreEntity entity);
}
