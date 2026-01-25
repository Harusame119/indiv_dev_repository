package com.expenses.springboot.exp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.expenses.springboot.common.CustomRuntimeException;
import com.expenses.springboot.common.ExConstant;
import com.expenses.springboot.entity.TblCategoryEntity;
import com.expenses.springboot.entity.TblExpenseEntity;
import com.expenses.springboot.exp.dto.EXP112ServiceDeleteIn;
import com.expenses.springboot.exp.dto.EXP112ServiceDeleteOut;
import com.expenses.springboot.exp.dto.EXP112ServiceDisplayOut;
import com.expenses.springboot.exp.dto.EXP112ServiceRegisterIn;
import com.expenses.springboot.exp.dto.EXP112ServiceUpdateIn;
import com.expenses.springboot.exp.dto.EXP112ServiceUpdateOut;
import com.expenses.springboot.exp.dto.ExpenseSearchConditionDto;
import com.expenses.springboot.repository.TblCategoryMapper;
import com.expenses.springboot.repository.TblExpenseMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 種別マスタ管理サービス
 */
@Slf4j
@Service
@Transactional
public class EXP112Service {

    @Autowired
    private TblCategoryMapper tblCategoryMapper;

    @Autowired
    private TblExpenseMapper tblExpenseMapper;

    /**
     * 初期表示情報検索メソッド
     */
    public EXP112ServiceDisplayOut display() {

        // 出力項目
        EXP112ServiceDisplayOut output = new EXP112ServiceDisplayOut();
        // 画面表示用リスト
        List<TblCategoryEntity> tblCategoryList = new ArrayList<>();

        try {

            // ユーザ名取得
            String userId = SecurityContextHolder.getContext().getAuthentication().getName();

            // ユーザ名を条件にして検索
            tblCategoryList = tblCategoryMapper.findByUserId(userId);

            // 検索結果を出力項目に設定
            output.setTblCategoryList(tblCategoryList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 返却処理
        return output;

    }

    /**
     * 種別テーブル登録メソッド
     */
    public void register(EXP112ServiceRegisterIn input) {

        // Input用種別テーブルエンティティ
        TblCategoryEntity tblCategoryIn = new TblCategoryEntity();

        try {

            // ユーザ名取得
            String userId = SecurityContextHolder.getContext().getAuthentication().getName();

            // 登録項目設定
            // 種別名
            tblCategoryIn.setCategoryName(input.getCategoryName());
            // ユーザ名
            tblCategoryIn.setUserId(userId);
            // 表示順
            tblCategoryIn.setSortOrder(ExConstant.INT_DEFAULT_SORT_ORDER);

            // 登録処理実行
            tblCategoryMapper.insert(tblCategoryIn);

            log.info("登録を実行しました");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 削除メソッド
     */
    public EXP112ServiceDeleteOut delete(EXP112ServiceDeleteIn input) {

        // 初期化処理
        EXP112ServiceDeleteOut output = new EXP112ServiceDeleteOut();       // 出力項目
        List<TblExpenseEntity> tblExpenseList = new ArrayList<>();          // 出費テーブルエンティティリスト
        ExpenseSearchConditionDto conDto = new ExpenseSearchConditionDto(); // 出費テーブル検索条件Entity

        try {

            // ユーザ名取得
            String userId = SecurityContextHolder.getContext().getAuthentication().getName();

            // 検索条件設定
            conDto.setCategoryId(input.getCategoryId());  // 種別ID
            conDto.setUserId(userId);               // ユーザID

            // 検索条件に従って検索
            tblExpenseList = tblExpenseMapper.findByCondition(conDto);

            // 検索結果が0件の場合、削除を行う
            if (tblExpenseList.size() == 0) {

                // 削除メソッド呼び出し
                tblCategoryMapper.deleteById(input.getCategoryId());

                // 検索結果が1件以上ある場合
            } else {
                throw new CustomRuntimeException("");
            }

        } catch (CustomRuntimeException e) {

            throw new CustomRuntimeException("出費明細に登録されているため削除できません");

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 返却処理
        return output;
    }

    /**
     * 更新メソッド
     */
    public EXP112ServiceUpdateOut update(EXP112ServiceUpdateIn input) {

        // 初期化処理
        EXP112ServiceUpdateOut output = new EXP112ServiceUpdateOut();   // 出力項目
        TblCategoryEntity tblCategoryIn = new TblCategoryEntity();               // 入力項目

        // エンティティ設定
        tblCategoryIn.setCategoryId(input.getCategoryId());
        tblCategoryIn.setSortOrder(input.getSortOrder());

        try {

            // 更新処理実行
            tblCategoryMapper.updateSortOrderById(tblCategoryIn);

        } catch (Exception e) {
            throw new CustomRuntimeException("更新に失敗しました");
        }

        // 返却処理
        return output;
    }

}
