package com.expenses.springboot.common.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.expenses.springboot.common.ExConstant;
import com.expenses.springboot.common.dto.CreateMapServiceOut;
import com.expenses.springboot.entity.TblCategoryEntity;
import com.expenses.springboot.entity.TblPayerEntity;
import com.expenses.springboot.entity.TblStoreEntity;
import com.expenses.springboot.repository.TblCategoryMapper;
import com.expenses.springboot.repository.TblPayerMapper;
import com.expenses.springboot.repository.TblStoreMapper;

/**
 * マップ作成サービス
 */
@Service
public class CreateMapService {

    @Autowired
    private TblStoreMapper storeMapper;

    @Autowired
    private TblCategoryMapper categoryMapper;

    @Autowired
    private TblPayerMapper payerMapper;
	/**
	 * マップ作成メソッド(DB)
	 */
	public CreateMapServiceOut createMapFromDB(int blankFlg, String... targetDB) {

		// 出力項目
		CreateMapServiceOut output = new CreateMapServiceOut();

		// ユーザ名取得
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();

		// 引数の配列の項目分繰り返す
		for (String target : targetDB) {

			// 店舗テーブルの場合
			if (ExConstant.TBL_STORE.equals(target)) {

				// 店舗リスト取得
				Iterable<TblStoreEntity> storeList = storeMapper.findByUserId(userId);
				Map<Integer, String> storeMap = new LinkedHashMap<>();

				// 空白フラグ＝"1(あり)"の場合
				if (blankFlg == 1) {
					storeMap.put(ExConstant.INT_0, ExConstant.STR_BLANK);
				}

				// 店舗MAP作成
				for (TblStoreEntity storeInfo : storeList) {
					storeMap.put(storeInfo.getStoreId(), storeInfo.getStoreName());
				}

				// 店舗マップを出力項目に設定
				output.setStoreMap(storeMap);

				// 種別テーブルの場合
			} else if (ExConstant.TBL_CATEGORY.equals(target)) {

				// 種別リスト取得
				Iterable<TblCategoryEntity> categoryList = categoryMapper.findByUserId(userId);
				Map<Integer, String> categoryMap = new LinkedHashMap<>();

				// 空白フラグ＝"1(あり)"の場合
				if (blankFlg == 1) {
					categoryMap.put(ExConstant.INT_0, ExConstant.STR_BLANK);
				}

				// 種別MAP作成
				for (TblCategoryEntity categoryInfo : categoryList) {
					categoryMap.put(categoryInfo.getCategoryId(), categoryInfo.getCategoryName());
				}

				// 種別マップを出力項目に設定
				output.setCategoryMap(categoryMap);

				// 支払者テーブルの場合
			} else if (ExConstant.TBL_PAYER.equals(target)) {

				// 支払者リスト取得
				Iterable<TblPayerEntity> payerList = payerMapper.findByUserId(userId);
				Map<Integer, String> payerMap = new LinkedHashMap<>();

				// 空白フラグ＝"1(あり)"の場合
				if (blankFlg == 1) {
					payerMap.put(ExConstant.INT_0, ExConstant.STR_BLANK);
				}

				// 支払者MAP作成
				for (TblPayerEntity payerInfo : payerList) {
					payerMap.put(payerInfo.getPayerId(), payerInfo.getPayerName());
				}

				// 支払者マップを出力項目に設定
				output.setPayerMap(payerMap);

			}
		}

		return output;
	}
}
