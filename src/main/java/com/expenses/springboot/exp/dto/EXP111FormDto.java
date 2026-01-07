package com.expenses.springboot.exp.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

import com.expenses.springboot.entity.TblStoreEntity;

import lombok.Data;

/**
 * 店舗マスタ管理画面FormDto
 */
@Data
public class EXP111FormDto {

	// 店舗名
	@NotEmpty
	private String storeName;

	// 店舗リスト
	private List<TblStoreEntity> tblStoreList;

	// 隠しメッセージ
	private String hdnMsg;

}
