/**
 * 
 */
package com.expenses.springboot.rpt.controller;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.expenses.springboot.common.CreateCommonUtility;
import com.expenses.springboot.common.ExConstant;
import com.expenses.springboot.rpt.dto.RPT101FormDto;
import com.expenses.springboot.rpt.dto.RPT101ServiceTotallingIn;
import com.expenses.springboot.rpt.dto.RPT101ServiceTotallingOut;
import com.expenses.springboot.rpt.service.RPT101Service;

/**
 * 帳票(月次)出力画面コントローラー
 */
@Controller
public class RPT101Controller {

	@Autowired
	RPT101Service rpt101Service;

	/**
	 * 初期表示メソッド
	 */
	@RequestMapping(value="/RPT101_EV001" ,method=RequestMethod.GET)
	public String display(Model model, Authentication auth) {

		// 遷移先画面名
		String dispNm = ExConstant.DISPNM_RPT101;

		// 初期表示処理
		RPT101FormDto formDto = new RPT101FormDto();

		// 対象年プルダウンメニュー
		formDto.setPulTargetYear(CreateCommonUtility.createYearMap());

		// 対象月プルダウンメニュー
		formDto.setPulTargetMonth(CreateCommonUtility.createMonthMap());

		// 現在年月取得
		Calendar cal = Calendar.getInstance();
		String currentYear = String.valueOf(cal.get(Calendar.YEAR));
		String currentMonth = String.valueOf(cal.get(Calendar.MONTH) + 1);

		// プルダウンの初期選択項目を設定
		model.addAttribute("selectedYearKey", currentYear);
		model.addAttribute("selectedMonthKey", currentMonth);

		// formを設定
		model.addAttribute("formDto", formDto);

		// ログインユーザの情報設定
		model.addAttribute("userName", auth.getName());

		// 画面遷移
		return dispNm;

	}

	/**
	 * 帳票情報集計メソッド
	 */
	@RequestMapping(value="/RPT101_EV002" ,method=RequestMethod.POST)
	public String find(@ModelAttribute("formDto")
			@RequestParam("targetYear") String targetYear,
			@RequestParam("targetMonth") String targetMonth,
			@Validated RPT101FormDto formDto,
			BindingResult result,
			Model model,
			Authentication auth) {

		// 初期化処理
		// 遷移先画面名
		String dispNm = ExConstant.DISPNM_RPT101;

		// 帳票(月次)出力サービス集計出力
		RPT101ServiceTotallingOut rpt101ServiceTotallingOut = new RPT101ServiceTotallingOut();

		if (!result.hasErrors()) {

			// 帳票(月次)出力サービス集計入力
			RPT101ServiceTotallingIn rpt101ServiceTotallingIn = new RPT101ServiceTotallingIn();

			// 帳票(月次)出力サービス集計入力設定
			// 対象年
			rpt101ServiceTotallingIn.setTargetYear(targetYear);
			// 対象月
			rpt101ServiceTotallingIn.setTargetMonth(targetMonth);
			// 支払者１ ※固定値で"100"を設定
			rpt101ServiceTotallingIn.setPayerId1(100);
			// 支払者２ ※固定値で"101"を設定
			rpt101ServiceTotallingIn.setPayerId2(101);

			// 集計処理メソッド呼び出し
			rpt101ServiceTotallingOut = rpt101Service.totalling(rpt101ServiceTotallingIn);

			formDto.setResultEntity(rpt101ServiceTotallingOut.getResultEntity());

		} else {

		}

		// 対象年プルダウンメニュー
		formDto.setPulTargetYear(CreateCommonUtility.createYearMap());

		// 対象月プルダウンメニュー
		formDto.setPulTargetMonth(CreateCommonUtility.createMonthMap());

		// 検索結果が０件の場合、メッセージ表示
		if (formDto.getResultEntity() == null) {
			formDto.setHdnMsg("集計対象が０件でした");
		} else {
			formDto.setHdnMsg(null);
		}

		// プルダウンの初期選択項目を設定
		model.addAttribute("selectedYearKey", targetYear);
		model.addAttribute("selectedMonthKey", targetMonth);

		// formを設定
		model.addAttribute("formDto", formDto);

		// ログインユーザの情報設定
		model.addAttribute("userName", auth.getName());

		// 画面遷移
		return dispNm;

	}

}
