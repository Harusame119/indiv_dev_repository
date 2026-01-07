package com.expenses.springboot.exp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.expenses.springboot.common.ExConstant;
import com.expenses.springboot.entity.TblStoreEntity;
import com.expenses.springboot.exp.dto.EXP111ServiceDisplayOut;
import com.expenses.springboot.exp.dto.EXP111ServiceRegisterIn;
import com.expenses.springboot.repository.TblStoreMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 店舗管理サービス
 */
@Slf4j
@Service
@Transactional
public class EXP111Service {

    @Autowired
    private TblStoreMapper tblStoreMapper;

    /**
     * 初期表示情報検索メソッド
     */
    public EXP111ServiceDisplayOut display() {

        // 出力項目
        EXP111ServiceDisplayOut output = new EXP111ServiceDisplayOut();
        // 画面表示用リスト
        List<TblStoreEntity> tblStoreList = new ArrayList<>();

        try {

            // ユーザ名取得
            String userId = SecurityContextHolder.getContext().getAuthentication().getName();

            // ユーザ名を条件にして検索
            tblStoreList = tblStoreMapper.findByUserId(userId);

            // 検索結果を出力項目に設定
            output.setTblStoreList(tblStoreList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 返却処理
        return output;

    }

    /**
     * 店舗テーブル登録メソッド
     */
    public void register(EXP111ServiceRegisterIn input) {

        // Input用店舗テーブルエンティティ
        TblStoreEntity tblStoreIn = new TblStoreEntity();

        try {

            // ユーザ名取得
            String userId = SecurityContextHolder.getContext().getAuthentication().getName();

            // 登録項目設定
            // 店舗名
            tblStoreIn.setStoreName(input.getStoreName());
            // ユーザ名
            tblStoreIn.setUserId(userId);
            // 表示順
            tblStoreIn.setSortOrder(ExConstant.INT_DEFAULT_SORT_ORDER);

            // 登録処理実行
            tblStoreMapper.insert(tblStoreIn);

            log.info("登録を実行しました");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
