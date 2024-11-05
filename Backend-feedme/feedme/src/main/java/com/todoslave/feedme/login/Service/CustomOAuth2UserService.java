package com.todoslave.feedme.login.Service;

import com.todoslave.feedme.domain.entity.membership.Member;
import com.todoslave.feedme.login.dto.OAuth2Attribute;
import com.todoslave.feedme.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberService memberService;

    @Override // 유저를 가져오려고 한다.OAuth2User 라는 객체 형태로
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // 기본 OAuth2UserService 객체 생성 과정
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();

        // OAuth2UserService를 사용하여 OAuth2User 정보를 가져온다.
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        // 클라이언트 등록 ID(naver, kakao)와 사용자 이름 속성을 가져온다.
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        String userNameAttributeName = userRequest.getClientRegistration() //사용자 이름 가져오기
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

//        System.out.println(registrationId);
//        System.out.println(userNameAttributeName);
//        System.out.println("이거야!!");

        // OAuth2UserService를 사용하여 가져온 OAuth2User 정보로 OAuth2Attribute 객체를 만든다.
        OAuth2Attribute oAuth2Attribute =
                OAuth2Attribute.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        // OAuth2Attribute의 속성값들을 Map으로 반환 받는다.
        Map<String, Object> memberAttribute = oAuth2Attribute.convertToMap();

        // 사용자 email(또는 id) 정보를 가져온다.
        String email = (String) memberAttribute.get("email");

        System.out.println(email);


        // 이메일로 가입된 회원인지 조회한다.
        Optional<Member> findMember = memberService.findByEmail(email);

        if (findMember.isEmpty()) {
            // 회원이 존재하지 않을경우, memberAttribute의 exist 값을 false로 넣어준다.

            memberAttribute.put("exist", false);
            // 회원의 권한(회원이 존재하지 않으므로 기본권한인 ROLE_USER를 넣어준다),
            // 회원속성, 속성이름을 이용해 DefaultOAuth2User 객체를 생성해 반환한다.
            return new DefaultOAuth2User(
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                    memberAttribute, "email");
        }

        //일단 아이디 잘 따지나 체크
        System.out.println(findMember.get().getId());
        System.out.println("ROLE_".concat(findMember.get().getUserRole()));

        // 여기는 회원인 사람 -> SuccessHandler 에서 jwt 토큰을 발급해서 프론트로 넘겨 주게 된다!!

        // 회원이 존재할경우, memberAttribute의 exist 값을 true로 넣어준다.
        //SuccessHandler 에서 exist 변수의 값에 따라서 회원가입을 했는지 안했는지 여부를 체크하고 처리할 수 있게 넣어준다.
        memberAttribute.put("exist", true);


        // 회원의 권한과, 회원속성, 속성이름을 이용해 DefaultOAuth2User 객체를 생성해 반환한다.
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_".concat(findMember.get().getUserRole()))),
                memberAttribute, "email");

    }
}