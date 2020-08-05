package com.practice.restfulwebservice.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//HTTP Status code
//2XX -> OK
//4XX -> Client error
//5XX -> Server error
@ResponseStatus(HttpStatus.NOT_FOUND) // 이렇게 Status 를 지정하게 되면 앞으로 이 예외 클래스는 5XX 번대 에러가 아니라 Not found, 즉 404 에러로 클라이언트에게 전달되게 되어 클라이언트가 요청했던 데이터 값이 서버측의 오류중에서 데이터가 존재하지 않는 오류임을 알리는 방식으로 코딩을 해보자.
public class UserNOTFoundException extends RuntimeException {
    public UserNOTFoundException(String message) {
        super(message);
    }
}
