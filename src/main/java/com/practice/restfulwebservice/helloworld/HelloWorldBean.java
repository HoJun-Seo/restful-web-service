package com.practice.restfulwebservice.helloworld;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// lombok : Getter, Setter 와 같은 메소드들 자동 생성
@Data
@AllArgsConstructor // 이 어노테이션을 선언했음에도 불구하고 생성자를 작성하게 되면 컴파일 에러가 발생한다.(똑같은 생성자가 여러개 생기게 되기 때문)
@NoArgsConstructor // 디폴트 생성자도 함께 만들어주는 어노테이션
public class HelloWorldBean {
    private String message;

    /*public String getMessage(){
        return this.message;
    }
    public String setMessage(String msg){
        this.message = msg;
    }*/ // lombok 을 사용하기 때문에 Getter, Setter 를 직접 작성하지 않아도 된다.

    /*public HelloWorldBean(String message) {
        this.message = message;
    }*/  // @AllArgsConstructor 으로 인해 생성자 또한 직접 작성해줄 필요가 없다.
}
