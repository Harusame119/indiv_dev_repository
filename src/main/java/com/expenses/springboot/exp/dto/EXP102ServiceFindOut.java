package com.expenses.springboot.exp.dto;

import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * 出費照会サービス検索出力
 */
@Data
public class EXP102ServiceFindOut {

	// 店舗MAP
	private Map<Integer, String> storeMap;

	// 種別MAP
	private Map<Integer, String> categoryMap;

	// 支払者MAP
	private Map<Integer, String> payerMap;

	// 出費テーブル検索結果エンティティリスト
	private List<FindExpenseTblResultEntity>  resultList;

	// 合計金額
	private Integer sum;
}
