package com.expenses.springboot.common.dto;

import java.util.Map;

import lombok.Data;

/**
 * マップ作成サービス出力
 */
@Data
public class CreateMapServiceOut {

	// 店舗テーブルマップ
	private Map<Integer, String> storeMap;

	// 種別テーブルマップ
	private Map<Integer, String> categoryMap;

	// 支払者テーブルマップ
	private Map<Integer, String> payerMap;

}
