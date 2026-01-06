package com.example.productioReady.productioReady.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/posts", "/error", "/auth/**").permitAll()
                        .anyRequest().authenticated())
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessionConfig -> sessionConfig
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return httpSecurity.build();

    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}




// The WebSecurityConfig class is a configuration class in a Spring Boot application that sets up web security using Spring Security. It is annotated with @Configuration, indicating that it contains bean definitions, and @EnableWebSecurity, which enables Spring Security's web security support and provides integration with the Spring MVC framework.
// By creating this class, you can customize the security settings for your web application, such as defining access rules, configuring authentication mechanisms, and setting up security filters. However, in the provided code snippet, the class is currently empty and does not contain any specific security configurations. You can add methods and configurations to this class to tailor the security settings to your application's requirements.
// For example, you might want to override methods to configure HTTP security, authentication providers, or user details services, depending on your application's needs.
// Overall, the WebSecurityConfig class serves as a central place to manage and customize the security aspects of your Spring Boot web application.
// The WebSecurityConfig class is a configuration class in a Spring Boot application that sets up web security using Spring Security. It is annotated with @Configuration, indicating that it contains bean definitions, and @EnableWebSecurity, which enables Spring Security's web security support and provides integration with the Spring MVC framework.
// By creating this class, you can customize the security settings for your web application, such as defining access rules, configuring authentication mechanisms, and setting up security filters. However, in the provided code snippet, the class is currently empty and does not contain any specific security configurations. You can add methods and configurations to this class to tailor the security settings to your application's requirements.
// For example, you might want to override methods to configure HTTP security, authentication providers, or user details services, depending on your application's needs. Overall, the WebSecurityConfig class serves as a central place to manage and customize the security aspects of your Spring Boot web application.
// The WebSecurityConfig class is a configuration class in a Spring Boot application that sets up web security using Spring Security. It is annotated with @Configuration, indicating that it contains bean definitions, and @EnableWebSecurity, which enables Spring Security's web security support and provides integration with the Spring MVC framework.
// By creating this class, you can customize the security settings for your web application, such as defining access rules, configuring authentication mechanisms, and setting up security filters. However, in the provided code snippet, the class is currently empty and does not contain any specific security configurations. You can add methods and configurations to this class to tailor the security settings to your application's requirements.



//- Purpose: configure HTTP security for the application (authorization rules, CSRF, session policy, filter chain).
//- Current behavior: permits /api/auth/**, requires authentication for other endpoints, disables CSRF, sets session policy to STATELESS (suitable for JWT APIs).
//- Visibility: method is package-private. Prefer public for Spring proxy compatibility and clarity.
//- CSRF: disabling is fine for stateless token APIs; do not disable if you rely on cookie-based auth or browser forms.
//- Session policy: STATELESS is correct for JWT; if using sessions, choose IF_REQUIRED or default.
//- CORS: add explicit CORS config if the frontend is served from a different origin.
//- Exception handling: add exceptionHandling() to customize unauthorized/forbidden responses (clear JSON responses).
//- Filters: if using JWT, register a JwtAuthenticationFilter and add it before UsernamePasswordAuthenticationFilter.
//- Matcher compatibility: confirm requestMatchers usage matches your Spring Security version (Spring Security 6 uses requestMatchers).
//- Whitelisting: include docs/swagger and health endpoints in permit list (e.g., /swagger-ui/**, /v3/api-docs/**, /actuator/health).
//- Authentication manager: declare and inject an AuthenticationManager or AuthenticationProvider if you need custom auth logic.
//- Password encoding: ensure a PasswordEncoder bean is defined if using username/password flows.
//- Tests: add integration tests that exercise permitted and protected endpoints; test with and without valid JWT.
//- Logging & metrics: log security failures at appropriate level and consider metrics for auth failures.
//- TODOs: rate limiting on auth endpoints, audit logging for sensitive actions, refresh token handling if required.
//Short checklist before merge: change method visibility to public, add CORS + exception handling, wire JWT filter if present, add tests for auth flow.

