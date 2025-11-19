/**
 * 
 */
package com.expenses.springboot.exp.controller;

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
import com.expenses.springboot.exp.dto.EXP101FormDto;
import com.expenses.springboot.exp.dto.EXP101ServiceRegisterIn;
import com.expenses.springboot.exp.service.EXP101Service;

/**
 * 出費登録画面コントローラー
 */
@Controller
public class EXP101Controller {

	@Autowired
	EXP101Service exp101Service;

	/**
	 * 初期表示メソッド
	 */
	@RequestMapping(value="/EXP101_EV001" ,method=RequestMethod.GET)
	public String diplay(Model model) {

		// 遷移先画面名
		String dispNm = ExConstant.DISPNM_EXP101;

		// 初期表示処理
		EXP101FormDto formDto = exp101Service.display();

		// formを設定
		model.addAttribute("formDto", formDto);

		// 画面遷移
		return dispNm;

	}

	/**
	 * 出費登録メソッド
	 */
	@RequestMapping(value="/EXP101_EV002" ,method=RequestMethod.POST)
	public String register(@ModelAttribute("formDto")
			@RequestParam("storeId") String storeKey,
			@RequestParam("categoryId") String categoryKey,
			@RequestParam("payerId") String payerKey,
			@Validated EXP101FormDto formDto,
			BindingResult result,
			Model model) {

		// 遷移先画面名
		String dispNm = ExConstant.DISPNM_EXP101;

		// 出費登録サービス登録入力
		EXP101ServiceRegisterIn input = new EXP101ServiceRegisterIn();

		// メッセージ用金額
		String amountMsg = formDto.getTextAmount();

		if (!result.hasErrors()) {

			// 出費登録サービス登録入力設定
			// 金額
			input.setAmount(formDto.getTextAmount());
			// 店舗ID
			input.setStoreId(Integer.valueOf(storeKey));
			// 種別ID
			input.setCategoryId(Integer.valueOf(categoryKey));
			// 支払者ID
			input.setPayerId(Integer.valueOf(payerKey));
			// 支払日
			input.setPaymentDate(formDto.getTextPaymentDate());
			// 分割フラグ
			input.setSplitFlg(formDto.getRadioSplitFlg());
			// 備考
			input.setRemarks(formDto.getTextRemarks());

			// 登録処理
			exp101Service.resister(input);

			// 支払日を取得
			String paymentDate = formDto.getTextPaymentDate();

			// 初期表示処理
			formDto = exp101Service.display();

			// 支払日は同じものを設定
			formDto.setTextPaymentDate(paymentDate);

			// 隠しメッセージ設定
			StringBuffer msg = new StringBuffer();
			msg.append("登録が完了しました");
			msg.append("：");
			msg.append(amountMsg);
			msg.append("円");
			formDto.setHdnMsg(msg.toString());

		} else {

			// 初期表示処理
			formDto = exp101Service.display();

		}

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
