package com.todoslave.feedme.controller;

import com.todoslave.feedme.DTO.FriendInfoResponseDTO;
import com.todoslave.feedme.DTO.MemberSearchResponseDTO;
import com.todoslave.feedme.DTO.MemberSignupRequestDTO;
import com.todoslave.feedme.DTO.MypageResponseDTO;
import com.todoslave.feedme.config.jwt.JwtProperties;

import com.todoslave.feedme.domain.entity.membership.Member;

import com.todoslave.feedme.login.Handler.JWTUtill;
import com.todoslave.feedme.login.Service.RefreshTokenService;
import com.todoslave.feedme.login.Service.TokenBlacklistService;
import com.todoslave.feedme.login.dto.TokenResponseStatus;
import com.todoslave.feedme.login.util.SecurityUtil;
import com.todoslave.feedme.repository.MemberRepository;
import com.todoslave.feedme.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "회원 CRUD")
@RequiredArgsConstructor
public class MemberController {


    private final JwtProperties jwtProperties;
    private final MemberService memberService;
    private final TokenBlacklistService tokenBlacklistService;
    private final RefreshTokenService tokenService;
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(jwtProperties.getSecretKey().getBytes());
    }


    @Operation(summary = "맴버 검색 (for 친구추가)")
    @GetMapping("/{searchvalue}")
    public ResponseEntity<List<MemberSearchResponseDTO>> getUsers(@PathVariable String searchvalue) {
        List<MemberSearchResponseDTO> list = memberService.getMemberList(searchvalue);
        return new ResponseEntity<List<MemberSearchResponseDTO>>(list, HttpStatus.OK);
    }


    @Operation(summary = "맴버 가입")
    @PostMapping
    public ResponseEntity<?> signupMember(@RequestBody MemberSignupRequestDTO memberSignupRequestDTO){

        // 닉네임 중복 체크
        if (memberService.checkNickname(memberSignupRequestDTO.getNickname())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Nickname already exists");
        }

        Member member = memberService.registerMember(memberSignupRequestDTO);
        return ResponseEntity.ok(member);
    }


    @Operation(summary = "맴버 수정")
    @PatchMapping
    public ResponseEntity<?> updateMember(@RequestBody MemberSignupRequestDTO memberSignupRequestDTO){

        // 닉네임 중복 체크
        if (memberService.checkNickname(memberSignupRequestDTO.getNickname())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Nickname already exists");
        }

        Member member = memberService.updateMember(memberSignupRequestDTO);

        return ResponseEntity.ok(member);
    }


    @Operation(summary = "맴버 정보 가져오기 (테스트용)")
    @GetMapping("/holder/test")
    public Member getCurrentMember() {

//        LocalDateTime t = LocalDateTime.now();
//        System.out.println(t);
//        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul")); // 또는 사용자의 시간대
//        System.out.println("현재 날짜: " + today);


        return SecurityUtil.getCurrentMember();
    }

    @Operation(summary = "내 정보 페이지")
    @GetMapping("/mypage")
    public ResponseEntity<MypageResponseDTO> getMypage(@RequestHeader("Authorization") final String accessToken) {

        return ResponseEntity.ok(memberService.getMyPage());
    }


    @Operation(summary = "로그아웃")
    @GetMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") final String accessToken) {

        // 토큰을 블랙리스트에 추가하여 무효화
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody();
        Date expiryDate = claims.getExpiration();

        // 블랙리스트 서비스에 토큰을 추가
        tokenBlacklistService.addToBlacklist(accessToken, expiryDate);

        //레디스에서 제거
        tokenService.removeRefreshToken(accessToken);

        return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
    }


    @Operation(summary = "맴버 탈퇴")
    @DeleteMapping
    public ResponseEntity<?> deleteMember(@RequestHeader("Authorization") final String accessToken) {
        int id = SecurityUtil.getCurrentUserId();

        if (id == -1) {
            return ResponseEntity.badRequest().body("로그인 상태를 확인해주세요.");
        }

        boolean isDeleted = memberService.removeMember(); // id를 인자로 전달하도록 수정

        if (isDeleted) {
            return ResponseEntity.ok("정상적으로 삭제되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 삭제된 회원이거나, 로그인 상태를 확인해주세요.");
        }
    }


}
