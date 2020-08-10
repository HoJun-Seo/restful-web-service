package com.practice.restfulwebservice.user;


import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils; // 스프링 부트에서 Bean 들 간에 연관된 작업을 도와주는 유틸 클래스(인스턴스 생성, 복사 등등)
//복사의 경우 두 인스턴스 간의 공통적인 필드 속성이 있을 경우 해당하는 값을 카피하는 기능 또한 포함되어 있다.
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//관리자용 컨트롤러 생성
@RestController
@RequestMapping("/admin") // 매핑을 하기위한 기본적인 선행(prefix) uri 이름을 클래스 블록에 선언할 수 있다.
public class AdminUserController {

    private UserDaoService service;

    public AdminUserController(UserDaoService service){
        this.service = service;
    }

    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers(){
        List<User> users = service.findAll();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "password", "ssn"); // 출력에 포함시킬 필드 선언
        //클라이언트에게 반환하는 객체 타입이 User 타입이 아닌, 위에서 생성한 필터가 적용 될 수 있는 MappingJacksonValue 타입으로 바꿔서 전달을 시켜보자.
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo", filter);
        // 위에서 생성한 필터를 사용할 수 있는 타입으로 변환
        // 유저 도메인 객체에서 어노테이션으로 선언한 UserInfo 필드 값을 사용하였다.

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(users);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }

    //@GetMapping("/v1/users/{id}") // 정보 필터링이 적용되는 것 없이 모든 정보를 출력할 수 있게끔 만들어보자.
    // uri 에서 v1 은 해당 메소드에 버전을 부여시킨 것이다.
    //@GetMapping(value = "/users/{id}/", params = "version=1") //Request parameter 를 이용한 방법
    // 우리가 호출하고자 하는 데이터 값 뒤에 버전 정보가 추가로 전달되어야 한다.(id 경로 뒤에 / 추가)
    // 사용하려고 하는 uri 값 뒤에 request parameter 값을 전달해줌으로서 1.0 버전인지 2.0 버전인지 명시하는 방법이다.
    //@GetMapping(value = "/users/{id}", headers = "X-API-VERSION=1")
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv1+json")
    public MappingJacksonValue retrieveUserV1(@PathVariable int id){ // 필터 적용을 위한 반환 타입 변경(MappingJacksonValue)
        User user = service.findOne(id);
        if(user == null){
            throw  new UserNOTFoundException(String.format("ID[%s] not found", id));
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "password", "ssn"); // 출력에 포함시킬 필드 선언
        //클라이언트에게 반환하는 객체 타입이 User 타입이 아닌, 위에서 생성한 필터가 적용 될 수 있는 MappingJacksonValue 타입으로 바꿔서 전달을 시켜보자.
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo", filter);
        // 위에서 생성한 필터를 사용할 수 있는 타입으로 변환
        // 유저 도메인 객체에서 어노테이션으로 선언한 UserInfo 필드 값을 사용하였다.

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }

    //@GetMapping("/v2/users/{id}")
    //@GetMapping(value = "/users/{id}/", params = "version=2")
    //@GetMapping(value = "/users/{id}", headers = "X-API-VERSION=2")
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv2+json")
    public MappingJacksonValue retrieveUserV2(@PathVariable int id){
        User user = service.findOne(id);
        if(user == null){
            throw  new UserNOTFoundException(String.format("ID[%s] not found", id));
        }
        // User -> User2
        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user, userV2); // 검색한 user 인스턴스 값을 userV2 로 복사한다.
        userV2.setGrade("VIP");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "grade", "ssn");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfoV2", filter);


        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userV2);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }
    
}
