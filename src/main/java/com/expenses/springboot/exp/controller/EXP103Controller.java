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

import com.expenses.springboot.common.CustomRuntimeException;
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
    @RequestMapping(value = "/EXP103_EV001", method = RequestMethod.POST)
    public String display(@RequestParam("id") Integer id,
            HttpSession session,
            Model model) throws Exception {

        // 初期化処理
        String dispNm = ExConstant.DISPNM_EXP103;                             // 遷移先画面名
        EXP103ServiceFindOut exp103ServiceFindOut = new EXP103ServiceFindOut(); // 出費詳細サービス検索出力
        EXP103ServiceFindIn exp103ServiceFindIn = new EXP103ServiceFindIn();    // 出費詳細サービス検索入力
        EXP103FormDto formDto103 = new EXP103FormDto();                         // 出費詳細画面DTO
        EXP102FormDto formDto102 = new EXP102FormDto();                         // 出費照会画面DTO

        // 出費詳細サービス検索入力設定
        exp103ServiceFindIn.setExpenseId(id);

        try {

            // 初期表示処理
            exp103ServiceFindOut = exp103Service.display(exp103ServiceFindIn);

            // 出費詳細画面DTO設定メソッド呼び出し
            formDto103 = this.set103FormDto(exp103ServiceFindOut.getTblExpenseEntity());

            // プルダウン項目設定
            formDto103.setPulStore(exp103ServiceFindOut.getStoreMap());        // 店舗プルダウンメニュー
            formDto103.setPulCategory(exp103ServiceFindOut.getCategoryMap());  // 種別プルダウンメニュー
            formDto103.setPulPayer(exp103ServiceFindOut.getPayerMap());        // 支払者プルダウンメニュー

            // メッセージのクリア
            formDto103.setHdnMsg(null);

            // formを設定
            model.addAttribute("formDto", formDto103);

            // 例外が発生した場合、出費照会画面に戻る
        } catch (CustomRuntimeException e) {

            // 出費照会画面DTO設定メソッド呼び出し
            formDto102 = set102FormDto(session);

            // 遷移先画面設定費照会画面に設定
            dispNm = ExConstant.DISPNM_EXP102;

            // formを設定
            model.addAttribute("formDto", formDto102);

            // 画面表示用エラーメッセージ設定
            model.addAttribute("errMsg", e.getMessage());

        }

        // 画面遷移
        return dispNm;

    }

    /**
     * 出費照会（詳細画面へのリターン）メソッド
     */
    @RequestMapping(value = "/EXP103_EV002", method = RequestMethod.POST)
    public String returnToE102(HttpSession session, Model model) throws Exception {

        // 初期化処理
        String dispNm = ExConstant.DISPNM_EXP102;     // 遷移先画面名
        EXP102FormDto formDto = new EXP102FormDto();    // 画面DTO

        // 出費照会画面DTO設定メソッド
        formDto = set102FormDto(session);

        // formを設定
        model.addAttribute("formDto", formDto);

        // 画面遷移
        return dispNm;

    }

    /**
     * 削除メソッド
     */
    @RequestMapping(value = "/EXP103_EV003", method = RequestMethod.POST)
    public String delete(@Validated @ModelAttribute("formDto") EXP103FormDto formDto,
            HttpSession session,
            Model model) throws Exception {

        // 初期化処理
        String dispNm = ExConstant.DISPNM_EXP102;                                     // 遷移先画面名
        EXP103ServiceDeleteOut exp103ServiceDeleteOut = new EXP103ServiceDeleteOut();   // 出費詳細サービス削除出力
        EXP103ServiceDeleteIn exp103ServiceDeleteIn = new EXP103ServiceDeleteIn();      // 出費詳細サービス削除入力
        EXP102FormDto conFormDto = new EXP102FormDto();                                 // 画面DTO

        // 出費詳細サービス削除入力設定
        exp103ServiceDeleteIn.setExpenseId(Integer.parseInt((formDto.getHiddenId()))); // 出費ID

        // 削除処理メソッド呼び出し
        exp103ServiceDeleteOut = exp103Service.delete(exp103ServiceDeleteIn);

        // 出費照会画面DTO設定メソッド呼び出し
        conFormDto = set102FormDto(session);

        // 隠しメッセージの設定
        conFormDto.setHdnMsg("削除しました");

        // formを設定
        model.addAttribute("formDto", conFormDto);

        // 画面遷移
        return dispNm;
    }

    /**
     * 出費更新メソッド
     */
    @RequestMapping(value = "/EXP103_EV004", method = RequestMethod.POST)
    public String update(@Validated @ModelAttribute("formDto") EXP103FormDto formDto,
            BindingResult result,
            HttpSession session,
            Model model) {

        // 初期化処理
        String dispNm = ExConstant.DISPNM_EXP103;                                     // 遷移先画面名
        EXP103ServiceUpdateOut exp103ServiceUpdateOut = new EXP103ServiceUpdateOut();   // 出費詳細サービス更新出力
        EXP103ServiceUpdateIn exp103ServiceUpdateIn = new EXP103ServiceUpdateIn();      // 出費詳細サービス更新入力

        // バリデーションチェックに問題無し
        if (!result.hasErrors()) {

            // 出費詳細サービス更新入力設定
            exp103ServiceUpdateIn.setExpenseId(Integer.parseInt(formDto.getHiddenId()));   // 出費ID
            exp103ServiceUpdateIn.setAmount(Integer.parseInt(formDto.getTextAmount()));    // 金額
            exp103ServiceUpdateIn.setStoreId(formDto.getStoreId());                         // 店舗ID
            exp103ServiceUpdateIn.setCategoryId(formDto.getCategoryId());                   // 種別ID
            exp103ServiceUpdateIn.setPayerId(formDto.getPayerId());                         // 支払者ID
            exp103ServiceUpdateIn.setPaymentDate(formDto.getTextPaymentDate());             // 支払日
            exp103ServiceUpdateIn.setSplitFlg(formDto.getRadioSplitFlg());                  // 分割有無フラグ
            exp103ServiceUpdateIn.setRemarks(formDto.getTextRemarks());                     // 備考

            // 更新処理メソッド呼び出し
            exp103ServiceUpdateOut = exp103Service.update(exp103ServiceUpdateIn);

            // 画面DTO設定メソッド呼び出し
            formDto = this.set103FormDto(exp103ServiceUpdateOut.getTblExpenseEntity());

            // プルダウン項目設定
            formDto.setPulStore(exp103ServiceUpdateOut.getStoreMap());          // 店舗マップ
            formDto.setPulCategory(exp103ServiceUpdateOut.getCategoryMap());    // 種別マップ
            formDto.setPulPayer(exp103ServiceUpdateOut.getPayerMap());          // 支払者マップ

            // 隠しメッセージの設定
            formDto.setHdnMsg("更新しました");

        }

        // formを設定
        model.addAttribute("formDto", formDto);

        return dispNm;
    }

    /**
     * 出費詳細画面DTO設定メソッド
     */
    private EXP103FormDto set103FormDto(TblExpenseEntity entity) {

        // 初期化処理
        EXP103FormDto output = new EXP103FormDto(); // 画面DTO

        // 値の設定
        output.setTextAmount(String.valueOf(entity.getAmount()));                          // 金額
        output.setTextPaymentDate(new SimpleDateFormat(
                ExConstant.DATE_FORMAT_SPLIT_HYPHEN).format(entity.getPaymentDate()));  // 支払日
        output.setRadioSplitFlg(entity.getSplitFlg());                                      // 分割有無フラグ
        output.setTextRemarks(entity.getRemarks());                                         // 備考
        output.setHiddenId(String.valueOf(entity.getId()));                                // 出費ID
        output.setStoreId(entity.getStoreId());                                             // 店舗ID
        output.setCategoryId(entity.getCategoryId());                                       // 種別ID
        output.setPayerId(entity.getPayerId());                                             // 支払者ID

        return output;
    }

    /**
     * 出費照会画面DTO設定メソッド
     */
    private EXP102FormDto set102FormDto(HttpSession session) throws Exception {

        // 初期化処理
        EXP102FormDto output = new EXP102FormDto();                             // 画面DTO
        EXP102ServiceFindOut exp102ServiceFindOut = new EXP102ServiceFindOut(); // 出費照会サービス検索出力
        EXP102ServiceFindIn exp102ServiceFindIn = new EXP102ServiceFindIn();    // 出費照会サービス検索入力

        // 画面DTO
        output = (EXP102FormDto) session.getAttribute("conFormDto");

        // セッション切れの場合、例外スロー
        if (output == null) {
            throw new Exception("セッションの有効期限が切れました");
        }

        // 出費照会サービス検索入力設定
        exp102ServiceFindIn.setStoreId(output.getStoreId());                    // 店舗ID
        exp102ServiceFindIn.setCategoryId(output.getCategoryId());              // 種別ID
        exp102ServiceFindIn.setPayerId(output.getPayerId());                    // 支払者ID
        exp102ServiceFindIn.setStartDate(output.getStartYMDSearchCondition());  // 開始日
        exp102ServiceFindIn.setEndDate(output.getEndYMDSearchCondition());      // 終了日
        exp102ServiceFindIn.setRemarks(output.getRemarksSearchCondition());     // 備考

        // 検索処理メソッド呼び出し
        exp102ServiceFindOut = exp102Service.find(exp102ServiceFindIn);

        // 検索結果の設定
        output.setResultList(exp102ServiceFindOut.getResultList()); // 出費テーブル検索結果エンティティリスト
        output.setSumAmount(exp102ServiceFindOut.getSum());         // 合計金額

        // 各種プルダウンを設定
        output.setPulStore(exp102ServiceFindOut.getStoreMap());         // 店舗プルダウンメニュー
        output.setPulCategory(exp102ServiceFindOut.getCategoryMap());   // 種別プルダウンメニュー
        output.setPulPayer(exp102ServiceFindOut.getPayerMap());         // 支払者プルダウンメニュー

        // メッセージのクリア
        output.setHdnMsg(null);

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
