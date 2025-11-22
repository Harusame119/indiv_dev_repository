package com.expenses.springboot.exp.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.expenses.springboot.common.ExConstant;
import com.expenses.springboot.common.dto.CreateMapServiceOut;
import com.expenses.springboot.common.service.CreateMapService;
import com.expenses.springboot.entity.TblExpenseEntity;
import com.expenses.springboot.exp.dto.EXP101FormDto;
import com.expenses.springboot.exp.dto.EXP101ServiceRegisterIn;
import com.expenses.springboot.repository.TblExpenseMapper;

/**
 * 出費登録サービス
 */
@Service
public class EXP101Service {

    @Autowired
    private TblExpenseMapper expenseMapper;

	@Autowired
	CreateMapService createMapService;

	/**
	 * 初期表示情報検索メソッド
	 */
	public EXP101FormDto display() {

		EXP101FormDto exp101FormDto = new EXP101FormDto();

		// マップ作成サービス出力
		CreateMapServiceOut mapOut = new CreateMapServiceOut();

		/* 
		 * マップ作成メソッドの呼び出し
		 * 空白フラグ  ：0（空白なし）
		 * 対象テーブル：店舗テーブル、種別テーブル、支払者テーブル
		 */
		mapOut = createMapService.createMapFromDB(0,
				ExConstant.TBL_STORE,
				ExConstant.TBL_CATEGORY,
				ExConstant.TBL_PAYER);

		// 画面初期表示項目設定
		// 店舗プルダウンメニュー
		exp101FormDto.setPulStore(mapOut.getStoreMap());

		// 種別プルダウンメニュー
		exp101FormDto.setPulCategory(mapOut.getCategoryMap());

		// 支払者プルダウンメニュー
		exp101FormDto.setPulPayer(mapOut.getPayerMap());

		// 分割フラグラジオボタン
		exp101FormDto.setRadioSplitFlg(ExConstant.SPLIT_FLG_SPLIT);

		// 支払日
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = sdf.format(date);
		exp101FormDto.setTextPaymentDate(strDate);

		return exp101FormDto;

	}

	/**
	 * 出費テーブル登録メソッド
	 */
	@Transactional
	public void register(EXP101ServiceRegisterIn input) {

		try {

			// Input用出費テーブルエンティティ
			TblExpenseEntity tblExpenseIn = new TblExpenseEntity();

			// ユーザ名取得
			String userId = SecurityContextHolder.getContext().getAuthentication().getName();

			// 現在日付取得
			java.sql.Date nowDate = new java.sql.Date(new java.util.Date().getTime());

			// 支払日型変換
			java.sql.Date paymentDate = java.sql.Date.valueOf(input.getPaymentDate());

			// エンティティ設定
			// 金額
			tblExpenseIn.setAmount(Integer.valueOf(input.getAmount()));
			// 店舗ID
			tblExpenseIn.setStoreId(input.getStoreId());
			// 種別ID
			tblExpenseIn.setCategoryId(input.getCategoryId());
			// 支払者ID
			tblExpenseIn.setPayerId(input.getPayerId());
			// 支払日
			tblExpenseIn.setPaymentDate(paymentDate);
			// 分割フラグ
			tblExpenseIn.setSplitFlg(input.getSplitFlg());
			// 登録日
			tblExpenseIn.setRegisterDate(nowDate);
			// 備考
			tblExpenseIn.setRemarks(input.getRemarks());
			// ユーザID
			tblExpenseIn.setUserId(userId);

			// 登録処理実行
			expenseMapper.insert(tblExpenseIn);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

