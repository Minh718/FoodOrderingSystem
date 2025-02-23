package com.minhkuku.userservice.dtos.request;

import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserSigninRequest {
    @Size(min = 10, message = "PHONE_INVALID")
    String phone;

    @Size(min = 6, message = "INVALID_PASSWORD")
    String password;
}
