package com.practice.restfulwebservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration // 해당 어노테이션이 등록되어 있는 클래스들은 스프링 부트가 기동하면서 메모리에 설정 정보와 같이 로딩하게 된다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // WebSecurityConfigurerAdapter : Security 와 관련되어 있는 Configuraton 을 설정하기 위한 클래스이다.


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //h2-console 접속 시 보안 인증 절차 생략
        http.authorizeRequests().antMatchers("/h2-console/**").permitAll();
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication().withUser("Prid").password("{noop}zxc3226659").roles("USER");
            // noop 옵션 : 어떤 동작도 하지 않고 인코딩 없이 사용할 수 있게끔 하는 옵션
    }


}
