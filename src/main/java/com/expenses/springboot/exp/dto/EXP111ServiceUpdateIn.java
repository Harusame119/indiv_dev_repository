package com.expenses.springboot.exp.dto;

import lombok.Data;

/**
 * 店舗マスタ管理サービス更新入力
 */
@Data
public class EXP111ServiceUpdateIn {

	// 店舗ID
	private Integer storeId;

	// 表示順
	private Integer sortOrder;
}
