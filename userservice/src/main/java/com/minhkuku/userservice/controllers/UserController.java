package com.minhkuku.userservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minhkuku.userservice.dtos.reponse.ApiResponse;
import com.minhkuku.userservice.dtos.reponse.InfoUserResponse;
import com.minhkuku.userservice.services.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    UserService userService;

    @GetMapping("/my-info")
    ApiResponse<InfoUserResponse> getMyInfo() {
        return ApiResponse.<InfoUserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }
}
