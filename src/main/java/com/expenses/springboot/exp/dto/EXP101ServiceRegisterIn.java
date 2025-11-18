package com.expenses.springboot.exp.dto;

import lombok.Data;

/**
 * 出費登録サービス登録入力
 */
@Data
public class EXP101ServiceRegisterIn {

	// 金額
	private String amount;

	// 店舗ID
	private Integer storeId;

	// 種別ID
	private Integer categoryId;

	// 支払者ID
	private Integer payerId;

	// 支払日
	private String paymentDate;

	// 分割フラグ
	private String splitFlg;

	// 備考
	private String remarks;

}
