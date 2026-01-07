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

import com.expenses.springboot.common.CustomRuntimeException;
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
    @RequestMapping(value = "/EXP102_EV001", method = RequestMethod.GET)
    public String display(Model model) {

        // 初期化処理
        String dispNm = ExConstant.DISPNM_EXP102; // 遷移先画面名

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
    @RequestMapping(value = "/EXP102_EV002", method = RequestMethod.POST)
    public String find(@Validated @ModelAttribute("formDto") EXP102FormDto formDto,
            BindingResult result,
            HttpSession session,
            Model model) {

        // 初期化処理
        String dispNm = ExConstant.DISPNM_EXP102;     // 遷移先画面名
        EXP102ServiceFindOut exp102ServiceFindOut
                = new EXP102ServiceFindOut();           // 出費照会サービス検索出力
        EXP102ServiceFindIn exp102ServiceFindIn
                = new EXP102ServiceFindIn();            // 出費照会サービス検索入力
        boolean successFlg = true;                      // 照会可否フラグ

        // 検索条件をセッションに格納
        session.setAttribute("conFormDto", formDto);

        try {

            // バリデーションチェックに問題無し
            if (!result.hasErrors()) {
    
                // 出費照会サービス検索入力設定
                exp102ServiceFindIn.setStoreId(formDto.getStoreId());                   // 店舗ID
                exp102ServiceFindIn.setCategoryId(formDto.getCategoryId());             // 種別ID
                exp102ServiceFindIn.setPayerId(formDto.getPayerId());                   // 支払者ID
                exp102ServiceFindIn.setStartDate(formDto.getStartYMDSearchCondition()); // 開始日
                exp102ServiceFindIn.setEndDate(formDto.getEndYMDSearchCondition());     // 終了日
                exp102ServiceFindIn.setRemarks(formDto.getRemarksSearchCondition());    // 備考

                // 検索処理メソッド呼び出し
                exp102ServiceFindOut = exp102Service.find(exp102ServiceFindIn);

                // 検索結果の設定
                formDto.setResultList(exp102ServiceFindOut.getResultList());    // 出費テーブルエンティティリスト
                formDto.setSumAmount(exp102ServiceFindOut.getSum());            // 合計金額

                // 各種プルダウンを設定
                formDto.setPulStore(exp102ServiceFindOut.getStoreMap());        // 店舗プルダウンメニュー
                formDto.setPulCategory(exp102ServiceFindOut.getCategoryMap());  // 種別プルダウンメニュー
                formDto.setPulPayer(exp102ServiceFindOut.getPayerMap());        // 支払者プルダウンメニュー

                // バリデーションチェックに問題無し
            } else {

                // 登録可否フラグをfalseに設定
                successFlg = false;
                // 画面表示用エラーメッセージ設定
                model.addAttribute("errMsg", "入力エラー");

            }

            // 例外が発生した場合
        } catch (CustomRuntimeException e) {

            // 登録可否フラグをfalseに設定
            successFlg = false;
            // 画面表示用エラーメッセージ設定
            model.addAttribute("errMsg", e.getMessage());

        }

        // 照会失敗の場合、初期表示
        if (!successFlg) {

            // 初期表示処理呼び出し
            formDto = exp102Service.display();

            // 照会成功の場合
        } else {
            // 何もしない
        }

        // formを設定
        model.addAttribute("formDto", formDto);

        // 画面遷移
        return dispNm;

    }

}
