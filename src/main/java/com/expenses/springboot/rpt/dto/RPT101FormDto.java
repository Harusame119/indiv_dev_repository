package com.expenses.springboot.rpt.dto;

import java.util.Map;

import lombok.Data;

/**
 * 帳票(月次)出力FormDto
 */
@Data
public class RPT101FormDto {

	// 対象年プルダウンメニュー
	private Map<String, String> pulTargetYear;
	private String targetYear;

	// 対象月プルダウンメニュー
	private Map<String, String> pulTargetMonth;
	private String targetMonth;

	// 帳票(月次)出力集計結果Entity
	RPT101TotallingResultEntity resultEntity;

	// 隠しメッセージ
	private String hdnMsg;
}
