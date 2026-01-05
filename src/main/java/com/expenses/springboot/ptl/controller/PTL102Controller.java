/**
 * 
 */
package com.expenses.springboot.ptl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.expenses.springboot.common.ExConstant;

/**
 * サブメニュー(家計簿)画面コントローラー
 */
@Controller
public class PTL102Controller {

	@RequestMapping(value="/ptl102_EV001" ,method=RequestMethod.GET)
	public String diplay(Model model) {

		// 遷移先画面名
		String dispNm = ExConstant.DISPNM_PTL102;

		// 画面遷移
		return dispNm;

	}

}
