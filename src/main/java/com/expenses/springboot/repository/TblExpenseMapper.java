package com.expenses.springboot.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.expenses.springboot.entity.TblExpenseEntity;
import com.expenses.springboot.exp.dto.ExpenseSearchConditionDto;

@Mapper
public interface TblExpenseMapper {

	// 検索(ユーザID)
	List<TblExpenseEntity> findByUserId(String userId);

	// 主キー検索
	TblExpenseEntity findById(int id);

	// 条件検索
	List<TblExpenseEntity> findByCondition(ExpenseSearchConditionDto conDto);

	// 登録
	void insert(TblExpenseEntity entity);

	// 主キー削除
	void deleteById(int id);

}
