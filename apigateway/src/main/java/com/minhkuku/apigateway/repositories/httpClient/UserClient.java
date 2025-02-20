package com.minhkuku.apigateway.repositories.httpClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

import com.minhkuku.apigateway.dtos.request.IntrospectRequest;
import com.minhkuku.apigateway.dtos.response.ApiResponse;
import com.minhkuku.apigateway.dtos.response.IntrospectResponse;

import reactor.core.publisher.Mono;

@FeignClient(name = "user-service")
public interface UserClient {
    @PostMapping(value = "/auth/introspect", produces = MediaType.APPLICATION_JSON_VALUE)
    Mono<ApiResponse<IntrospectResponse>> introspect(@RequestBody IntrospectRequest request);
}
