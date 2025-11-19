package com.expenses.springboot.exp.dto;

import lombok.Data;

/**
 * 出費テーブル検索結果エンティティ
 */
@Data
public class FindExpenseTblResultEntity {

	// ID
	private String id;

	// 支払日
	private String paymentDate;

	// 金額
	private Integer amount;

	// 店舗名
	private String storeNm;

	// 種別名
	private String categoryNm;

	// 支払者名
	private String payerNm;

	// 分割有無
	private String splitStr;

	// 備考
	private String remarks;

}
