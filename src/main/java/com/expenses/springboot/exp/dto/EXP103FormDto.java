package com.expenses.springboot.exp.dto;

import java.util.Map;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * 出費詳細画面FormDto
 */
@Data
public class EXP103FormDto {

	// 金額テキストボックス
	@NotEmpty
	private String textAmount;

	// 店舗プルダウンメニュー
	private Map<Integer, String> pulStore;
    private Integer storeId;

	// 種別プルダウンメニュー
	private Map<Integer, String> pulCategory;
    private Integer categoryId;

	// 支払者プルダウンメニュー
	private Map<Integer, String> pulPayer;
    private Integer payerId;

	// 支払日テキストボックス
	@NotNull
	private String textPaymentDate;

	// 分割フラグラジオボタン
	private String radioSplitFlg;

	// 備考テキストボックス
	private String textRemarks;

	// ID隠し項目
	private String hiddenId;

	// 隠しメッセージ
	private String hdnMsg;
}
