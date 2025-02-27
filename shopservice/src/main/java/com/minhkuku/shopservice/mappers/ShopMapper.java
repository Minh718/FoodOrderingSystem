package com.minhkuku.shopservice.mappers;

import org.mapstruct.Mapper;

import com.minhkuku.shopservice.dtos.request.ShopRequest;
import com.minhkuku.shopservice.entities.Shop;

@Mapper(componentModel = "spring")
public interface ShopMapper {
    Shop toShop(ShopRequest shop);
}
