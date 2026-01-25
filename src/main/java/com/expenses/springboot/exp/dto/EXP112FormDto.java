package com.expenses.springboot.exp.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

import com.expenses.springboot.entity.TblCategoryEntity;

import lombok.Data;

/**
 * 種別マスタ管理画面FormDto
 */
@Data
public class EXP112FormDto {

	// 種別名
	@NotEmpty
	private String categoryName;

	// 種別リスト
	private List<TblCategoryEntity> tblCategoryList;

	// 隠しメッセージ
	private String hdnMsg;

}
