package com.todoslave.feedme.login.util;


import lombok.*;

@NoArgsConstructor
@Getter
@ToString
@AllArgsConstructor
@Builder
public class SecurityUserDto {
    private Integer id;
    private String email;
    private String nickname;
    private String role;
}