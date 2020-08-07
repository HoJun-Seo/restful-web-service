package com.practice.restfulwebservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@SpringBootApplication
public class RestfulWebServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestfulWebServiceApplication.class, args);
    }

    @Bean // @SpringBootApplication 어노테이션이 있는 클래스에 @Bean 어노테이션을 선언하게 되면, 스프링 부트가 초기화 될 때 아래에 선언한 메소드에 해당하는 값이 메모리에 올라가서 다른쪽에 있는 클래스들도 사용할 수 있게된다.
    public SessionLocaleResolver localResolver(){
        // 세션을 통해서 로케일 값을 얻어온다.
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.KOREA);
        return localeResolver;
    }
}
