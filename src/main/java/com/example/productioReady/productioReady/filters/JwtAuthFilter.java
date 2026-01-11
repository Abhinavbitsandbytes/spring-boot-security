package com.example.productioReady.productioReady.filters;

import com.example.productioReady.productioReady.entities.UserEntity;
import com.example.productioReady.productioReady.services.JwtService;
import com.example.productioReady.productioReady.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Get Authorization header and validate JWT
        try {
            final String requestTokenHeader = request.getHeader("Authorization");
            if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = requestTokenHeader.split("Bearer ")[1];

            Long userId = jwtService.getUserIdFromToken(token);
// set authentication if userId is valid && SecurityContext is not set
            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserEntity user = userService.getUserById(userId);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(user, null, null);
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            handlerExceptionResolver.resolveException(request, response, null, ex);
        }
    }

}

// before handlerExceptionResolver was added when exception occurred in the filter the response was not being sent properly that's why we added handlerExceptionResolver to handle exceptions properly
// earlier it was throwing exception like this in postman
//{
//        "timestamp": "2026-01-11T10:39:57.203Z",
//        "status": 500,
//        "error": "Internal Server Error",
//        "path": "/posts/1"
//        }
//  now after adding handlerExceptionResolver it is throwing proper exception like this
//{
//        "error": "JWT expired 723678 milliseconds ago at 2026-01-11T10:31:02.000Z. Current time: 2026-01-11T10:43:05.678Z. Allowed clock skew: 0 milliseconds.",
//        "statusCode": "401 UNAUTHORIZED",
//        "timeStamp": "2026-01-11T16:13:05.7334016"
//        }

// handlerExceptionResolver takes 4 parameters HttpServletRequest request, HttpServletResponse response,
// Object handler, Exception ex
// we are passing null for handler because we don't have handler object in filter
// The JwtAuthFilter is a custom filter that handles JWT authentication for incoming HTTP requests.
// It intercepts requests, extracts the JWT token from the Authorization header, validates it,
// and sets the authentication in the SecurityContext if the token is valid.
// In this code, we have two dependencies injected via constructor injection using @RequiredArgsConstructor:
// 1. JwtService: This service is responsible for handling JWT-related operations, such as extracting
// the user ID from the token.
// 2. UserService: This service is used to retrieve user details based on the user ID extracted from the JWT token.
// The doFilterInternal method is overridden to implement the filtering logic. It performs the following steps:
// 1. It retrieves the Authorization header from the incoming HTTP request.
// 2. It checks if the header is null or does not start with "Bearer ". If so, it continues the filter chain
// without further processing.
// 3. It extracts the JWT token from the Authorization header.
// 4. It uses the JwtService to extract the user ID from the token.
// 5. If a user ID is found and the SecurityContext is not already set, it retrieves the corresponding UserEntity using the UserService.
// 6. It creates a UsernamePasswordAuthenticationToken with the user details and sets it in the SecurityContext.
// 7. Finally, it continues the filter chain, allowing the request to proceed.
// Additionally, we have added a HandlerExceptionResolver to handle exceptions that may occur during the filtering process. If an exception is thrown, it is resolved using the handlerExceptionResolver, which ensures that proper error responses are sent back to the client.
// Overall,

// here we have created a JwtAuthFilter that extends OncePerRequestFilter. This filter intercepts incoming HTTP requests, extracts the JWT token from the Authorization header, validates it, and sets the authentication in the SecurityContext if the token is valid. This allows us to secure our endpoints and ensure that only authenticated users can access them.
// Now, when you make requests to secured endpoints, the JwtAuthFilter will validate the JWT token and authenticate the user accordingly.
// Make sure to register this filter in your security configuration to ensure it gets applied to incoming requests.
// For example, in your SecurityConfig class, you can add the filter like this:
// http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
// This ensures that the JwtAuthFilter is executed before the default UsernamePasswordAuthenticationFilter provided by Spring Security.

// we have done following line by line explanation of the code above
// 1. The class is annotated with @Component, making it a Spring-managed bean,
//    and @RequiredArgsConstructor, which generates a constructor for the final fields.
// 2. It extends OncePerRequestFilter, ensuring that the filter is executed once per request.
// 3. The doFilterInternal method is overridden to implement the filtering logic.
// 4. It retrieves the Authorization header from the incoming HTTP request.
// 5. It checks if the header is null or does not start with "Bearer ". If so, it continues the filter chain without further processing.
// 6. It extracts the JWT token from the Authorization header.
// 7. It uses the JwtService to extract the user ID from the token.
// 8. If a user ID is found &&  SecurityContext is not set, it retrieves the corresponding UserEntity using the UserService.
// 9. It creates a UsernamePasswordAuthenticationToken with the user details and sets it in the SecurityContext.
// 10. Finally, it continues the filter chain, allowing the request to proceed.

// WebAuthenticationDetailsSource is a class in Spring Security that provides additional details about the authentication request. It is used to build the details object that can be associated with an authentication token, such as UsernamePasswordAuthenticationToken.
// In the context of the provided code, WebAuthenticationDetailsSource is used to create an instance of WebAuthenticationDetails, which contains information about the current HTTP request, such as the remote address and session ID. This information can be useful for auditing and logging purposes, as well as for implementing additional security measures based on the request context.
// By calling buildDetails(request) on the WebAuthenticationDetailsSource instance, we create a WebAuthenticationDetails object that is then set as the details of the UsernamePasswordAuthenticationToken. This allows the authentication token to carry additional information about the request, which can be accessed later in the authentication process or during authorization checks.
// In summary, WebAuthenticationDetailsSource is a utility class that helps create detailed information about the authentication request, enhancing the context of the authentication token in Spring Security.

// UsernamePasswordAuthenticationToken is a class in Spring Security that represents an authentication request or an authenticated principal with a username and password. It is commonly used during the authentication process to encapsulate the user's credentials (username and password) when they attempt to log in.
// In the context of the provided code, the UsernamePasswordAuthenticationToken is created with the UserEntity object, which represents the authenticated user. The second parameter is set to null because we are not using credentials (password) for further authentication at this point. The third parameter is also set to null, indicating that there are no specific authorities or roles associated with this authentication token.
// By setting the UsernamePasswordAuthenticationToken in the SecurityContext, we inform Spring Security that the user has been successfully authenticated, allowing them to access secured resources based on their authentication status.
// In summary, UsernamePasswordAuthenticationToken is a way to represent the authentication state of a user in Spring Security, and it is used to facilitate the authentication process and manage user sessions.package com.example.productioReady.productioReady.filters;