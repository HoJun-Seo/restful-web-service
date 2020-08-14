package com.practice.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonFilter("UserInfo")
//@JsonIgnoreProperties(value = {"password", "ssn"})
@ApiModel(description = "사용자 상세 정보를 위한 도메인 객체") // Swagger 커스터마이징
@Entity
public class User {
    @Id
    @GeneratedValue
    private Integer id;

    @Size(min = 2, message = "Name 은 2글자 이상 입력해주세요") // 속성 값의 길이가 최소 2를 넘어야 한다는 제약 부여
    @ApiModelProperty(notes = "사용자 이름을 입력해주세요.") // Swagger 커스터마이징
    private String name;
    @Past // 과제 데이터만 사용할 수 있다는 제약 부여
    @ApiModelProperty(notes = "등록일 을 입력해주세요.")
    private Date joinDate;

    @ApiModelProperty(notes = "사용자의 패스워드를 입력해주세요.")
    private String password;
    @ApiModelProperty(notes = "사용자의 주민번호를 입력해주세요.")
    private String ssn;
}
