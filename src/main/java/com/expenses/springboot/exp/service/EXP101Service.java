package com.expenses.springboot.exp.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.expenses.springboot.common.CustomRuntimeException;
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
@Transactional
public class EXP101Service {

    @Autowired
    private TblExpenseMapper tblExpenseMapper;

    @Autowired
    CreateMapService createMapService;

    /**
     * 初期表示情報検索メソッド
     */
    public EXP101FormDto display() {

        // 初期化処理
        EXP101FormDto exp101FormDto = new EXP101FormDto();      // 画面DTO
        CreateMapServiceOut mapOut = new CreateMapServiceOut(); // マップ作成サービス出力

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
        exp101FormDto.setPulStore(mapOut.getStoreMap());                // 店舗プルダウンメニュー
        exp101FormDto.setPulCategory(mapOut.getCategoryMap());          // 種別プルダウンメニュー
        exp101FormDto.setPulPayer(mapOut.getPayerMap());                // 支払者プルダウンメニュー
        exp101FormDto.setRadioSplitFlg(ExConstant.SPLIT_FLG_SPLIT);   // 分割フラグラジオボタン

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
    public void register(EXP101ServiceRegisterIn input) {

        try {

            // 初期化処理
            TblExpenseEntity tblExpenseIn = new TblExpenseEntity(); // Input用出費テーブルエンティティ

            // ユーザ名取得
            String userId = SecurityContextHolder.getContext().getAuthentication().getName();

            // 現在日付取得
            java.sql.Date nowDate = new java.sql.Date(new java.util.Date().getTime());

            // 支払日型変換
            java.sql.Date paymentDate = java.sql.Date.valueOf(input.getPaymentDate());

            // エンティティ設定
            tblExpenseIn.setAmount(Integer.valueOf(input.getAmount()));    // 金額
            tblExpenseIn.setStoreId(input.getStoreId());                    // 店舗ID
            tblExpenseIn.setCategoryId(input.getCategoryId());              // 種別ID
            tblExpenseIn.setPayerId(input.getPayerId());                    // 支払者ID
            tblExpenseIn.setPaymentDate(paymentDate);                       // 支払日
            tblExpenseIn.setSplitFlg(input.getSplitFlg());                  // 分割フラグ
            tblExpenseIn.setRegisterDate(nowDate);                          // 登録日
            tblExpenseIn.setRemarks(input.getRemarks());                    // 備考
            tblExpenseIn.setUserId(userId);                                 // ユーザID

            // 登録処理実行
            tblExpenseMapper.insert(tblExpenseIn);

        } catch (Exception e) {
            throw new CustomRuntimeException("登録に失敗しました");
        }
    }
}
