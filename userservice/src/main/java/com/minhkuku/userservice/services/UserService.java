package com.minhkuku.userservice.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.minhkuku.userservice.dtos.reponse.InfoUserResponse;
import com.minhkuku.userservice.entities.User;
import com.minhkuku.userservice.exceptions.AppException;
import com.minhkuku.userservice.exceptions.ErrorCode;
import com.minhkuku.userservice.mappers.UserMapper;
import com.minhkuku.userservice.repositories.UserRepository;

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
    UserMapper userMapper;

    public InfoUserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findById(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toInfoUserResponse(user);
    }
}
