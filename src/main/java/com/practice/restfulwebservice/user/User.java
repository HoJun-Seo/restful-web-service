package com.practice.restfulwebservice.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
public class User {
    private Integer id;

    @Size(min = 2, message = "Name 은 2글자 이상 입력해주세요") // 속성 값의 길이가 최소 2를 넘어야 한다는 제약 부여
    private String name;
    @Past // 과제 데이터만 사용할 수 있다는 제약 부여
    private Date joinDate;
}
