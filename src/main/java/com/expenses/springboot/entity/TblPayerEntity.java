package com.expenses.springboot.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 支払者テーブルエンティティ
 */
@Setter
@Getter
public class TblPayerEntity {

	private int payerId;

	private String payerName;

	private String userId;

	private int sortOrder;
}
