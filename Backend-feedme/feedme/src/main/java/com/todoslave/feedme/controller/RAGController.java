package com.todoslave.feedme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class RAGController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/callRAG")
    public Map<String, Object> callRAG(@RequestBody Map<String, Object> requestData) {
        // Flask API URL (로컬 서버나 ngrok을 통해 공개된 URL)
        String flaskUrl = "https://magnetic-ram-brave.ngrok-free.app/rag";

        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // HTTP 요청 바디에 데이터 포함
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestData, headers);

        // Flask API로 POST 요청 보내기
        ResponseEntity<Map> response = restTemplate.exchange(flaskUrl, HttpMethod.POST, entity, Map.class);

        // Flask API로부터 받은 응답 반환
        return response.getBody();
    }
}
