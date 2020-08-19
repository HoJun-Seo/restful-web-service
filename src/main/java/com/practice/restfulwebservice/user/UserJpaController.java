package com.practice.restfulwebservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/jpa") // uri prefix 지정
public class UserJpaController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
       return userRepository.findAll(); // 특별한 조건없이 모든 데이터를 반환한다.
    }

    @GetMapping("/users/{id}")
    public Resource<User> retrieveUser(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()){ // 해당하는 id 값의 학생정보가 존재하지 않을 경우 예외 반환
            throw new UserNOTFoundException(String.format("ID[%s] not found"));
        }
        // 만약 HATEOAS 기능을 사용하고 싶다면?
        Resource<User> resource = new Resource<>(user.get());
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        resource.add(linkTo.withRel("all-users")); // resource 에 링크 추가

        return resource;
        //return user.get(); // 데이터 반환값이 Optional 타입이라고 메소드의 반환 타입을 똑같이 맞춰주려고 할 필요 없이
        // 위와 같이 get() 메소드를 활용해주면 된다.
        //get() 메소드는 T 타입의 반환값, 즉 위의 user 데이터 초기화에서 User 타입의 데이터를 가진다.
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser = userRepository.save(user);

        // 생성된 데이터에 한 하여 id 값을 자동으로 지정해준다
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    // /jpa/users/90001/posts
    @GetMapping("/users/{id}/posts")
    public List<Post> retrieveAllPostByUser(@PathVariable int id){
        // id 값에 해당하는 것은 사용자의 id 이므로 이것을 이용하여 실제 해당하는 사용자가 존재하는지 그렇지 않은지 검색해야 하므로 Optional 타입의 데이터를 사용해야 한다.
        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()){ // 해당하는 id 값의 학생정보가 존재하지 않을 경우 예외 반환
            throw new UserNOTFoundException(String.format("ID[%s] not found"));
        }

        return user.get().getPosts();
    }
}
