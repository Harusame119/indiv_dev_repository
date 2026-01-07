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

import com.expenses.springboot.common.ExConstant;
import com.expenses.springboot.exp.dto.EXP111FormDto;
import com.expenses.springboot.exp.dto.EXP111ServiceDisplayOut;
import com.expenses.springboot.exp.dto.EXP111ServiceRegisterIn;
import com.expenses.springboot.exp.service.EXP111Service;

/**
 * 店舗マスタ管理画面コントローラー
 */
@Controller
public class EXP111Controller {

    @Autowired
    EXP111Service exp111Service;

    /**
     * 初期表示メソッド
     */
    @RequestMapping(value = "/EXP111_EV001", method = RequestMethod.GET)
    public String display(Model model) {

        // 初期化処理
        String dispNm = ExConstant.DISPNM_EXP111;                                         // 遷移先画面名
        EXP111FormDto formDto = new EXP111FormDto();                                        // 画面DTO
        EXP111ServiceDisplayOut exp111ServiceDisplayOut = new EXP111ServiceDisplayOut();    // 店舗マスタ管理サービス表示出力

        // 初期表示処理
        exp111ServiceDisplayOut = exp111Service.display();

        // 検索結果を設定
        formDto.setTblStoreList(exp111ServiceDisplayOut.getTblStoreList());

        // formを設定
        model.addAttribute("formDto", formDto);

        // 画面遷移
        return dispNm;

    }

    /**
     * 店舗マスタ登録メソッド
     */
    @RequestMapping(value = "/EXP111_EV002", method = RequestMethod.POST)
    public String register(@Validated @ModelAttribute("formDto") EXP111FormDto formDto,
            BindingResult result,
            Model model) {

        // 初期化処理
        String dispNm = ExConstant.DISPNM_EXP111;                                         // 遷移先画面名
        EXP111ServiceRegisterIn exp111ServiceRegisterIn = new EXP111ServiceRegisterIn();    // 店舗マスタ管理サービス登録入力
        EXP111ServiceDisplayOut exp111ServiceDisplayOut = new EXP111ServiceDisplayOut();    // 店舗マスタ管理サービス表示出力

        // バリデーションチェックに問題無し
        if (!result.hasErrors()) {

            // 店舗マスタ登録サービス登録入力設定
            exp111ServiceRegisterIn.setStoreName(formDto.getStoreName());

            // 登録処理メソッド呼び出し
            exp111Service.register(exp111ServiceRegisterIn);

            // 初期表示処理
            exp111ServiceDisplayOut = exp111Service.display();

            // 検索結果を設定
            formDto.setTblStoreList(exp111ServiceDisplayOut.getTblStoreList());

            // 隠しメッセージ設定
            StringBuffer msg = new StringBuffer();
            msg.append("登録が完了しました");
            formDto.setHdnMsg(msg.toString());

        }

        // formを設定
        model.addAttribute("formDto", formDto);

        // 画面遷移
        return dispNm;

    }

    /**
     * 店舗マスタ更新メソッド
     */
    

}
