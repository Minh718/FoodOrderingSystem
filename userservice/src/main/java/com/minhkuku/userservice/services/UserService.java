package com.minhkuku.userservice.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.minhkuku.userservice.constants.PredefinedRole;
import com.minhkuku.userservice.dtos.reponse.InfoUserResponse;
import com.minhkuku.userservice.dtos.request.ShopRequest;
import com.minhkuku.userservice.entities.Role;
import com.minhkuku.userservice.entities.User;
import com.minhkuku.userservice.exceptions.AppException;
import com.minhkuku.userservice.exceptions.ErrorCode;
import com.minhkuku.userservice.mappers.UserMapper;
import com.minhkuku.userservice.repositories.RoleRepository;
import com.minhkuku.userservice.repositories.UserRepository;
import com.minhkuku.userservice.repositories.httpClient.ShopClient;

import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    ShopClient shopClient;

    public InfoUserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findById(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toInfoUserResponse(user);
    }

    public String registerStore(MultipartFile imageUrl, ShopRequest request) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findById(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        request.setId(user.getId());
        try {
            shopClient.createShop(imageUrl, request);
            Role role = roleRepository
                    .findByName(PredefinedRole.SHOP_ROLE)
                    .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
            user.getRoles().add(role);
            userRepository.save(user);
            return "Register success";
        } catch (FeignException e) {
            throw new AppException(ErrorCode.SHOP_SERVICE_ERROR);
        } catch (Exception e) {
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
