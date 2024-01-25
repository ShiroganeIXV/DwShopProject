package com.dwshop.admin.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordEncoderTest {
    @Test
    public void PasswordEncoderTest(){
        // given
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "dianawu95";

        // when
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // then
        System.out.println(encodedPassword);
        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
        assertThat(matches).isTrue();


    }
}
