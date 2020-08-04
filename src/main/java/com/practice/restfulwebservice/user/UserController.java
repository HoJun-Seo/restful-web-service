package com.practice.restfulwebservice.user;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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
    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }
    //GET /users/1 or /users/10 -> String // id 값을 정수형으로 작성하였다고 해도 서버측(Controller)에 전달될 때는 문자열 형태로 전달되게 된다.
    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id){ // int 형으로 선언해 놓으면 String 으로 변환되었던 데이터 값이 자동으로 정수형으로 매핑되어 사용할수 있게 된다.
        return service.findOne(id);
    }
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user){ // 클라이언트로부터 form data type 이 아닌 json, xml 과 같이 object 형태의 데이터를 받기 위해서는 매개변수 타입에 @RequestBody 어노테이션을 선언해 주어야 한다.
        // 현재 전달받고자 하는 데이터는 RequestBody 형식의 역할을 한다.
        // 클라이언트에서 전달하는 데이터 포맷에 일치하는 필드의 값을 ,선언시켰던 클래스(UserDomainClass)가 가지고 있는 값과 매핑시켜서 전달하게 된다.
        // 전달된 유저 객체를 DAOService 에 save 메소드에 전달하게 되면 저장 작업이 완료된다.
        User savedUser = service.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()) // path 에서 검색했던 가변 변수 id에 새롭게 만들어진 getId() 값을 지정 시켜준다.
                .toUri();

        return ResponseEntity.created(location).build();
        // 상태코드 반환값 : 201  // 서버로부터 요청 결과값에 적절한 상태 코드를 반환시켜 주는 것이 좋은 REST API 를 설계하는 방법중 하나이다.
        // 위와 같은 코드를 통해 클라이언트에게 생성된 id 값을 알려주게 되면 클라이언트가 id 값이 무엇인지 다시 물어볼 필요가 없게 되므로 그만큼 필요한 통신 횟수가 줄어들게 되어 서버 트래픽에 긍정적인 영향을 끼치게 된다.
        // REST API 를 설계할 때 가장 안 좋은 것 중 하나 : 클라이언트의 모든 요청을 용도에 맞춰서 GEt,POST,PUT,DELETE 와 같이 구분하지 않고
        // 모든 요청을 POST 메소드로 처리한 다음 응답 코드를 200 번 ok 로 처리하는 경우이다.
        // 그렇게 하나의 단일되어 있는 상태 코드 값을 사용하지 말고 예외 핸들링과 같은 것을 적절하게 조합해서 사용하는 것이 좋다.
        // 그렇게 하나의 단일되어 있는 상태 코드 값을 사용하지 말고 예외 핸들링과 같은 것을 적절하게 조합해서 사용하는 것이 좋다.
        // 새로운 사용자를 추가했을 때도 똑같은 200 번 코드를 사용하는 것이 아닌 201 번 과 같은 create 상태값을 가지고 적잘하게 처리하는 것이 좋다.
        // 작업 용도에 맞춰서 리소스의 상태 즉, HTTP 의 메소드들을 적절하게 호출하자.
    }
}
