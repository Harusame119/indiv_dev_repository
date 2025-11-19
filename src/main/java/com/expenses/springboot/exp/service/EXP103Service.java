package com.expenses.springboot.exp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expenses.springboot.common.ExConstant;
import com.expenses.springboot.common.dto.CreateMapServiceOut;
import com.expenses.springboot.common.service.CreateMapService;
import com.expenses.springboot.entity.TblExpenseEntity;
import com.expenses.springboot.exp.dto.EXP103ServiceDeleteIn;
import com.expenses.springboot.exp.dto.EXP103ServiceDeleteOut;
import com.expenses.springboot.exp.dto.EXP103ServiceFindIn;
import com.expenses.springboot.exp.dto.EXP103ServiceFindOut;
import com.expenses.springboot.repository.TblExpenseMapper;


/**
 * 出費詳細サービス
 */
@Service
public class EXP103Service {

	@Autowired
	CreateMapService createMapService;

	@Autowired
	TblExpenseMapper tblExpenseMapper;

	/**
	 * 初期表示情報検索メソッド
	 */
	public EXP103ServiceFindOut display(EXP103ServiceFindIn input) {

		// 出力項目
		EXP103ServiceFindOut output = new EXP103ServiceFindOut();

		// 出費テーブルエンティティ出力
		TblExpenseEntity tblExpenseEntityOut = new TblExpenseEntity();

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

		try {

			// 検索条件に従って検索
			tblExpenseEntityOut = tblExpenseMapper.findById(input.getExpenseId());

			// 出力項目に設定
			output.setStoreMap(mapOut.getStoreMap());
			output.setCategoryMap(mapOut.getCategoryMap());
			output.setPayerMap(mapOut.getPayerMap());
			output.setTblExpenseEntity(tblExpenseEntityOut);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// 返却処理
		return output;

	}

	/**
	 * 削除メソッド
	 */
	public EXP103ServiceDeleteOut delete(EXP103ServiceDeleteIn input) {

		// 出力項目
		EXP103ServiceDeleteOut output = new EXP103ServiceDeleteOut();

		try {

			// 削除メソッド呼び出し
			tblExpenseMapper.deleteById(input.getExpenseId());

		} catch (Exception e) {
			e.printStackTrace();
		}

		// 返却処理
		return output;
	}

}
