package com.practice.restfulwebservice.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service // 해당 클래스의 역할을 알린다.
public class UserDaoService {
    // 사용자 전체 목록 조회
    // 사용자 정보 추가
    // 사용자 정보 상세 보기
    // 위와 같은 비즈니스 로직을 추가한다.(보통 Service 로 되어있는 클래스에 정의한다.)
    // 직접 관계형 데이터베이스를 사용하거나 하지 않고 메모리에 있는 데이터를 활용한다.
    // 관계형 데이터베이스를 다루는 것은 후에 JPA 강의에서 진행 될 것
    private static List<User> users = new ArrayList<>();

    private static int usersCount = 3;

    static {
        users.add(new User(1, "Evan", new Date()));
        users.add(new User(2, "Prid", new Date()));
        users.add(new User(3, "Pantom", new Date()));
    }

    public List<User> findAll(){
        return users;
    }

    public User save(User user){
        if(user.getId() == null){
            user.setId(++usersCount); // Id 의 값이 존재하지 않으면 전체 리스트 갯수에서 +1 을 하여 Id 값을 세팅해준다.
        }
        users.add(user);
        return user;
    }

    public User findOne(int id){
        for (User user : users){
            if(user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public User deleteById(int id){
        // 데이터가 존재하는지 부터 검사한다.
        Iterator<User> iterator = users.iterator();
        while(iterator.hasNext()){
            User user = iterator.next();
            if(user.getId() == id){
                iterator.remove();
                return user;
            }
        }
        return null;
    }
}
