package com.expenses.springboot.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.expenses.springboot.entity.TblCategoryEntity;

@Mapper
public interface TblCategoryMapper {

	// 検索(ユーザID)
	List<TblCategoryEntity> findByUserId(String userId);

    // 登録
    void insert(TblCategoryEntity entity);

    // 主キー削除
    void deleteById(int id);

    // 更新(表示順)
    void updateSortOrderById(TblCategoryEntity entity);

}
