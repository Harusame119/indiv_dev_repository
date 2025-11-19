package com.expenses.springboot.exp.dto;

import java.sql.Date;

import lombok.Data;

/**
 * 出費テーブル検索条件エンティティ
 */
@Data
public class ExpenseSearchConditionDto {

	// 開始日
	private Date startDate;

	// 終了日
	private Date endDate;

	// 店舗ID
	private int storeId;

	// 種別ID
	private int categoryId;

	// 支払者ID
	private int payerId;

	// 備考
	private String remarks;
}
