package com.practice.restfulwebservice.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
// RestController 를 사용하게 되면 반환되는 값이 response body 에 포함되지 않더라도 Java 의 배열 또는 컬렉션 같은 값으로 반환하게 되면 자동으로 Json 문서로 반환해주는 특징을 가진다.
// Json 이 아닌 xml 형태로 반환하고자 한다면 xml 값을 사용하기 위한 라이브러리를 추가하면 된다.
public class HelloWorldController {
    // GET
    // /hello-world : uri(사용자에 의해 호출되는 end point)
    // @RequestMapping(method = RequestMethod.GET, path = "/hello-world")
    @GetMapping(path = "/hello-world") // uri 외 다른 값도 주고 싶을 경우 path 를 붙여서 구분해 주는것이 좋다.
    public String helloWorld(){
        return "Hello World";
    }

    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("Hello World");
        // Json 형태로 메세지가 반환된다.
    }
    /*
    반환시키고자 하는 값을 String 이 아니라 Java Bean 형태로 하면
    스프링 프레임워크 에서는 단순한 텍스트 또는 객체가 아닌 Json 형태로 변환하여 반환하게 된다.
     */

    //Path Variable 은 정의한 API 의 url 에 변수를 지정해주는 것(가변 변수)
    @GetMapping(path = "/hello-world-bean/path-variable/{name}") // Path Variable 을 활용하려면 매핑하려는 url 뒤에 {} 를 사용하여 변수명을 지정해 주어야 한다.
    public HelloWorldBean helloWorldBean(@PathVariable String name){ // Path Variable 로 사용하고자 하는 변수를 선언할 때는 앞에 @PathVariable 어노테이션을 붙여준다.
        //여기서 변수의 이름을 다르게 가져가고 싶다면 @PathVariable(value = "name") String another_value 와 같은 형식으로 파라미터를 작성해주면 된다.
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }
}
