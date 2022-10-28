package com.computatongsin.computatongsin.dto.req;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SignupReqDto {

    @Pattern(regexp = "^([0-9]|[a-z]|[A-Z]|-|_|@|\\.){3,40}$", message = "잘못된 아이디 형식입니다")
    @NotBlank(message = "이름을 입력해주세요")
    private String username;

    @Pattern(regexp = "^([0-9]|[a-z]|[A-Z]){4,12}$", message = "잘못된 비밀번호 형식입니다")
    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    @NotBlank
    private String passwordConfirm;

    @NotBlank
    private String nickname;

}
