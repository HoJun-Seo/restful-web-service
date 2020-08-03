package com.practice.restfulwebservice.user;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    // 이곳에서 앞서 작성한 도메인 데이터와 비즈니스 로직을 활용한다.
    // 개발자가 직접 서비스의 인스턴스를 생성하여 사용하는 것이 아니라 스프링 프레임워크에 의해 관리되도록 인스턴스를 선언하고 사용해야 한다.
    private UserDaoService service;
    // 스프링에서 선언되어 관리되고 있는 인스턴스를 Bean 이라고 부르고, 그 용도에 따라서 Controller Bean, Repository Bean, Serive Bean 등과 같이 선언해서 사용하게 된다.
    // 스프링에서 선언된 Bean 을 의존성 주입이라는 방법으로 관리하고 있는데, 의존성 주입은 xml 설정 파일을 통해서도 가능하고, 클래스에서 Setter 함수나 생성자를 통해서도 가능하다.
    // 즉, 스프링 컨테이너에 등록된 Bean 들을 사용하기 위해서는 컨테이너에 등록된 Bean 의 참조값을 받아와서 사용하게 된다.
    // 스프링 컨테이너 또는 IOC 컨테이너에 등록된 Bean 들은 개발자가 프로그램 실행 도중에 변경할 수 없기 때문에 일관성 있는 인스턴스를 사용할 수 있게된다.
    // 이곳에서 유저 서비스 인스턴스를 의존성 주입이라는 방법으로 할당해서 사용한다.
    // 지금은 생성자를 이용하여 의존성을 주입해보자.
    public UserController(UserDaoService service){
        this.service = service; // 생성자를 이용한 의존성 주입
    }

    // 메소드를 3가지 등록시킨다.
    // 사용자 전체 목록 조회, 사용자 개별 조회, 사용자 추가
}
