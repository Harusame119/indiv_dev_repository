package com.expenses.springboot.rpt.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expenses.springboot.common.ExConstant;
import com.expenses.springboot.entity.TblExpenseEntity;
import com.expenses.springboot.entity.TblPayerEntity;
import com.expenses.springboot.exp.dto.ExpenseSearchConditionDto;
import com.expenses.springboot.repository.TblExpenseMapper;
import com.expenses.springboot.repository.TblPayerMapper;
import com.expenses.springboot.rpt.dto.RPT101ServiceTotallingIn;
import com.expenses.springboot.rpt.dto.RPT101ServiceTotallingOut;
import com.expenses.springboot.rpt.dto.RPT101TotallingResultEntity;

/**
 * 帳票(月次)出力集計サービス
 */
@Service
public class RPT101Service {

	@Autowired
	TblExpenseMapper tblExpenseMapper;

	@Autowired
	TblPayerMapper tblPayerMapper;

	/**
	 * 帳票集計メソッド
	 */
	public RPT101ServiceTotallingOut totalling(RPT101ServiceTotallingIn input) {

		// 出力項目
		RPT101ServiceTotallingOut output = new RPT101ServiceTotallingOut();

		// 出費テーブルエンティティリスト
		List<TblExpenseEntity> tblExpenseList = new ArrayList<>();

		// 出費テーブル検索条件Entity
		ExpenseSearchConditionDto conDto = new ExpenseSearchConditionDto();

		// 帳票(月次)出力集計結果Entity
		RPT101TotallingResultEntity c101TotallingResultEntity = new RPT101TotallingResultEntity();

		// 支払者リスト取得
		Iterable<TblPayerEntity> payerList = tblPayerMapper.findAll();
		Map<Integer, String> payerMap = new LinkedHashMap<>();

		// 支払者MAP作成
		for (TblPayerEntity payerInfo : payerList) {
			payerMap.put(payerInfo.getPayerId(), payerInfo.getPayerName());
		}

		try {

			// 検索条件設定メソッド呼び出し
			conDto = createSearchCodition(input);

			// 検索条件に従って検索
			tblExpenseList = tblExpenseMapper.findByCondition(conDto);

			// 検索結果が0件でない場合
			if (tblExpenseList.size() != ExConstant.INT_0) {

				// 出費テーブルリスト集計メソッドの呼び出し
				c101TotallingResultEntity = totallingTblExpense(tblExpenseList, input, payerMap);

				// 出力項目に設定
				output.setResultEntity(c101TotallingResultEntity);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output;

	}

	/**
	 * 検索条件設定メソッド
	 */
	private ExpenseSearchConditionDto createSearchCodition(RPT101ServiceTotallingIn input) throws Exception {

		// 出力項目
		ExpenseSearchConditionDto output = new ExpenseSearchConditionDto();

		// カレンダーインスタンスを取得
		Calendar cal = Calendar.getInstance();

		// 対象月の最終日を取得
		cal.set(Calendar.YEAR, Integer.valueOf(input.getTargetYear()));
		cal.set(Calendar.MONTH, Integer.valueOf(input.getTargetMonth()) - 1);
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		// 結合文字列
		StringBuilder startDateStr = new StringBuilder();
		StringBuilder endDateStr = new StringBuilder();

		// 対象月の開始日編集処理
		startDateStr.append(input.getTargetYear());
		startDateStr.append(ExConstant.STR_HYPHEN);
		startDateStr.append(input.getTargetMonth());
		startDateStr.append(ExConstant.STR_HYPHEN);
		startDateStr.append(ExConstant.STR_01);

		// 開始日型変換
		java.sql.Date startDate = java.sql.Date.valueOf(startDateStr.toString());

		// 対象月の最終日編集処理
		endDateStr.append(input.getTargetYear());
		endDateStr.append(ExConstant.STR_HYPHEN);
		endDateStr.append(input.getTargetMonth());
		endDateStr.append(ExConstant.STR_HYPHEN);
		endDateStr.append(lastDay);

		// 終了日型変換
		java.sql.Date endDate = java.sql.Date.valueOf(endDateStr.toString());

		// 出力項目設定
		output.setStartDate(startDate);
		output.setEndDate(endDate);

		// 返却処理
		return output;

	}

	/**
	 * 出費テーブルリスト集計メソッド
	 */
	private RPT101TotallingResultEntity totallingTblExpense(List<TblExpenseEntity> inputList,
			RPT101ServiceTotallingIn input, Map<Integer, String> payerMap) {

		// 出力項目
		RPT101TotallingResultEntity output = new RPT101TotallingResultEntity();

		// 支払者毎にリスト作成

		// 分割フラグ毎にフラグ作成


		// 疎通用スタブデータ
		output.setPayer1("うしお");
		output.setPayer2("なつみ");
		output.setPayAmountSplit1("100000");
		output.setPayAmountSplit2("80000");
		output.setPayAmountUnSplit1("0");
		output.setPayAmountUnSplit2("3000");
		output.setPayer("なつみ");
		output.setSettlementAmount("7000");

		return output;
	}

}
