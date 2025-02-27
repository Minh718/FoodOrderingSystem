package com.minhkuku.userservice.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.minhkuku.userservice.dtos.reponse.ApiResponse;
import com.minhkuku.userservice.dtos.reponse.InfoUserResponse;
import com.minhkuku.userservice.dtos.request.ShopRequest;
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

    @PostMapping(value = "/register/store", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ApiResponse<String> registerStore(
            @RequestParam("imageUrl") MultipartFile imageUrl, @ModelAttribute ShopRequest request) {
        return ApiResponse.<String>builder()
                .result(userService.registerStore(imageUrl, request))
                .build();
    }
}
