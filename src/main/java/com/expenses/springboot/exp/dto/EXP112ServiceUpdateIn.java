package com.expenses.springboot.exp.dto;

import lombok.Data;

/**
 * 種別マスタ管理サービス更新入力
 */
@Data
public class EXP112ServiceUpdateIn {

	// 種別ID
	private Integer categoryId;

	// 表示順
	private Integer sortOrder;
}
