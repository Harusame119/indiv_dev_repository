/**
 * 
 */
package com.expenses.springboot.exp.controller;

import java.text.SimpleDateFormat;

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
import com.expenses.springboot.entity.TblExpenseEntity;
import com.expenses.springboot.exp.dto.EXP102FormDto;
import com.expenses.springboot.exp.dto.EXP102ServiceFindIn;
import com.expenses.springboot.exp.dto.EXP102ServiceFindOut;
import com.expenses.springboot.exp.dto.EXP103FormDto;
import com.expenses.springboot.exp.dto.EXP103ServiceDeleteIn;
import com.expenses.springboot.exp.dto.EXP103ServiceDeleteOut;
import com.expenses.springboot.exp.dto.EXP103ServiceFindIn;
import com.expenses.springboot.exp.dto.EXP103ServiceFindOut;
import com.expenses.springboot.exp.dto.EXP103ServiceUpdateIn;
import com.expenses.springboot.exp.dto.EXP103ServiceUpdateOut;
import com.expenses.springboot.exp.service.EXP102Service;
import com.expenses.springboot.exp.service.EXP103Service;

/**
 * 出費詳細画面コントローラー
 */
@Controller
public class EXP103Controller {

	@Autowired
	EXP102Service exp102Service;

	@Autowired
	EXP103Service exp103Service;

	/**
	 * 初期表示メソッド
	 */
	@RequestMapping(value="/EXP103_EV001" ,method=RequestMethod.POST)
	public String display(@RequestParam("id") Integer id,
			Model model) {

		// 遷移先画面名
		String dispNm = ExConstant.DISPNM_EXP103;

		// 出費詳細サービス検索出力
		EXP103ServiceFindOut exp103ServiceFindOut = new EXP103ServiceFindOut();

		// 出費詳細サービス検索入力
		EXP103ServiceFindIn exp103ServiceFindIn = new EXP103ServiceFindIn();

		// 出費詳細サービス検索入力設定
		exp103ServiceFindIn.setExpenseId(id);

		// 初期表示処理
		exp103ServiceFindOut = exp103Service.display(exp103ServiceFindIn);

		// 画面DTO
		EXP103FormDto formDto = this.setFormDto(exp103ServiceFindOut);

		// プルダウンの初期選択項目を設定
		model.addAttribute("selectedStoreKey", exp103ServiceFindOut.getTblExpenseEntity().getStoreId());
		model.addAttribute("selectedCategoryKey", exp103ServiceFindOut.getTblExpenseEntity().getCategoryId());
		model.addAttribute("selectedPayerKey", exp103ServiceFindOut.getTblExpenseEntity().getPayerId());

		// メッセージのクリア
		formDto.setHdnMsg(null);

		// 更新前情報の設定（更新時比較用）　→エンティティそのままだと持てなそう
		model.addAttribute("formDtoBefUpd", formDto);

		// formを設定
		model.addAttribute("formDto", formDto);

		// 画面遷移
		return dispNm;

	}

	/**
	 * 出費照会（詳細画面へのリターン）メソッド
	 */
	@RequestMapping(value="/EXP103_EV002" ,method=RequestMethod.POST)
	public String returnToE102(HttpSession session, Model model) throws Exception {

		// 遷移先画面名
		String dispNm = ExConstant.DISPNM_EXP102;

		// 画面DTO
		EXP102FormDto formDto = (EXP102FormDto) session.getAttribute("conFormDto");

		if (formDto == null) {
			throw new Exception("セッションの有効期限が切れました");
		}

		// 出費照会サービス検索出力
		EXP102ServiceFindOut exp102ServiceFindOut = new EXP102ServiceFindOut();

		// 出費照会サービス検索入力
		EXP102ServiceFindIn exp102ServiceFindIn = new EXP102ServiceFindIn();

		// 出費照会サービス検索入力設定
		// 店舗ID
		exp102ServiceFindIn.setStoreId(formDto.getStoreId());
		// 種別ID
		exp102ServiceFindIn.setCategoryId(formDto.getCategoryId());
		// 支払者ID
		exp102ServiceFindIn.setPayerId(formDto.getPayerId());
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

		// 各種プルダウンを設定
		formDto.setPulStore(exp102ServiceFindOut.getStoreMap());
		formDto.setPulCategory(exp102ServiceFindOut.getCategoryMap());
		formDto.setPulPayer(exp102ServiceFindOut.getPayerMap());

		// プルダウンの初期選択項目を設定
		model.addAttribute("selectedStoreKey", (Integer) session.getAttribute("conStoreId"));
		model.addAttribute("selectedCategoryKey", (Integer) session.getAttribute("conCategoryId"));
		model.addAttribute("selectedPayerKey", (String) session.getAttribute("conRemarks"));

		// メッセージのクリア
		formDto.setHdnMsg(null);

		// formを設定
		model.addAttribute("formDto", formDto);

		// 画面遷移
		return dispNm;

	}

