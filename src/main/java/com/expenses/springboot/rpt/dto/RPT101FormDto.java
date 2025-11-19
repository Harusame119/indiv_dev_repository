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

	// 対象月プルダウンメニュー
	private Map<String, String> pulTargetMonth;

	// 帳票(月次)出力集計結果Entity
	RPT101TotallingResultEntity resultEntity;

	// 隠しメッセージ
	private String hdnMsg;
}
