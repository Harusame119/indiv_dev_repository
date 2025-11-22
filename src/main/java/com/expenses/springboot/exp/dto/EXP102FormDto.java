package com.expenses.springboot.exp.dto;

import java.util.List;
import java.util.Map;

import jakarta.validation.constraints.NotEmpty;

import lombok.Data;

/**
 * 出費照会画面FormDto
 */
@Data
public class EXP102FormDto {

	// 開始年月日検索条件
	@NotEmpty
	private String startYMDSearchCondition;

	// 終了年月検索条件
	@NotEmpty
	private String endYMDSearchCondition;

	// 店舗プルダウンメニュー
	private Map<Integer, String> pulStore;
	private Integer storeId;

	// 種別プルダウンメニュー
	private Map<Integer, String> pulCategory;
	private Integer categoryId;

	// 支払者プルダウンメニュー
	private Map<Integer, String> pulPayer;
	private Integer payerId;

	// 備考検索条件
	private String remarksSearchCondition;

	// 出費テーブルエンティティリスト
	private List<FindExpenseTblResultEntity> resultList;

	// 合計金額
	private Integer sumAmount;

	// ID隠し項目
	private String hiddenId;

	// 隠しメッセージ
	private String hdnMsg;
}
