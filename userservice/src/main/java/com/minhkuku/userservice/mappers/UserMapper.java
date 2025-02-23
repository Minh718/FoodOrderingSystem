package com.minhkuku.userservice.mappers;

import org.mapstruct.Mapper;

import com.minhkuku.userservice.dtos.reponse.AuthResponse;
import com.minhkuku.userservice.dtos.reponse.InfoUserResponse;
import com.minhkuku.userservice.dtos.reponse.UserCreationRequest;
import com.minhkuku.userservice.dtos.reponse.UserSignupResponse;
import com.minhkuku.userservice.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserSignupResponse toUserSignupResponse(User user);

    AuthResponse toAuthResponse(User user);

    InfoUserResponse toInfoUserResponse(User user);
}
