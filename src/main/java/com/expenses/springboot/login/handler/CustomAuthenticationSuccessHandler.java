package com.expenses.springboot.login.handler;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        HttpSession session = request.getSession();

        // principalからユーザ情報を取得
        Object principal = authentication.getPrincipal();
        if (principal instanceof org.springframework.security.core.userdetails.User user) {
            session.setAttribute("userName", user.getUsername());
        }

        // ログイン後の遷移先
        response.sendRedirect("/ptl101");
    }
}

