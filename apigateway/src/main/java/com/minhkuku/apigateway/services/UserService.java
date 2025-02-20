package com.minhkuku.apigateway.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import com.minhkuku.apigateway.dtos.request.IntrospectRequest;
import com.minhkuku.apigateway.dtos.response.ApiResponse;
import com.minhkuku.apigateway.dtos.response.IntrospectResponse;
import com.minhkuku.apigateway.repositories.httpClient.UserClient;

import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserClient userClient;

    public Mono<ApiResponse<IntrospectResponse>> introspect(String token) {
        return userClient.introspect(IntrospectRequest.builder()
                .token(token)
                .build());
    }
}
