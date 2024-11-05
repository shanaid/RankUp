package com.todoslave.feedme.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todoslave.feedme.DTO.CreatureInfoResponseDTO;
import com.todoslave.feedme.DTO.CreatureMakeRequestDTO;
import com.todoslave.feedme.domain.entity.avatar.Creature;
import com.todoslave.feedme.domain.entity.membership.Member;
import com.todoslave.feedme.login.util.SecurityUtil;
import com.todoslave.feedme.repository.CreatureRepository;
import com.todoslave.feedme.repository.CreatureTodoReposito;
import com.todoslave.feedme.service.CreatureService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/creature")
@RequiredArgsConstructor
public class CreatureController {

    private final CreatureService creatureService;

    private final RestTemplate restTemplate = new RestTemplate();

    private final CreatureRepository creatureRepository;
//    @Operation(summary = "크리쳐 생성")
//    @PostMapping
//    public ResponseEntity<?> createCreature(@RequestBody CreatureMakeRequestDTO request,@RequestPart, @RequestHeader("Authorization") final String accessToken) {
//
//        Member member = SecurityUtil.getCurrentMember();
//
//            Creature creature = creatureService.createFristCreature(request.getKeyword(), request.getPhoto(), request.getCreatureName());
//
//        return ResponseEntity.ok(Map.of("creatureId", creature.getId(), "message", "크리쳐가 성공적으로 생성되었습니다."));
//    }
//

    @Operation(summary = "크리쳐 생성")
    @PostMapping
    public ResponseEntity<?> createCreature(
            @RequestParam("creatureName") String creatureName,
            @RequestParam("keyword") String keyword,
            @RequestParam("file") MultipartFile file,
            @RequestHeader("Authorization") final String accessToken) {

        // 파일 저장 로직 또는 처리
        // 예를 들어, 파일을 저장하거나 처리하는 로직

        // 닉네임 중복 체크
        if (creatureService.checkNickname(creatureName)){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Nickname already exists");
        }

        // 서비스 호출 예시
        Creature creature = creatureService.createFristCreature(keyword, file, creatureName);

        return ResponseEntity.ok(Map.of("creatureId", creature.getId(), "message", "크리쳐가 성공적으로 생성되었습니다."));
    }

    @Operation(summary = "크리쳐 보기")
    @GetMapping
    public ResponseEntity<CreatureInfoResponseDTO> getCreatures(@RequestHeader("Authorization") final String accessToken) {

        CreatureInfoResponseDTO creature = creatureService.creatureInfo(SecurityUtil.getCurrentMember());

        return new ResponseEntity<CreatureInfoResponseDTO>(creature, HttpStatus.OK);
    }

    @Operation(summary = "크리쳐 삭제")
    @DeleteMapping
    public ResponseEntity<?> delectCreature(@RequestHeader("Authorization") final String accessToken) {
        if(creatureService.removeCreature()){
        return new ResponseEntity<String>("삭제되었습니다.", HttpStatus.OK);}else{
            return new ResponseEntity<String>("해당 크리쳐를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST);
        }
    }


}
