package com.expenses.springboot.entity;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 出費テーブルエンティティ
 */
@Setter
@Getter
public class TblExpenseEntity {

	private int id;

	private int amount;

	private int storeId;

	private int categoryId;

	private int payerId;

	private Date paymentDate;

	private String splitFlg;

	private String remarks;

	private Date resisterDate;

}
