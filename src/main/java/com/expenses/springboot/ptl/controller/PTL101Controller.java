/**
 * 
 */
package com.expenses.springboot.ptl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * メインメニュー画面コントローラー
 */
@Controller
public class PTL101Controller {

	@RequestMapping(value="/ptl" ,method=RequestMethod.GET)
	public String diplay(Model model) {

		// 遷移先画面名
		String dispNm = "PTL101";

		// 画面遷移
		return dispNm;

	}

}
