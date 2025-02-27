package com.minhkuku.menuservice.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.minhkuku.menuservice.services.MenuService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MenuController {
    MenuService menuService;

    public getAllMenuOfShop ()
}