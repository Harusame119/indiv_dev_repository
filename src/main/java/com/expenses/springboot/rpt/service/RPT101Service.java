package com.expenses.springboot.rpt.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.expenses.springboot.common.CustomRuntimeException;
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

        // 初期化処理
        RPT101ServiceTotallingOut output = new RPT101ServiceTotallingOut();                         // 出力項目
        List<TblExpenseEntity> tblExpenseList = new ArrayList<>();                                  // 出費テーブルエンティティリスト
        ExpenseSearchConditionDto conDto = new ExpenseSearchConditionDto();                         // 出費テーブル検索条件Entity
        RPT101TotallingResultEntity c101TotallingResultEntity = new RPT101TotallingResultEntity();  // 帳票(月次)出力集計結果Entity
        Map<Integer, String> payerMap = new LinkedHashMap<>();                                      // 支払者MAP

        // ユーザ名取得
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        try {

            // 支払者リスト取得
            List<TblPayerEntity> payerList = tblPayerMapper.findByUserId(userId);

            // 支払者リストが2件でない場合
            if (payerList.size() != 2) {

                // 例外をスロー
                throw new CustomRuntimeException("支払者テーブルのユーザレコードが2件以外の値になっています");
            }

            // 支払者MAP作成
            for (TblPayerEntity payerInfo : payerList) {
                payerMap.put(payerInfo.getPayerId(), payerInfo.getPayerName());
            }

            // 検索条件設定メソッド呼び出し
            conDto = createSearchCodition(input);

            // ユーザID設定
            conDto.setUserId(userId);

            // 検索条件に従って検索
            tblExpenseList = tblExpenseMapper.findByCondition(conDto);

            // 検索結果が0件でない場合
            if (tblExpenseList.size() != ExConstant.INT_0) {

                // 出費テーブルリスト集計メソッドの呼び出し
                c101TotallingResultEntity = totallingTblExpense(tblExpenseList, input, payerList);

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
            RPT101ServiceTotallingIn input, List<TblPayerEntity> payerList) {

        // 初期化処理
        RPT101TotallingResultEntity output = new RPT101TotallingResultEntity(); // 出力項目
        int payAmountSplit1 = ExConstant.INT_0;     // 支払金額分割有(支払者1)
        int payAmountSplit2 = ExConstant.INT_0;     // 支払金額分割有(支払者2)
        int payAmountUnSplit1 = ExConstant.INT_0;   // 支払金額分割無(支払者1)
        int payAmountUnSplit2 = ExConstant.INT_0;   // 支払金額分割無(支払者2)
        int payAmountPerPerson = ExConstant.INT_0;  // 支払金額1人当

        TblPayerEntity payer1 = payerList.get(0);   // 支払者1
        TblPayerEntity payer2 = payerList.get(1);   // 支払者2

        // 集計処理
        for (TblExpenseEntity entity : inputList) {

            // 出費エンティティの支払者が支払者1の場合
            if (entity.getPayerId() == payer1.getPayerId()) {
                // 分割フラグが"0"(分割有)の場合
                if (entity.getSplitFlg().equals("0")) {

                    // 支払金額分割有(支払者1)に設定
                    payAmountSplit1 += entity.getAmount();

                    // 分割フラグが"1"(分割無)の場合
                } else {

                    // 支払金額分割無(支払者1)に設定
                    payAmountUnSplit1 += entity.getAmount();

                }

                // 出費エンティティの支払者が支払者2の場合
            } else if (entity.getPayerId() == payer2.getPayerId()) {
                // 分割フラグが"0"(分割有)の場合
                if (entity.getSplitFlg().equals("0")) {

                    // 支払金額分割有(支払者2)に設定
                    payAmountSplit2 += entity.getAmount();

                    // 分割フラグが"1"(分割無)の場合
                } else {

                    // 支払金額分割無(支払者2)に設定
                    payAmountUnSplit2 += entity.getAmount();

                }

                // 出費エンティティの支払者が想定外の値の場合
            } else {
                throw new CustomRuntimeException("支払者IDが想定外の値、再実行してください");
            }
        }

        // 集計結果を元に支払額を決定
        payAmountPerPerson = (payAmountSplit1 + payAmountSplit2) / 2;

        // 支払金額分割有(支払者1) > 支払金額分割有(支払者2)の場合
        if (payAmountSplit1 > payAmountSplit2) {

            // 支払者に支払者2を設定
            output.setPayer(payer2.getPayerName());

            // 支払金額を設定
            output.setSettlementAmount(String.valueOf(payAmountPerPerson - payAmountSplit2));

            // 支払金額分割有(支払者2) > 支払金額分割有(支払者1)の場合
        } else if (payAmountSplit2 > payAmountSplit1) {

            // 支払者に支払者1を設定
            output.setPayer(payer1.getPayerName());

            // 支払金額を設定
            output.setSettlementAmount(String.valueOf(payAmountPerPerson - payAmountSplit1));

            // 支払金額分割有(支払者1) = 支払金額分割有(支払者2)の場合
        } else {

            // 支払者に支払者1を設定
            output.setPayer("支払い無し");

            // 支払金額を設定
            output.setSettlementAmount("0");

        }

        // 出力項目設定
        output.setPayer1(payer1.getPayerName());
        output.setPayer2(payer2.getPayerName());
        output.setPayAmountSplit1(String.valueOf(payAmountSplit1));
        output.setPayAmountSplit2(String.valueOf(payAmountSplit2));
        output.setPayAmountUnSplit1(String.valueOf(payAmountUnSplit1));
        output.setPayAmountUnSplit2(String.valueOf(payAmountUnSplit2));

        return output;
    }

}
