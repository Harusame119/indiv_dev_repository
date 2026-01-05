package com.expenses.springboot.exp.dto;

import java.util.Map;

import com.expenses.springboot.entity.TblExpenseEntity;

import lombok.Data;

/**
 * 出費詳細サービス更新出力
 */
@Data
public class EXP103ServiceUpdateOut {

    // 店舗MAP
    private Map<Integer, String> storeMap;

    // 種別MAP
    private Map<Integer, String> categoryMap;

    // 支払者MAP
    private Map<Integer, String> payerMap;

    // 出費テーブルエンティティ
    private TblExpenseEntity tblExpenseEntity;

}
