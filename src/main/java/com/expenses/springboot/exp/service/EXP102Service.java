package com.expenses.springboot.exp.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.expenses.springboot.common.CustomRuntimeException;
import com.expenses.springboot.common.ExConstant;
import com.expenses.springboot.common.dto.CreateMapServiceOut;
import com.expenses.springboot.common.service.CreateMapService;
import com.expenses.springboot.entity.TblExpenseEntity;
import com.expenses.springboot.exp.dto.EXP102FormDto;
import com.expenses.springboot.exp.dto.EXP102ServiceFindIn;
import com.expenses.springboot.exp.dto.EXP102ServiceFindOut;
import com.expenses.springboot.exp.dto.ExpenseSearchConditionDto;
import com.expenses.springboot.exp.dto.FindExpenseTblResultEntity;
import com.expenses.springboot.repository.TblExpenseMapper;

/**
 * 出費照会サービス
 */
@Service
@Transactional
public class EXP102Service {

    @Autowired
    CreateMapService createMapService;

    @Autowired
    private TblExpenseMapper tblExpenseMapper;

    /**
     * 初期表示情報検索メソッド
     */
    public EXP102FormDto display() {

        EXP102FormDto exp102FormDto = new EXP102FormDto();

        // マップ作成サービス出力
        CreateMapServiceOut mapOut = new CreateMapServiceOut();

        /* 
         * マップ作成メソッドの呼び出し
         * 空白フラグ  ：1（空白あり）
         * 対象テーブル：店舗テーブル、種別テーブル、支払者テーブル
         */
        mapOut = createMapService.createMapFromDB(1,
                ExConstant.TBL_STORE,
                ExConstant.TBL_CATEGORY,
                ExConstant.TBL_PAYER);

        // 当日日付取得
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sdf.format(date);

        // 画面初期表示項目設定
        exp102FormDto.setPulStore(mapOut.getStoreMap());        // 店舗プルダウンメニュー
        exp102FormDto.setPulCategory(mapOut.getCategoryMap());  // 種別プルダウンメニュー
        exp102FormDto.setPulPayer(mapOut.getPayerMap());        // 支払者プルダウンメニュー
        exp102FormDto.setStartYMDSearchCondition(strDate);      // 開始日
        exp102FormDto.setEndYMDSearchCondition(strDate);        // 終了日

        return exp102FormDto;

    }

    /**
     * 出費テーブル検索メソッド
     */
    public EXP102ServiceFindOut find(EXP102ServiceFindIn input) {

        // 初期化処理
        EXP102ServiceFindOut output = new EXP102ServiceFindOut();           // 出力項目
        List<TblExpenseEntity> tblExpenseList = new ArrayList<>();          // 出費テーブルエンティティリスト
        ExpenseSearchConditionDto conDto = new ExpenseSearchConditionDto(); // 出費テーブル検索条件Entity
        CreateMapServiceOut mapOut = new CreateMapServiceOut();             // マップ作成サービス出力

        /* 
         * マップ作成メソッドの呼び出し
         * 空白フラグ  ：1（空白あり）
         * 対象テーブル：店舗テーブル、種別テーブル、支払者テーブル
         */
        mapOut = createMapService.createMapFromDB(1,
                ExConstant.TBL_STORE,
                ExConstant.TBL_CATEGORY,
                ExConstant.TBL_PAYER);

        try {

            // ユーザ名取得
            String userId = SecurityContextHolder.getContext().getAuthentication().getName();

            // 開始日型変換
            java.sql.Date startDate = java.sql.Date.valueOf(input.getStartDate());

            // 終了日型変換
            java.sql.Date endDate = java.sql.Date.valueOf(input.getEndDate());

            // 検索条件設定
            conDto.setStartDate(startDate);                 // 開始日
            conDto.setEndDate(endDate);                     // 終了日
            conDto.setStoreId(input.getStoreId());          // 店舗ID
            conDto.setCategoryId(input.getCategoryId());    // 種別ID
            conDto.setPayerId(input.getPayerId());          // 支払者ID
            conDto.setRemarks(input.getRemarks());          // 備考
            conDto.setUserId(userId);                       // ユーザID

            // 検索条件に従って検索
            tblExpenseList = tblExpenseMapper.findByCondition(conDto);

            // 出力項目に設定
            output.setStoreMap(mapOut.getStoreMap());       // 店舗マップ
            output.setCategoryMap(mapOut.getCategoryMap()); // 種別マップ
            output.setPayerMap(mapOut.getPayerMap());       // 支払者マップ

            // 出費テーブルリストがnullでない場合、出費テーブル検索結果リストを作成する
            if (tblExpenseList != null) {

                // 出費テーブル検索結果作成リスト
                List<FindExpenseTblResultEntity> resultList = new ArrayList<>();

                // 合計金額
                int sum = 0;

                // 取得した出費テーブルエンティティを回す
                for (TblExpenseEntity entity : tblExpenseList) {

                    // 出費テーブル検索結果エンティティ
                    FindExpenseTblResultEntity resultEntity = new FindExpenseTblResultEntity();

                    // 支払日の変換
                    SimpleDateFormat sdf = new SimpleDateFormat(ExConstant.DATE_FORMAT_SPLIT_HYPHEN);
                    String strDate = sdf.format(entity.getPaymentDate());

                    // 値の設定
                    resultEntity.setId(String.valueOf(entity.getId()));                                // ID 
                    resultEntity.setPaymentDate(strDate);                                               // 支払日
                    resultEntity.setAmount(entity.getAmount());                                         // 金額
                    resultEntity.setStoreNm(mapOut.getStoreMap().get(entity.getStoreId()));             // 店舗ID
                    resultEntity.setCategoryNm(mapOut.getCategoryMap().get(entity.getCategoryId()));    // 種別ID
                    resultEntity.setPayerNm(mapOut.getPayerMap().get(entity.getPayerId()));             // 支払者ID

                    // 分割有無が"0"の場合"〇"、"1"の場合"×"
                    if (ExConstant.SPLIT_FLG_SPLIT.equals(entity.getSplitFlg())) {
                        resultEntity.setSplitStr(ExConstant.DISP_STR_CIRCLE);
                    } else if (ExConstant.SPLIT_FLG_NONSPLIT.equals(entity.getSplitFlg())) {
                        resultEntity.setSplitStr(ExConstant.DISP_STR_CROSS);
                    }

                    // 備考が12桁以上の場合、12桁に切り出す
                    if (entity.getRemarks().length() <= 12) {
                        resultEntity.setRemarks(entity.getRemarks());
                    } else {
                        resultEntity.setRemarks(entity.getRemarks().substring(0, 12));
                    }

                    // 金額を合計金額に加算
                    sum += entity.getAmount();

                    // 作成した検索結果エンティティを結果リストに格納
                    resultList.add(resultEntity);

                }

                // 検索結果を出力項目に設定
                output.setResultList(resultList);

                // 金額を出力項目に設定
                output.setSum(sum);
            }

        } catch (Exception e) {
            throw new CustomRuntimeException("照会に失敗しました");
        }

        // 返却処理
        return output;

    }

}
