package com.expenses.springboot.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * ユーザーテーブルエンティティ
 */
@Setter
@Getter
public class TblUserEntity {

	// ユーザID
	private String userId;

	// ユーザパスワード
	private String userPass;

	// ユーザロール
	private String userRole;

	// 削除フラグ
	private boolean deleteFlg;
}

