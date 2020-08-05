package com.practice.restfulwebservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    private Date timestamp; // 예외가 발생한 시간
    private String message; // 예외 처리시 메세지
    private String details; // 예외 처리의 상세 정보

    //Response Entity Exception Handler 라는 클래스를 상속받은 새로운 Handler 클래스를 만들어준다.
    // 이 클래스는 사용하고 있는 시스템에서 에러가 발생하면 그 에러를 핸들링하기 위한, 스프링 부트에서 제공되는 클래스이다.
    //컨트롤러 Bean 에서 예외가 발생하게 되면 위의 핸들러 예외 클래스가 발생할 수 있도록 처리해주자.
    //스프링 프레임워크에서 로깅정보나 로그인정보 아니면 필요했었던 메세지를 추가하는 작업과 같이
    //모든 비즈니스 로직 또는 어떤 컨트롤러 에서 실행시켜줘야 하는 공통적인 항목이 있다면 그것을 AOP 라고 하여 별도의 공통된 로직을 사용해줄 수 있다.
    // 실행하고자 하는 예외 처리 핸들러 클래스도 AOP 기능을 사용한다.
    // 스프링 부트 어플리케이션 내에서 공통적으로 처리되어야 하는 로직이나, 처리해줄 수 있는 메소드를 추가할때 사용하면 된다.
}
