package com.minhkuku.userservice.entities;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.*;

import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "phone", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String phone;

    String password;

    String otp;

    @Default
    LocalDateTime createAt = LocalDateTime.now();

    @Column(name = "email_verified", nullable = false, columnDefinition = "boolean default false")
    boolean verified;

    @ManyToMany
    Set<Role> roles;
}
