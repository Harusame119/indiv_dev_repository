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

import com.expenses.springboot.common.CustomRuntimeException;
import com.expenses.springboot.common.ExConstant;
import com.expenses.springboot.exp.dto.EXP111FormDto;
import com.expenses.springboot.exp.dto.EXP111ServiceDeleteIn;
import com.expenses.springboot.exp.dto.EXP111ServiceDeleteOut;
import com.expenses.springboot.exp.dto.EXP111ServiceDisplayOut;
import com.expenses.springboot.exp.dto.EXP111ServiceRegisterIn;
import com.expenses.springboot.exp.dto.EXP111ServiceUpdateIn;
import com.expenses.springboot.exp.dto.EXP111ServiceUpdateOut;
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

            // 店舗名をクリア
            formDto.setStoreName(ExConstant.STR_BLANK);

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
     * 店舗マスタ削除メソッド
     */
    @RequestMapping(value = "/EXP111_EV003", method = RequestMethod.POST)
    public String delete(@ModelAttribute("formDto") EXP111FormDto formDto,
            @RequestParam("storeId") Integer storeId,
            Model model) throws Exception {

        // 初期化処理
        String dispNm = ExConstant.DISPNM_EXP111;                                         // 遷移先画面名
        EXP111ServiceDeleteOut exp111ServiceDeleteOut = new EXP111ServiceDeleteOut();       // 店舗マスタ管理サービス削除出力
        EXP111ServiceDeleteIn exp111ServiceDeleteIn = new EXP111ServiceDeleteIn();          // 店舗マスタ管理サービス削除入力
        EXP111ServiceDisplayOut exp111ServiceDisplayOut = new EXP111ServiceDisplayOut();    // 店舗マスタ管理サービス表示出力

        // 店舗マスタ管理サービス削除入力
        exp111ServiceDeleteIn.setStoreId(storeId);

        try {

            // 削除処理メソッド呼び出し
            exp111ServiceDeleteOut = exp111Service.delete(exp111ServiceDeleteIn);

            // 隠しメッセージ設定
            formDto.setHdnMsg("削除しました");

            // 例外発生した場合
        } catch (CustomRuntimeException e) {

            // 画面表示用エラーメッセージ設定
            model.addAttribute("errMsg", e.getMessage());

        }

        // 初期表示処理
        exp111ServiceDisplayOut = exp111Service.display();

        // 検索結果を設定
        formDto.setTblStoreList(exp111ServiceDisplayOut.getTblStoreList());     // 検索結果

        // formを設定
        model.addAttribute("formDto", formDto);

        // 画面遷移
        return dispNm;

    }

    /**
     * 店舗マスタ更新メソッド
     */
    @RequestMapping(value = "/EXP111_EV004", method = RequestMethod.POST)
    public String update(@ModelAttribute("formDto") EXP111FormDto formDto,
            @RequestParam("storeId") Integer storeId,
            @RequestParam("sortOrder") Integer sortOrder,
            Model model) throws Exception {

        // 初期化処理
        String dispNm = ExConstant.DISPNM_EXP111;                                         // 遷移先画面名
        EXP111ServiceUpdateOut exp111ServiceUpdateOut = new EXP111ServiceUpdateOut();       // 店舗マスタ管理サービス更新出力
        EXP111ServiceUpdateIn exp111ServiceUpdateIn = new EXP111ServiceUpdateIn();          // 店舗マスタ管理サービス更新入力
        EXP111ServiceDisplayOut exp111ServiceDisplayOut = new EXP111ServiceDisplayOut();    // 店舗マスタ管理サービス表示出力

        // 店舗マスタ管理サービス更新入力設定
        exp111ServiceUpdateIn.setStoreId(storeId);      // 店舗ID
        exp111ServiceUpdateIn.setSortOrder(sortOrder);  // 表示順

        try {

            // 更新処理メソッド呼び出し
            exp111Service.update(exp111ServiceUpdateIn);

            // 隠しメッセージの設定
            formDto.setHdnMsg("更新しました");

        } catch (CustomRuntimeException e) {

            // 画面表示用エラーメッセージ設定
            model.addAttribute("errMsg", e.getMessage());

        }

        // 初期表示処理
        exp111ServiceDisplayOut = exp111Service.display();

        // 検索結果を設定
        formDto.setTblStoreList(exp111ServiceDisplayOut.getTblStoreList());     // 検索結果

        // formを設定
        model.addAttribute("formDto", formDto);

        // 画面遷移
        return dispNm;

    }

}
