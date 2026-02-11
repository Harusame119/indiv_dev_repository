package com.expenses.springboot.rpt.dto;

import lombok.Data;

/**
 * 帳票(月次)出力サービス集計入力
 */
@Data
public class RPT101ServiceTotallingIn {

	// 対象年
	private String targetYear;

	// 対象月
	private String targetMonth;

}
