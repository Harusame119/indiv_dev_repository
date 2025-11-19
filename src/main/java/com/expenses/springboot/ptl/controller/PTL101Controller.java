/**
 * 
 */
package com.expenses.springboot.ptl.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * ポータル画面コントローラー
 */
@Controller
public class PTL101Controller {

	@RequestMapping(value="/ptl101" ,method=RequestMethod.GET)
	public String diplay(Model model, Authentication auth) {

		// 遷移先画面名
		String dispNm = "PTL101";

		// ログインユーザの情報設定
		model.addAttribute("userName", auth.getName());

		// 画面遷移
		return dispNm;

	}

}
