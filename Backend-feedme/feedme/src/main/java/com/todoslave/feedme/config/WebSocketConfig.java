//package com.todoslave.feedme.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//
//@Configuration
//@EnableWebSocketMessageBroker
//public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//
//  @Override
//  public void configureMessageBroker(MessageBrokerRegistry config) {
//    config.enableSimpleBroker("/chatting", "/chatRoom");
//    config.setApplicationDestinationPrefixes("/chat");
//  }
//
//  @Override
//  public void registerStompEndpoints(StompEndpointRegistry registry) {
//    registry.addEndpoint("/friendChat")
//        .setAllowedOriginPatterns("http://localhost:8080","null") // 명시적인 오리진 사용
//        .withSockJS();
//  }
//}

//0810 진기가 보내준 파일
package com.todoslave.feedme.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/chatting", "/chatRoom");
    config.setApplicationDestinationPrefixes("/chat");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/ws/friendChat")

        .setAllowedOriginPatterns("*")
        .withSockJS();

  }
}