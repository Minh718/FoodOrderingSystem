package com.minhkuku.shopservice.dtos.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShopRequest {

    private String id;

    private String name;

    private String location;
}
