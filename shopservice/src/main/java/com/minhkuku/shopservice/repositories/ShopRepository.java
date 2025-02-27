package com.minhkuku.shopservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.minhkuku.shopservice.entities.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop, String> {
}