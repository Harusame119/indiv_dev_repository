package com.expenses.springboot.exp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.expenses.springboot.common.CustomRuntimeException;
import com.expenses.springboot.common.ExConstant;
import com.expenses.springboot.common.dto.CreateMapServiceOut;
import com.expenses.springboot.common.service.CreateMapService;
import com.expenses.springboot.entity.TblExpenseEntity;
import com.expenses.springboot.exp.dto.EXP103ServiceDeleteIn;
import com.expenses.springboot.exp.dto.EXP103ServiceDeleteOut;
import com.expenses.springboot.exp.dto.EXP103ServiceFindIn;
import com.expenses.springboot.exp.dto.EXP103ServiceFindOut;
import com.expenses.springboot.exp.dto.EXP103ServiceUpdateIn;
import com.expenses.springboot.exp.dto.EXP103ServiceUpdateOut;
import com.expenses.springboot.repository.TblExpenseMapper;

/**
 * 出費詳細サービス
 */
@Service
@Transactional
public class EXP103Service {

    @Autowired
    CreateMapService createMapService;

    @Autowired
    TblExpenseMapper tblExpenseMapper;

    /**
     * 初期表示情報検索メソッド
     */
    public EXP103ServiceFindOut display(EXP103ServiceFindIn input) {

        // 初期化処理
        EXP103ServiceFindOut output = new EXP103ServiceFindOut();   // 出力項目
        TblExpenseEntity tblExpenseOut = new TblExpenseEntity();    // 出費テーブルエンティティ出力
        CreateMapServiceOut mapOut = new CreateMapServiceOut();     // マップ作成サービス出力

        try {

            // 検索条件に従って検索
            tblExpenseOut = tblExpenseMapper.findById(input.getExpenseId());

            // 取得結果がnullの場合
            if (tblExpenseOut == null) {
                throw new CustomRuntimeException("");
            }

            /* 
             * マップ作成メソッドの呼び出し
             * 空白フラグ  ：0（空白なし）
             * 対象テーブル：店舗テーブル、種別テーブル、支払者テーブル
             */
            mapOut = createMapService.createMapFromDB(0,
                    ExConstant.TBL_STORE,
                    ExConstant.TBL_CATEGORY,
                    ExConstant.TBL_PAYER);

            // 出力項目に設定
            output.setStoreMap(mapOut.getStoreMap());       // 店舗マップ
            output.setCategoryMap(mapOut.getCategoryMap()); // 種別マップ
            output.setPayerMap(mapOut.getPayerMap());       // 支払者マップ
            output.setTblExpenseEntity(tblExpenseOut);      // 出費テーブルエンティティ

        } catch (CustomRuntimeException e) {

            throw new CustomRuntimeException("対象の出費明細が存在しません");

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

    /**
    * 更新メソッド
    */
    public EXP103ServiceUpdateOut update(EXP103ServiceUpdateIn input) {

        // 初期化処理
        EXP103ServiceUpdateOut output = new EXP103ServiceUpdateOut();   // 出力項目
        TblExpenseEntity tblExpenseIn = new TblExpenseEntity();         // 入力項目
        TblExpenseEntity tblExpenseOut = new TblExpenseEntity();        // 出費テーブルエンティティ出力
        CreateMapServiceOut mapOut = new CreateMapServiceOut();         // マップ作成サービス出力

        /* 
         * マップ作成メソッドの呼び出し
         * 空白フラグ  ：0（空白なし）
         * 対象テーブル：店舗テーブル、種別テーブル、支払者テーブル
         */
        mapOut = createMapService.createMapFromDB(0,
                ExConstant.TBL_STORE,
                ExConstant.TBL_CATEGORY,
                ExConstant.TBL_PAYER);

        // 現在日付取得
        java.sql.Date nowDate = new java.sql.Date(new java.util.Date().getTime());

        // 支払日型変換
        java.sql.Date paymentDate = java.sql.Date.valueOf(input.getPaymentDate());

        try {

            // エンティティ設定
            tblExpenseIn.setId(input.getExpenseId());                       // 出費ID
            tblExpenseIn.setAmount(Integer.valueOf(input.getAmount()));    // 金額
            tblExpenseIn.setStoreId(input.getStoreId());                    // 店舗ID
            tblExpenseIn.setCategoryId(input.getCategoryId());              // 種別ID
            tblExpenseIn.setPayerId(input.getPayerId());                    // 支払者ID
            tblExpenseIn.setPaymentDate(paymentDate);                       // 支払日
            tblExpenseIn.setSplitFlg(input.getSplitFlg());                  // 分割フラグ
            tblExpenseIn.setRegisterDate(nowDate);                          // 登録日
            tblExpenseIn.setRemarks(input.getRemarks());                    // 備考

            // 更新処理実行
            tblExpenseMapper.update(tblExpenseIn);

            // 更新した結果を再取得
            tblExpenseOut = tblExpenseMapper.findById(input.getExpenseId());

            // 出力項目に設定
            output.setStoreMap(mapOut.getStoreMap());
            output.setCategoryMap(mapOut.getCategoryMap());
            output.setPayerMap(mapOut.getPayerMap());
            output.setTblExpenseEntity(tblExpenseOut);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 返却処理
        return output;
    }
}
