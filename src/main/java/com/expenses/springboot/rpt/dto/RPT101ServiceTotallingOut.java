package com.expenses.springboot.rpt.dto;

import lombok.Data;

/**
 * 帳票(月次)出力サービス集計出力
 */
@Data
public class RPT101ServiceTotallingOut {

	// 帳票(月次)出力集計結果Entity
	RPT101TotallingResultEntity resultEntity;
}
