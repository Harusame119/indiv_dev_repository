package com.expenses.springboot.exp.dto;

import java.util.ArrayList;
import java.util.List;

import com.expenses.springboot.entity.TblCategoryEntity;

import lombok.Data;

/**
 * 種別マスタ管理サービス削除出力
 */
@Data
public class EXP112ServiceDisplayOut {

    // 種別テーブルエンティティリスト
    List<TblCategoryEntity> tblCategoryList = new ArrayList<>();
}
