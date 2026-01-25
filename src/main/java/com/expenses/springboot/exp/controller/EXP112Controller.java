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
import com.expenses.springboot.exp.dto.EXP112FormDto;
import com.expenses.springboot.exp.dto.EXP112ServiceDeleteIn;
import com.expenses.springboot.exp.dto.EXP112ServiceDeleteOut;
import com.expenses.springboot.exp.dto.EXP112ServiceDisplayOut;
import com.expenses.springboot.exp.dto.EXP112ServiceRegisterIn;
import com.expenses.springboot.exp.dto.EXP112ServiceUpdateIn;
import com.expenses.springboot.exp.dto.EXP112ServiceUpdateOut;
import com.expenses.springboot.exp.service.EXP112Service;

/**
 * 種別マスタ管理画面コントローラー
 */
@Controller
public class EXP112Controller {

    @Autowired
    EXP112Service exp112Service;

    /**
     * 初期表示メソッド
     */
    @RequestMapping(value = "/EXP112_EV001", method = RequestMethod.GET)
    public String display(Model model) {

        // 初期化処理
        String dispNm = ExConstant.DISPNM_EXP112;                                         // 遷移先画面名
        EXP112FormDto formDto = new EXP112FormDto();                                        // 画面DTO
        EXP112ServiceDisplayOut exp112ServiceDisplayOut = new EXP112ServiceDisplayOut();    // 種別マスタ管理サービス表示出力

        // 初期表示処理
        exp112ServiceDisplayOut = exp112Service.display();

        // 検索結果を設定
        formDto.setTblCategoryList(exp112ServiceDisplayOut.getTblCategoryList());

        // formを設定
        model.addAttribute("formDto", formDto);

        // 画面遷移
        return dispNm;

    }

    /**
     * 種別マスタ登録メソッド
     */
    @RequestMapping(value = "/EXP112_EV002", method = RequestMethod.POST)
    public String register(@Validated @ModelAttribute("formDto") EXP112FormDto formDto,
            BindingResult result,
            Model model) {

        // 初期化処理
        String dispNm = ExConstant.DISPNM_EXP112;                                         // 遷移先画面名
        EXP112ServiceRegisterIn exp112ServiceRegisterIn = new EXP112ServiceRegisterIn();    // 種別マスタ管理サービス登録入力
        EXP112ServiceDisplayOut exp112ServiceDisplayOut = new EXP112ServiceDisplayOut();    // 種別マスタ管理サービス表示出力

        // バリデーションチェックに問題無し
        if (!result.hasErrors()) {

            // 種別マスタ登録サービス登録入力設定
            exp112ServiceRegisterIn.setCategoryName(formDto.getCategoryName());

            // 登録処理メソッド呼び出し
            exp112Service.register(exp112ServiceRegisterIn);

            // 初期表示処理
            exp112ServiceDisplayOut = exp112Service.display();

            // 検索結果を設定
            formDto.setTblCategoryList(exp112ServiceDisplayOut.getTblCategoryList());

            // 種別名をクリア
            formDto.setCategoryName(ExConstant.STR_BLANK);

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
     * 種別マスタ削除メソッド
     */
    @RequestMapping(value = "/EXP112_EV003", method = RequestMethod.POST)
    public String delete(@ModelAttribute("formDto") EXP112FormDto formDto,
            @RequestParam("categoryId") Integer categoryId,
            Model model) throws Exception {

        // 初期化処理
        String dispNm = ExConstant.DISPNM_EXP112;                                         // 遷移先画面名
        EXP112ServiceDeleteOut exp112ServiceDeleteOut = new EXP112ServiceDeleteOut();       // 種別マスタ管理サービス削除出力
        EXP112ServiceDeleteIn exp112ServiceDeleteIn = new EXP112ServiceDeleteIn();          // 種別マスタ管理サービス削除入力
        EXP112ServiceDisplayOut exp112ServiceDisplayOut = new EXP112ServiceDisplayOut();    // 種別マスタ管理サービス表示出力

        // 種別マスタ管理サービス削除入力
        exp112ServiceDeleteIn.setCategoryId(categoryId);

        try {

            // 削除処理メソッド呼び出し
            exp112ServiceDeleteOut = exp112Service.delete(exp112ServiceDeleteIn);

            // 隠しメッセージ設定
            formDto.setHdnMsg("削除しました");

            // 例外発生した場合
        } catch (CustomRuntimeException e) {

            // 画面表示用エラーメッセージ設定
            model.addAttribute("errMsg", e.getMessage());

        }

        // 初期表示処理
        exp112ServiceDisplayOut = exp112Service.display();

        // 検索結果を設定
        formDto.setTblCategoryList(exp112ServiceDisplayOut.getTblCategoryList());     // 検索結果

        // formを設定
        model.addAttribute("formDto", formDto);

        // 画面遷移
        return dispNm;

    }

    /**
     * 種別マスタ更新メソッド
     */
    @RequestMapping(value = "/EXP112_EV004", method = RequestMethod.POST)
    public String update(@ModelAttribute("formDto") EXP112FormDto formDto,
            @RequestParam("categoryId") Integer categoryId,
            @RequestParam("sortOrder") Integer sortOrder,
            Model model) throws Exception {

        // 初期化処理
        String dispNm = ExConstant.DISPNM_EXP112;                                         // 遷移先画面名
        EXP112ServiceUpdateOut exp112ServiceUpdateOut = new EXP112ServiceUpdateOut();       // 種別マスタ管理サービス更新出力
        EXP112ServiceUpdateIn exp112ServiceUpdateIn = new EXP112ServiceUpdateIn();          // 種別マスタ管理サービス更新入力
        EXP112ServiceDisplayOut exp112ServiceDisplayOut = new EXP112ServiceDisplayOut();    // 種別マスタ管理サービス表示出力

        // 種別マスタ管理サービス更新入力設定
        exp112ServiceUpdateIn.setCategoryId(categoryId);    // 種別ID
        exp112ServiceUpdateIn.setSortOrder(sortOrder);      // 表示順

        try {

            // 更新処理メソッド呼び出し
            exp112Service.update(exp112ServiceUpdateIn);

            // 隠しメッセージの設定
            formDto.setHdnMsg("更新しました");

        } catch (CustomRuntimeException e) {

            // 画面表示用エラーメッセージ設定
            model.addAttribute("errMsg", e.getMessage());

        }

        // 初期表示処理
        exp112ServiceDisplayOut = exp112Service.display();

        // 検索結果を設定
        formDto.setTblCategoryList(exp112ServiceDisplayOut.getTblCategoryList());     // 検索結果

        // formを設定
        model.addAttribute("formDto", formDto);

        // 画面遷移
        return dispNm;

    }

}
