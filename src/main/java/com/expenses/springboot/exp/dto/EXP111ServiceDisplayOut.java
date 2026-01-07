package com.expenses.springboot.exp.dto;

import java.util.ArrayList;
import java.util.List;

import com.expenses.springboot.entity.TblStoreEntity;

import lombok.Data;

/**
 * 店舗マスタ管理サービス削除出力
 */
@Data
public class EXP111ServiceDisplayOut {

    // 店舗テーブルエンティティリスト
    List<TblStoreEntity> tblStoreList = new ArrayList<>();
}
