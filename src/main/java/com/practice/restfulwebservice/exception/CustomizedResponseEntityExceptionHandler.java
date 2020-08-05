package com.practice.restfulwebservice.exception;

import com.practice.restfulwebservice.user.UserNOTFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice // 모든 컨트롤러가 실행될 때 반드시, 지금 선언시켜 놓은 어노테이션을 가지고 있는 Bean 이 사전에 먼저 실행된다.
//만약 에러가 발생된다고 하면 현재 핸들러에서 등록시켰던 에러 메소드가 바로 실행될 수 있을 것이다.
//모든 예외 상황 발생시 반드시 현재 핸들러에서 모두 처리할 수 있도록 코드를 작성하자.
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class) // 어떤 컨트롤러가 실행된다고 하더라도 예외 발생시에는 현재 작성한 핸들러가 실행되게 될 것이다.
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        // 현재 핸들러로 인해 예외 발생 시간, 예외 처리 메세지, 예외 처리 세부 정보와 같이 표시하고 싶은 정보들만 빼내에 제한적으로 예외 발생 상황을 리턴해줄 수 있다.
    } // 현재 만들어진 메소드는 모든 예외 처리를 전부 처리해주는 예외이기 때문에 일반화되어 있는 Exception 이란 클래스 말고
    // 사용자가 존재하지 않았을 때 처리할 수 있는 UserNotFoundException 이라는 클래스를 만들어놓은 상태이다.
    //만약 컨트롤러에서 NotFound Exception 이라는 예외가 발생하게 되면 지금 등록시킨 handleAllExceptions 가 아닌
    // 아래에서 작성한 handleUserNotFoundException 이 실행되도록 코드를 작성해보자.

    @ExceptionHandler(UserNOTFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(Exception ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false)); // 객체는 동일하게 사용한다.

        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);

    }

}
