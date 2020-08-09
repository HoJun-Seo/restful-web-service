package com.practice.restfulwebservice.user;


import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
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

    @GetMapping("/users/{id}") // 정보 필터링이 적용되는 것 없이 모든 정보를 출력할 수 있게끔 만들어보자.
    public MappingJacksonValue retrieveUser(@PathVariable int id){ // 필터 적용을 위한 반환 타입 변경(MappingJacksonValue)
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
    
}
