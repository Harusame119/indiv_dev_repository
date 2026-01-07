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

import com.expenses.springboot.common.CustomRuntimeException;
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
    @RequestMapping(value = "/EXP101_EV001", method = RequestMethod.GET)
    public String diplay(Model model) {

        // 初期化処理
        String dispNm = ExConstant.DISPNM_EXP101;   // 遷移先画面名

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
    @RequestMapping(value = "/EXP101_EV002", method = RequestMethod.POST)
    public String register(@Validated @ModelAttribute("formDto") EXP101FormDto formDto,
            BindingResult result,
            Model model) {

        // 初期化処理
        String dispNm = ExConstant.DISPNM_EXP101;         // 遷移先画面名
        EXP101ServiceRegisterIn exp101ServiceRegisterIn
                = new EXP101ServiceRegisterIn();            // 出費登録サービス登録入力
        boolean successFlg = true;                          // 登録可否フラグ

        // 保持しておく情報を取得
        String textAmount = formDto.getTextAmount();        // 金額
        int storeId = formDto.getStoreId();                 // 店舗ID
        int categoryId = formDto.getCategoryId();           // 種別ID
        int payerId = formDto.getPayerId();                 // 支払者ID
        String paymentDate = formDto.getTextPaymentDate();  // 支払日
        String radioSplitFlg = formDto.getRadioSplitFlg();  // 分割フラグ
        String textRemarks = formDto.getTextRemarks();      // 備考 

        try {

            // バリデーションチェックに問題無し
            if (!result.hasErrors()) {

                // 出費登録サービス登録入力設定
                exp101ServiceRegisterIn.setAmount(formDto.getTextAmount());             // 金額
                exp101ServiceRegisterIn.setStoreId(formDto.getStoreId());               // 店舗ID
                exp101ServiceRegisterIn.setCategoryId(formDto.getCategoryId());         // 種別ID
                exp101ServiceRegisterIn.setPayerId(formDto.getPayerId());               // 支払者ID
                exp101ServiceRegisterIn.setPaymentDate(formDto.getTextPaymentDate());   // 支払日
                exp101ServiceRegisterIn.setSplitFlg(formDto.getRadioSplitFlg());        // 分割フラグ
                exp101ServiceRegisterIn.setRemarks(formDto.getTextRemarks());           // 備考

                // 登録処理
                exp101Service.register(exp101ServiceRegisterIn);

                // バリデーションチェックに問題有り
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

        // 初期表示処理
        formDto = exp101Service.display();

        // 保持しておく情報は同じものを設定
        formDto.setStoreId(storeId);                // 店舗ID
        formDto.setCategoryId(categoryId);          // 種別ID
        formDto.setPayerId(payerId);                // 支払者ID
        formDto.setTextPaymentDate(paymentDate);    // 支払日

        // 登録成功の場合、メッセージを設定、金額はクリア
        if (successFlg) {

            // 隠しメッセージ設定
            StringBuffer msg = new StringBuffer();
            msg.append("登録が完了しました");
            msg.append("：");
            msg.append(textAmount);
            msg.append("円");
            formDto.setHdnMsg(msg.toString());

            // 登録失敗の場合、金額、分割フラグ、備考もクリアしない
        } else {

            // 値を設定
            formDto.setTextAmount(textAmount);          // 金額
            formDto.setRadioSplitFlg(radioSplitFlg);    // 分割フラグ
            formDto.setTextRemarks(textRemarks);        // 備考

        }

        // formを設定
        model.addAttribute("formDto", formDto);

        // 画面遷移
        return dispNm;

    }

}
