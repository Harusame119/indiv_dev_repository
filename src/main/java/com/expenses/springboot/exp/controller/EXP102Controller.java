/**
 * 
 */
package com.expenses.springboot.exp.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.expenses.springboot.common.ExConstant;
import com.expenses.springboot.exp.dto.EXP102FormDto;
import com.expenses.springboot.exp.dto.EXP102ServiceFindIn;
import com.expenses.springboot.exp.dto.EXP102ServiceFindOut;
import com.expenses.springboot.exp.service.EXP102Service;

/**
 * 出費照会画面コントローラー
 */
@Controller
public class EXP102Controller {

	@Autowired
	EXP102Service exp102Service;

	/**
	 * 初期表示メソッド
	 */
	@RequestMapping(value="/EXP102_EV001" ,method=RequestMethod.GET)
	public String display(Model model) {

		// 遷移先画面名
		String dispNm = ExConstant.DISPNM_EXP102;

		// 初期表示処理
		EXP102FormDto formDto = exp102Service.display();

		// formを設定
		model.addAttribute("formDto", formDto);

		// 画面遷移
		return dispNm;

	}

	/**
	 * 出費照会メソッド
	 */
	@RequestMapping(value="/EXP102_EV002" ,method=RequestMethod.POST)
	public String find(@ModelAttribute("formDto")
			@RequestParam("storeId") Integer storeKey,
			@RequestParam("categoryId") Integer categoryKey,
			@RequestParam("payerId") Integer payerKey,
			@Validated EXP102FormDto formDto,
			BindingResult result,
			HttpSession session,
			Model model) {

		// 遷移先画面名
		String dispNm = ExConstant.DISPNM_EXP102;

		// 検索条件をセッションに格納
		session.setAttribute("conStoreId", storeKey);
		session.setAttribute("conCategoryId", categoryKey);
		session.setAttribute("conPayerId", payerKey);
		session.setAttribute("conFormDto", formDto);

		// 出費照会サービス検索出力
		EXP102ServiceFindOut exp102ServiceFindOut = new EXP102ServiceFindOut();

		if (!result.hasErrors()) {

			// 出費照会サービス検索入力
			EXP102ServiceFindIn exp102ServiceFindIn = new EXP102ServiceFindIn();

			// 出費照会サービス検索入力設定
			// 店舗ID
			exp102ServiceFindIn.setStoreId(storeKey);
			// 種別ID
			exp102ServiceFindIn.setCategoryId(categoryKey);
			// 支払者ID
			exp102ServiceFindIn.setPayerId(payerKey);
			// 開始日
			exp102ServiceFindIn.setStartDate(formDto.getStartYMDSearchCondition());
			// 終了日
			exp102ServiceFindIn.setEndDate(formDto.getEndYMDSearchCondition());
			// 備考
			exp102ServiceFindIn.setRemarks(formDto.getRemarksSearchCondition());

			// 検索処理メソッド呼び出し
			exp102ServiceFindOut = exp102Service.find(exp102ServiceFindIn);

			// 検索結果の設定
			formDto.setResultList(exp102ServiceFindOut.getResultList());
			formDto.setSumAmount(exp102ServiceFindOut.getSum());

		} else {

		}

		// 各種プルダウンを設定
		formDto.setPulStore(exp102ServiceFindOut.getStoreMap());
		formDto.setPulCategory(exp102ServiceFindOut.getCategoryMap());
		formDto.setPulPayer(exp102ServiceFindOut.getPayerMap());

		// プルダウンの初期選択項目を設定
		model.addAttribute("selectedStoreKey", storeKey);
		model.addAttribute("selectedCategoryKey", categoryKey);
		model.addAttribute("selectedPayerKey", payerKey);

		// formを設定
		model.addAttribute("formDto", formDto);

		// 画面遷移
		return dispNm;

	}

}
