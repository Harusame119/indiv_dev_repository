package com.expenses.springboot.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.expenses.springboot.entity.TblUserEntity;
import com.expenses.springboot.login.repository.TblUserMapper;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private TblUserMapper userMapper;

//    @Autowired
//    //テスト用定義
//    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TblUserEntity user = userMapper.findByUserId(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

//        // テスト用照合確認用ログ
//        String rawPassword = "password"; // テスト用に入力値を仮定
//        boolean matches = passwordEncoder.matches(rawPassword, user.getUserPass());
//        System.out.println("入力値: " + rawPassword);
//        System.out.println("DBハッシュ: " + user.getUserPass());
//        System.out.println("照合結果: " + matches);

        return org.springframework.security.core.userdetails.User
            .withUsername(user.getUserId())
            .password(user.getUserPass())
            .roles(user.getUserRole())
            .disabled(user.isDeleteFlg())
            .build();
    }
}