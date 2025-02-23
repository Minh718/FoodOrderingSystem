package com.minhkuku.userservice.controllers;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minhkuku.userservice.dtos.reponse.ApiResponse;
import com.minhkuku.userservice.dtos.reponse.AuthResponse;
import com.minhkuku.userservice.dtos.reponse.IntrospectResponse;
import com.minhkuku.userservice.dtos.reponse.UserCreationRequest;
import com.minhkuku.userservice.dtos.reponse.UserSignupResponse;
import com.minhkuku.userservice.dtos.request.IntrospectRequest;
import com.minhkuku.userservice.dtos.request.OtpRequest;
import com.minhkuku.userservice.dtos.request.UserSigninRequest;
import com.minhkuku.userservice.services.AuthenticationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder().result(result).build();
    }

    @PostMapping("/signup")
    ApiResponse<UserSignupResponse> singnup(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserSignupResponse>builder()
                .result(authenticationService.createUser(request))
                .build();
    }

    @PostMapping("/signup/otp")
    ApiResponse<AuthResponse> confirmSingnup(@RequestBody @Valid OtpRequest request) {
        return ApiResponse.<AuthResponse>builder()
                .result(authenticationService.confirmSignup(request))
                .build();
    }

    @PostMapping("/signin")
    ApiResponse<AuthResponse> singnin(@RequestBody @Valid UserSigninRequest request) {
        return ApiResponse.<AuthResponse>builder()
                .result(authenticationService.userSignin(request))
                .build();
    }
}
