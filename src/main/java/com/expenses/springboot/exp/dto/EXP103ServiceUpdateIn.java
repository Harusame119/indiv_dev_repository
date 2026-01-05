package com.expenses.springboot.exp.dto;

import lombok.Data;

/**
 * 出費詳細サービス更新入力
 */
@Data
public class EXP103ServiceUpdateIn {

    // 出費ID
    private Integer expenseId;

    // 金額
    private int amount;

    // 店舗ID
    private int storeId;

    // 種別ID
    private int categoryId;

    // 支払者ID
    private int payerId;

    // 支払日
    private String paymentDate;

    // 分割有無フラグ
    private String splitFlg;

    // 備考
    private String remarks;

}
