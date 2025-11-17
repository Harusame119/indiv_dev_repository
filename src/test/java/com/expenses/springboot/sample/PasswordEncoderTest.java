package com.expenses.springboot.sample;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderTest {

    @Test
    void generatePasswordForTestUser() {
        // BCrypt のコスト値（ストレッチ回数）を指定。デフォルトは 10
        PasswordEncoder encoder = new BCryptPasswordEncoder(10);

        // 平文パスワード（テスト用）
        String rawPassword = "password";

        // ハッシュ化
        String encodedPassword = encoder.encode(rawPassword);

        // コンソールに出力 → この文字列を DB に保存
        System.out.println("Encoded password: " + encodedPassword);

        // 照合テスト（確認用）
        boolean matches = encoder.matches(rawPassword, encodedPassword);
        System.out.println("Password matches? " + matches);
    }
}

