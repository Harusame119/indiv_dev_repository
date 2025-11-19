package com.expenses.springboot.exp.dto;

import lombok.Data;

/**
 * 出費照会サービス検索入力
 */
@Data
public class EXP102ServiceFindIn {

	// 店舗ID
	private Integer storeId;

	// 種別ID
	private Integer categoryId;

	// 支払者ID
	private Integer payerId;

	// 開始日
	private String startDate;

	// 終了日
	private String endDate;

	// 備考
	private String remarks;
}
