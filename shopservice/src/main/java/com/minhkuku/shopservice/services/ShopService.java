package com.minhkuku.shopservice.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.minhkuku.shopservice.dtos.request.ShopRequest;
import com.minhkuku.shopservice.entities.Shop;
import com.minhkuku.shopservice.mappers.ShopMapper;
import com.minhkuku.shopservice.repositories.ShopRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final FileStorageService fileStorageService;
    private final ShopMapper shopMapper;

    public Shop createShop(ShopRequest request, MultipartFile imageUrl) {
        String imageUrlPath = fileStorageService.uploadFile(imageUrl);

        Shop shop = shopMapper.toShop(request);
        shop.setImageUrl(imageUrlPath);
        shopRepository.save(shop);
        return shop;
    }
}
