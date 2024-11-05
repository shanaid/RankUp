package com.todoslave.feedme.DTO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class MemberSignup {

    String email;

    String nickname;

    String userRole;

    Timestamp birthday;

}
