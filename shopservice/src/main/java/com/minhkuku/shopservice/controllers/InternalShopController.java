package com.minhkuku.shopservice.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.minhkuku.shopservice.dtos.request.ShopRequest;
import com.minhkuku.shopservice.dtos.response.ApiResponse;
import com.minhkuku.shopservice.entities.Shop;
import com.minhkuku.shopservice.services.ShopService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/internal/shops")
@RequiredArgsConstructor
public class InternalShopController {
    ShopService shopService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<Shop> createShop(
            @RequestPart("imageUrl") MultipartFile imageUrl,
            @RequestPart("data") ShopRequest request) {

        return ApiResponse.<Shop>builder().result(shopService.createShop(request, imageUrl))
                .build();
    }
}