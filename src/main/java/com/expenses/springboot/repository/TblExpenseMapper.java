package com.expenses.springboot.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.expenses.springboot.entity.TblExpenseEntity;

@Mapper
public interface TblExpenseMapper {

	// 全検索
	List<TblExpenseEntity> findAll();

//	// 主キー検索
//	TblExpenseEntity findById(int id);

	// 登録
	void insertExpense(TblExpenseEntity entity);
}
