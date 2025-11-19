package com.expenses.springboot.rpt.dto;

import lombok.Data;

/**
 * 帳票(月次)出力集計結果Entity
 */
@Data
public class RPT101TotallingResultEntity {

	// 支払者１
	private String payer1;

	// 支払者２
	private String payer2;

	// 支払金額分割有１
	private String payAmountSplit1;

	// 支払金額分割有２
	private String payAmountSplit2;

	// 支払金額分割無１
	private String payAmountUnSplit1;

	// 支払金額分割無２
	private String payAmountUnSplit2;

	// 精算者
	private String payer;

	// 精算額
	private String settlementAmount;
}
