package com.opensw.safeguard.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MemberJoinDTO {

    @NotBlank(message = "이메일은 필수 입력 값 입니다")
    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    private String username;
    @NotBlank(message = "비밀번호는 필수 입력 값 입니다")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    @NotBlank(message = "이름은 필수 입력 값 입니다")
    private String realName;

    @NotBlank(message = "번호는 필수 입력 값 입니다")
    private String phoneNumber;


    private List<String> associatedPhoneNumber;


}
