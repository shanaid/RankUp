 ////package com.todoslave.feedme.config.security;
//////
//////import com.todoslave.feedme.service.UserDetailService;
//////import lombok.RequiredArgsConstructor;
//////import org.springframework.context.annotation.Bean;
//////import org.springframework.context.annotation.Configuration;
//////import org.springframework.security.authentication.AuthenticationManager;
//////import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//////import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//////import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//////import org.springframework.security.core.userdetails.UserDetailsService;
//////import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//////import org.springframework.security.web.SecurityFilterChain;
//////import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//////import org.springframework.web.servlet.config.annotation.CorsRegistry;
//////import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//////
//////@RequiredArgsConstructor
//////@Configuration
//////public class WebSecurityConfig {
//////
//////    private final UserDetailService userService;
//////
////////    @Bean //CORS 설정
////////    public WebMvcConfigurer corsConfigurer() {
////////        return new WebMvcConfigurer() {
////////            @Override
////////            public void addCorsMappings(CorsRegistry registry) {
////////                registry.addMapping("/**")
////////                        .allowedOrigins("*")
////////                        .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
////////                        .allowedHeaders("*")
////////                        .allowCredentials(true);
////////            }
////////        };
////////    }
//////
//////    @Bean
//////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//////        http
//////                .authorizeHttpRequests(authorize -> authorize
//////                        .requestMatchers("/login", "/signup", "/user", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html","/user/**").permitAll()
//////                        .anyRequest().authenticated()
//////                )
//////                .formLogin(form -> form
//////                        .loginPage("/login") // 기본 로그인 필요하면 위치
//////                        .defaultSuccessUrl("/articles") //로그인에 성공하면 위치
//////                )
//////                .logout(logout -> logout
//////                        .logoutSuccessUrl("/login") //로그인에 성공하면 위치
//////                        .invalidateHttpSession(true) //세션 만료
//////                )
//////                .csrf(csrf -> csrf.disable());
//////
//////        return http.build();
//////    }
//////
//////    @Bean
//////    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//////        return authenticationConfiguration.getAuthenticationManager();
//////    }
//////
//////    @Bean
//////    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//////        return new BCryptPasswordEncoder();
//////    }
//////}
////
////
////
//////package com.todoslave.feedme.config.security;
////
////
////import com.todoslave.feedme.login.Service.CustomOAuth2UserService;
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.security.authentication.AuthenticationManager;
////import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
////import org.springframework.security.config.annotation.web.builders.HttpSecurity;
////import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
////import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
////import org.springframework.security.config.http.SessionCreationPolicy;
////import org.springframework.security.core.userdetails.UserDetailsService;
////import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
////import org.springframework.security.web.SecurityFilterChain;
////import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
////import org.springframework.web.cors.CorsConfiguration;
////import org.springframework.web.cors.CorsConfigurationSource;
////import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
////import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
////
////import java.util.Collections;
////
////@Configuration
////@EnableWebSecurity
////public class WebSecurityConfig {
////
////    private final UserDetailsService userDetailsService;
////
////    public WebSecurityConfig(UserDetailsService userDetailsService) {
////        this.userDetailsService = userDetailsService;
////    }
////
////    @Bean
////    public BCryptPasswordEncoder passwordEncoder() {
////        return new BCryptPasswordEncoder();
////    }
////
////    @Bean
////    CorsConfigurationSource corsConfigurationSource() {
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        CorsConfiguration config = new CorsConfiguration();
////        config.setAllowedHeaders(Collections.singletonList("*"));
////        config.setAllowedMethods(Collections.singletonList("*"));
////        config.setAllowedOriginPatterns(Collections.singletonList("http://localhost:3000")); // 허용할 origin
////        config.setAllowCredentials(true);
////        source.registerCorsConfiguration("/**", config);
////        return source;
////    }
////
//////    @Bean
//////    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//////        http
//////                .httpBasic(httpBasicConfigurer -> httpBasicConfigurer.disable())
//////                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
//////                .csrf(AbstractHttpConfigurer::disable)
//////                .authorizeHttpRequests(authorize ->
//////                        authorize
//////                                .requestMatchers("/login", "/signup", "/user", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/users/**").permitAll()
//////                                .anyRequest().authenticated()
//////                )
//////                .formLogin(form -> form
//////                        .loginPage("/login")
//////                        .defaultSuccessUrl("/articles")
//////                )
//////                .logout(logout -> logout
//////                        .logoutSuccessUrl("/login")
//////                        .invalidateHttpSession(true)
//////                );
//////
//////        return http.build();
//////    }
////
////    //되는거
//////    @Bean
//////    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector, CustomOAuth2UserService customOAuth2UserService) throws Exception {
//////        http
//////
//////                           .csrf(AbstractHttpConfigurer::disable)
//////                .sessionManagement((sessionManagement) ->
//////                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//////                )
//////                .formLogin(AbstractHttpConfigurer::disable)
//////                .httpBasic(AbstractHttpConfigurer::disable)
//////                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
//////                        .requestMatchers(new MvcRequestMatcher(introspector, "/api/user")).permitAll()
//////                        .anyRequest().authenticated()
//////                )
//////                .oauth2Login(oauth2Login ->
//////                        oauth2Login.userInfoEndpoint(userInfoEndpointConfig ->
//////                                userInfoEndpointConfig.userService(customOAuth2UserService)))
//////                .formLogin(form -> form
//////                        .loginPage("/login") // 기본 로그인 필요하면 위치
//////
//////                )
//////        ;
//////        return http.build();
//////    }
////    @Bean
////    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector, CustomOAuth2UserService customOAuth2UserService) throws Exception {
////        http
////                .httpBasic(httpBasicConfigurer -> httpBasicConfigurer.disable())
////                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
////                .csrf(AbstractHttpConfigurer::disable)
////                .authorizeHttpRequests(authorize ->
////                        authorize
////                                .requestMatchers("/login", "/signup", "/user", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/users/**").permitAll()
////                                .requestMatchers(new MvcRequestMatcher(introspector, "/api/user")).permitAll()
////                                .anyRequest().authenticated()
////                )
////                .formLogin(form -> form
////                        .loginPage("/login")
////                        .defaultSuccessUrl("/login")
////                )
////                .logout(logout -> logout
////                        .logoutSuccessUrl("/login")
////                        .invalidateHttpSession(true)
////                )
////                .sessionManagement(sessionManagement ->
////                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////                )
////                .oauth2Login(oauth2Login ->
////                        oauth2Login.userInfoEndpoint(userInfoEndpointConfig ->
////                                userInfoEndpointConfig.userService(customOAuth2UserService))
////                );
////
////        return http.build();
////    }
////    @Bean
////    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
////        return authenticationConfiguration.getAuthenticationManager();
////    }
////}
//package com.todoslave.feedme.config.security;
//
//import com.todoslave.feedme.login.Service.CustomOAuth2UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
//
//import java.util.Collections;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class WebSecurityConfig {
//
//    private final CustomOAuth2UserService customOAuth2UserService;
//
//
//    @Bean //security를 적용할것 설절
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return web -> web.ignoring()
//                .requestMatchers("/error", "/favicon.ico");
//    }
//
//    @Bean // 암호화 친구
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() { // 의미 없고
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowedHeaders(Collections.singletonList("*"));
//        config.setAllowedMethods(Collections.singletonList("*"));
//        config.setAllowedOriginPatterns(Collections.singletonList("http://localhost:3000")); // 허용할 origin
//        config.setAllowCredentials(true);
//        source.registerCorsConfiguration("/**", config);
//        return source;
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
//        http
//                .httpBasic(httpBasicConfigurer -> httpBasicConfigurer.disable()) // 이게 폼
//                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource())) //CORS 설정
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(authorize ->
//                        authorize
//                                .requestMatchers("/login","/testsite" ,"/signup", "/user", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/users/**").permitAll() // 유저 설정
//                                .requestMatchers(new MvcRequestMatcher(introspector, "/api/user")).permitAll()
//                                .anyRequest().authenticated()
//                )
//
//                .oauth2Login(oauth2Login ->
//                        oauth2Login.userInfoEndpoint(userInfoEndpointConfig ->
//                                        userInfoEndpointConfig.userService(customOAuth2UserService))
//                                .loginPage("/login") // 소셜 로그인 페이지
//                                .successHandler(new SimpleUrlAuthenticationSuccessHandler("/templates/testsite.html")) // 로그인 성공 시 리디렉션 URL
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login")
//                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID")
//                        .permitAll()
//                )
//                .sessionManagement(sessionManagement ->
//                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                );
//
//        return http.build();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//}
