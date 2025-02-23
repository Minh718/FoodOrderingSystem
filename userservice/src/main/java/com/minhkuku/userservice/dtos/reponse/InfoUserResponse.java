package com.minhkuku.userservice.dtos.reponse;

import java.util.Set;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InfoUserResponse {
    String id;
    String phone;
    Set<RoleResponse> roles;
}
