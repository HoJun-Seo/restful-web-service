package com.practice.restfulwebservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    // 연락처 정보를 위한 Contact 객체 생성(이름, url, 이메일)
    private static final Contact DEFAULT_CONTACT = new Contact("Prid", "http://www.joneconsulting.co.kr", "edowon@joneconsulting.co.kr");

    // 상수로 만들고 있는 목적은, 항목 고정이 되면 변경할 필요가 없는 정보들이기 때문에, Contact 나 ApiInfo 같은 정보를 상수로 만들어 두는 것이 편하다.
    private static final ApiInfo DEFAULT_API_INFO = new ApiInfo("Awesome API Title", "My User management REST API service", "1.0", "urn : tos", DEFAULT_CONTACT, "Apache 2.0", "http://www.apache.org/license/LICENSE-2.0", new ArrayList<>());

    // 프로듀스와 컨슈머가 어떤 형태로 데이터를 사용할 수 있는지 문서 타입을 지정한다.
    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(Arrays.asList("application/json", "application/xml"));
    // Arrays.asList() 메소드는 배열 형태의 데이터를 리스트 형태로 변환시켜 준다.
    @Bean
    public Docket api(){
        // 아래의 Docket 객체에서 api 정보, 프로듀스 정보, 컨슈머 정보를 변경해보자.
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(DEFAULT_API_INFO)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES);
    }
}