	/**
	 * 削除メソッド
	 */
	@RequestMapping(value="/EXP103_EV003" ,method=RequestMethod.POST)
	public String delete(@ModelAttribute("formDto")
			@Validated EXP103FormDto formDto,
			HttpSession session,
			Model model) throws Exception {

		// 遷移先画面名
		String dispNm = ExConstant.DISPNM_EXP102;

		// 出費詳細サービス削除出力
		EXP103ServiceDeleteOut exp103ServiceDeleteOut = new EXP103ServiceDeleteOut();

		// 出費詳細サービス削除入力
		EXP103ServiceDeleteIn exp103ServiceDeleteIn = new EXP103ServiceDeleteIn();

		// 出費詳細サービス削除入力設定
		// 出費ID
		exp103ServiceDeleteIn.setExpenseId(Integer.parseInt((formDto.getHiddenId())));

		// 削除処理メソッド呼び出し
		exp103ServiceDeleteOut = exp103Service.delete(exp103ServiceDeleteIn);

		// 画面DTO(検索条件)
		EXP102FormDto conFormDto = (EXP102FormDto) session.getAttribute("conFormDto");

		if (conFormDto == null) {
			throw new Exception("セッションの有効期限が切れました");
		}

		// 出費照会サービス検索出力
		EXP102ServiceFindOut exp102ServiceFindOut = new EXP102ServiceFindOut();

		// 出費照会サービス検索入力
		EXP102ServiceFindIn exp102ServiceFindIn = new EXP102ServiceFindIn();

		// 出費照会サービス検索入力設定
		// 店舗ID
		exp102ServiceFindIn.setStoreId(conFormDto.getStoreId());
		// 種別ID
		exp102ServiceFindIn.setCategoryId(conFormDto.getCategoryId());
		// 支払者ID
		exp102ServiceFindIn.setPayerId(conFormDto.getPayerId());
		// 開始日
		exp102ServiceFindIn.setStartDate(conFormDto.getStartYMDSearchCondition());
		// 終了日
		exp102ServiceFindIn.setEndDate(conFormDto.getEndYMDSearchCondition());
		// 備考
		exp102ServiceFindIn.setRemarks(conFormDto.getRemarksSearchCondition());

		// 検索処理メソッド呼び出し
		exp102ServiceFindOut = exp102Service.find(exp102ServiceFindIn);

		// 検索結果の設定
		conFormDto.setResultList(exp102ServiceFindOut.getResultList());
		conFormDto.setSumAmount(exp102ServiceFindOut.getSum());

		// 隠しメッセージの設定
		conFormDto.setHdnMsg("削除しました");

		// 各種プルダウンを設定
		conFormDto.setPulStore(exp102ServiceFindOut.getStoreMap());
		conFormDto.setPulCategory(exp102ServiceFindOut.getCategoryMap());
		conFormDto.setPulPayer(exp102ServiceFindOut.getPayerMap());

		// formを設定
		model.addAttribute("formDto", conFormDto);

		// 画面遷移
		return dispNm;
	}

	/**
	 * 出費更新メソッド
	 */
	@RequestMapping(value="/EXP103_EV004" ,method=RequestMethod.POST)
	public String update(@ModelAttribute("formDto")
			@RequestParam("formDtoBefUpd") EXP103FormDto befUpd,
			@RequestParam("storeId") String storeKey,
			@RequestParam("categoryId") String categoryKey,
			@RequestParam("payerId") String payerKey,
			@RequestParam("selectedStoreKey") String storeKeyBef,
			@RequestParam("selectedCategoryKey") String storeCategoryBef,
			@RequestParam("selectedPayerKey") String storePayerBef,
			@Validated EXP103FormDto formDto,
			BindingResult result,
			Model model) {

		// 遷移先画面名
		String dispNm = ExConstant.DISPNM_EXP103;

		// 出費詳細サービス更新出力
		EXP103ServiceUpdateOut exp103ServiceUpdateOut = new EXP103ServiceUpdateOut();

		// 出費詳細サービス更新入力
		EXP103ServiceUpdateIn exp103ServiceUpdateIn = new EXP103ServiceUpdateIn();

		if (checkChange()) {
			
		} else {
			
		}

		// プルダウンの初期選択項目を設定
		model.addAttribute("selectedStoreKey", storeKey);
		model.addAttribute("selectedCategoryKey", categoryKey);
		model.addAttribute("selectedPayerKey", payerKey);

		// formを設定
		model.addAttribute("formDto", formDto);

		return dispNm;
	}

	/**
	 * 画面DTO設定メソッド
	 */
	private EXP103FormDto setFormDto(EXP103ServiceFindOut input) {

		// 画面DTO
		EXP103FormDto output = new EXP103FormDto();

		// 出費テーブルエンティティ
		TblExpenseEntity entity = input.getTblExpenseEntity();

		// 値の設定
		output.setTextAmount(String.valueOf(entity.getAmount()));
		output.setTextPaymentDate(new SimpleDateFormat(
				ExConstant.DATE_FORMAT_SPLIT_HYPHEN).format(entity.getPaymentDate()));
		output.setRadioSplitFlg(entity.getSplitFlg());
		output.setTextRemarks(entity.getRemarks());
		output.setHiddenId(String.valueOf(entity.getId()));
		output.setPulStore(input.getStoreMap());
		output.setPulCategory(input.getCategoryMap());
		output.setPulPayer(input.getPayerMap());

		return output;
	}

	/**
	 * 変更有無判定メソッド
	 * true  :変更有り
	 * false :変更無し
	 */
	private boolean checkChange() {

		boolean changeFlg = false;

		return changeFlg;
	}

}
