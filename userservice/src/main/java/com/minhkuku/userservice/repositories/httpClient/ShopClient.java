package com.minhkuku.userservice.repositories.httpClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.minhkuku.userservice.configurations.AuthenticationRequestInterceptor;
import com.minhkuku.userservice.dtos.reponse.ApiResponse;
import com.minhkuku.userservice.dtos.request.ShopRequest;

@FeignClient(
        name = "shop-service",
        configuration = {AuthenticationRequestInterceptor.class})
public interface ShopClient {
    @PostMapping(
            value = "/internal/shops/create",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ApiResponse<ShopRequest> createShop(
            @RequestPart("imageUrl") MultipartFile imageUrl, @RequestPart("data") ShopRequest request);
}
