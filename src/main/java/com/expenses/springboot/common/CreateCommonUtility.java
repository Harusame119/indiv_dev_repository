package com.expenses.springboot.common;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 共通部品作成クラス
 */
public class CreateCommonUtility {

	/**
	 * 対象年マップ作成メソッド
	 */
	public static Map<String, String> createYearMap() {

		// マップ作成
		Map<String, String> map  = new LinkedHashMap<>();
		map.put("2024", "2024年");
		map.put("2025", "2025年");
		return map;

	}

	/**
	 * 対象月マップ作成メソッド
	 */
	public static Map<String, String> createMonthMap() {

		// マップ作成
		Map<String, String> map  = new LinkedHashMap<>();
		map.put("1", "1月");
		map.put("2", "2月");
		map.put("3", "3月");
		map.put("4", "4月");
		map.put("5", "5月");
		map.put("6", "6月");
		map.put("7", "7月");
		map.put("8", "8月");
		map.put("9", "9月");
		map.put("10", "10月");
		map.put("11", "11月");
		map.put("12", "12月");
		return map;

	}

}
