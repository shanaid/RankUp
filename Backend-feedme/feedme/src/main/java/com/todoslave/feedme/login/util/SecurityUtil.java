package com.todoslave.feedme.login.util;

import com.todoslave.feedme.domain.entity.membership.Member;
import com.todoslave.feedme.repository.MemberRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    private static MemberRepository memberRepository;

    @Autowired
    public SecurityUtil(MemberRepository memberRepository) {
        SecurityUtil.memberRepository = memberRepository;
    }

    public static SecurityUserDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof SecurityUserDto) {
            return (SecurityUserDto) authentication.getPrincipal();
        }

        return null; // 인증 정보가 없는 경우 처리
    }

    public static int getCurrentUserId() {
        SecurityUserDto userDto = getCurrentUser();
        return (userDto != null) ? userDto.getId() : -1; // -1로 대체하여 null 피하기
    }

    public static Member getCurrentMember() {
        SecurityUserDto userDto = getCurrentUser();
        if (userDto != null) {
            return memberRepository.findById(userDto.getId()).orElse(null);
        }
        return null;
    }
}