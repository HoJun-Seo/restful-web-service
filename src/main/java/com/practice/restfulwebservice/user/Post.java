package com.practice.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue
    private Integer id;

    private String description;

    // User : Post -> 1 : (0 ~ N)
    @ManyToOne(fetch = FetchType.LAZY) // post 입장에서는 유저 데이터가 하나만 와야 한다.
    @JsonIgnore // json 타입으로 데이터를 외부에 공개하려고 할 때 해당하는 값은 공개되지 않는다.
    private User user;
}
