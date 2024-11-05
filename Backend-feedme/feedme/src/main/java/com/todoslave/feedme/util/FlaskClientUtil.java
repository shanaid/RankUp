//package com.todoslave.feedme.util;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.core.io.Resource;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//
//@Component
//public class FlaskClientUtil {
//
//    private final RestTemplate restTemplate;
//
//    // HTTP 요청을 위해 사용
//    public FlaskClientUtil(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    /**
//     * Flask 서버로부터 크리쳐 이미지를 가져오는 메서드입니다.
//     *
//     * @param username  사용자의 이름
//     * @param creatureId 크리쳐의 고유 ID
//     * @param level     크리쳐의 현재 레벨
//     * @return Flask 서버로부터 받은 이미지 리소스
//     */
//    public Resource getCreatureImage(String username, int creatureId, int level) {
//        // URL 생성 (레벨은 쿼리 파라미터로 전송)
//        String url = String.format("http://localhost:3333/store/creature_data/%s/%d?level=%d", username, creatureId, level);
//
//        // GET 요청을 통해 Flask 서버로부터 이미지 데이터 받기
//        ResponseEntity<ByteArrayResource> response = restTemplate.getForEntity(url, ByteArrayResource.class);
//
//        // 요청이 성공했는지 확인하고, 성공하지 않았으면 예외를 던짐
//        if (response.getStatusCode().is2xxSuccessful()) {
//            return response.getBody(); // Flask 서버로부터 받은 이미지 데이터 반환
//        } else {
//            throw new RuntimeException("Failed to retrieve image from Flask server.");
//        }
//    }
//
//    /**
//     * Flask 서버로부터 크리쳐 그림일기를 가져오는 메서드입니다.
//     *
//     * @param username 사용자의 이름
//     * @param date     그림일기를 요청하는 날짜 (LocalDate 형식)
//     * @return Flask 서버로부터 받은 그림일기 리소스
//     */
//    public Resource getCreatureDiary(String username, LocalDate date) {
//        // 날짜를 문자열 형식으로 변환 (yyyy-MM-dd)
//        String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//
//        // URL 생성 (날짜는 쿼리 파라미터로 전송)
//        String url = String.format("http://localhost:3333/store/creature_diary/%s?date=%s", username, formattedDate);
//
//        // GET 요청을 통해 Flask 서버로부터 그림일기 데이터 받기
//        ResponseEntity<ByteArrayResource> response = restTemplate.getForEntity(url, ByteArrayResource.class);
//
//        // 요청이 성공했는지 확인하고, 성공하지 않았으면 예외를 던짐
//        if (response.getStatusCode().is2xxSuccessful()) {
//            return response.getBody(); // Flask 서버로부터 받은 그림일기 데이터 반환
//        } else {
//            throw new RuntimeException("Failed to retrieve diary from Flask server.");
//        }
//    }
//}
package com.todoslave.feedme.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.Base64;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class FlaskClientUtil {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    // HTTP 요청을 위해 사용
    public FlaskClientUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Flask 서버로부터 크리쳐 이미지를 가져와 byte[] 형태로 반환하는 메서드입니다.
     *
     * @param username  사용자의 이름
     * @param creatureId 크리쳐의 고유 ID (nullable)
     * @param level     크리쳐의 현재 레벨 (nullable)
     * @return Flask 서버로부터 받은 이미지 데이터 (byte[])
     */

    public byte[] getCreatureImageAsByteArray(String username, Integer creatureId, Integer level) {

        String url;

        // URL 생성: username을 creatureId로 간주하여 사용
//        if (creatureId == null || creatureId == 0 || level == null || level == 0) {
//            int tmp = username.length() % 4;
//            // creatureId나 level이 null 또는 0인 경우, 기본 이미지를 요청
//            url = String.format("http://flask:33333/store/egg/%d",tmp);
//
//        } else {
            // 일반적인 경우

        if(level==0){
            return null;
        }

            url = String.format("http://flask:33333/store/%s/%d/%d", username, creatureId, level);
//        }

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            try {
                // JSON 응답을 파싱하여 gif_data를 가져옴
                JsonNode root = objectMapper.readTree(response.getBody());
                String base64GifData = root.path("gif_data").asText();

                // Base64로 인코딩된 데이터를 디코딩하여 바이트 배열로 변환
                return Base64.getDecoder().decode(base64GifData);
            } catch (Exception e) {
                throw new RuntimeException("Failed to parse JSON or decode Base64 data", e);
            }
        } else {
            throw new RuntimeException("Failed to retrieve image from Flask server.");
        }
    }

    // Helper method to convert ByteArrayResource to byte[]
    private byte[] toByteArray(ByteArrayResource resource) {
        try {
            return resource.getInputStream().readAllBytes();
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert ByteArrayResource to byte array", e);
        }
    }

    /**
     * Flask 서버로부터 크리쳐 그림일기를 가져와 byte[] 형태로 반환하는 메서드입니다.
     *
     * @param username 사용자의 이름
     * @param date     그림일기를 요청하는 날짜 (LocalDate 형식)
     * @return Flask 서버로부터 받은 그림일기 데이터 (byte[])
     */
    public byte[] getCreatureDiaryAsByteArray(String username, LocalDate date) {
        // 날짜를 문자열 형식으로 변환 (yyyy-MM-dd)
        String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // URL 생성
        String url = String.format("http://flask:33333/store/creature_diary/%s/%s", username, formattedDate);

        try {
            ResponseEntity<ByteArrayResource> response = restTemplate.getForEntity(url, ByteArrayResource.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return toByteArray(response.getBody()); // byte[] 형태로 변환하여 반환
            } else {
                return null; // 요청이 성공하지 않았거나, 응답 본문이 없을 경우 null 반환
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve diary from Flask server.", e);
        }
    }




//    /**
//     * Flask 서버로부터 크리쳐 그림일기를 가져와 byte[] 형태로 반환하는 메서드입니다.
//     *
//     * @param username 사용자의 이름
//     * @param date     그림일기를 요청하는 날짜 (LocalDate 형식)
//     * @return Flask 서버로부터 받은 그림일기 데이터 (byte[])
//     */
//
//    public byte[] getCreatureDiaryAsByteArray(String username, LocalDate date) {
//        // 날짜를 문자열 형식으로 변환 (yyyy-MM-dd)
//        String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//
//        // URL 생성 (날짜는 쿼리 파라미터로 전송)
//        String url = String.format("http://flask:33333/store/creature_diary/%s/%s", username, formattedDate);
//
//        // GET 요청을 통해 Flask 서버로부터 그림일기 데이터 받기
//        try {
//            ResponseEntity<ByteArrayResource> response = restTemplate.getForEntity(url, ByteArrayResource.class);
//
//            // 요청이 성공했는지 확인하고, 성공하지 않았으면 null 반환
//            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
//                return toByteArray(response.getBody()); // byte[] 형태로 변환하여 반환
//            } else {
//                return null; // 요청이 성공하지 않았거나, 응답 본문이 없을 경우 null 반환
//            }
//        } catch (Exception e) {
//            // 예외 발생 시 null 반환
//            return null;
//        }
//    }
//
//
////    //크리쳐 다이어리 가져오기
////    public byte[] getCreatureDiaryAsByteArray(String username, LocalDate date) {
////        // 날짜를 문자열 형식으로 변환 (yyyy-MM-dd)
////        String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
////
////        // URL 생성 (날짜는 쿼리 파라미터로 전송)
////        String url = String.format("http://flask:33333/store/creature_diary/%s/%s", username, formattedDate);
////
////        // GET 요청을 통해 Flask 서버로부터 그림일기 데이터 받기
////        ResponseEntity<ByteArrayResource> response = restTemplate.getForEntity(url, ByteArrayResource.class);
////
////        // 요청이 성공했는지 확인하고, 성공하지 않았으면 예외를 던짐
////        if (response.getStatusCode().is2xxSuccessful()) {
////            return toByteArray(response.getBody()); // byte[] 형태로 변환하여 반환
////        } else {
////            throw new RuntimeException("Failed to retrieve diary from Flask server.");
////        }
////    }
//
//
//    private byte[] toByteArray(Resource resource) {
//        try (InputStream inputStream = resource.getInputStream()) {
//            return IOUtils.toByteArray(inputStream);
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to convert resource to byte array", e);
//        }
//    }
}

