package com.minhkuku.userservice.services;

import java.text.ParseException;
import java.util.HashSet;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.minhkuku.event.dto.NotificationEvent;
import com.minhkuku.userservice.constants.PredefinedRole;
import com.minhkuku.userservice.dtos.reponse.AuthResponse;
import com.minhkuku.userservice.dtos.reponse.IntrospectResponse;
import com.minhkuku.userservice.dtos.reponse.UserCreationRequest;
import com.minhkuku.userservice.dtos.reponse.UserSignupResponse;
import com.minhkuku.userservice.dtos.request.IntrospectRequest;
import com.minhkuku.userservice.dtos.request.OtpRequest;
import com.minhkuku.userservice.dtos.request.UserSigninRequest;
import com.minhkuku.userservice.entities.Role;
import com.minhkuku.userservice.entities.User;
import com.minhkuku.userservice.exceptions.AppException;
import com.minhkuku.userservice.exceptions.ErrorCode;
import com.minhkuku.userservice.mappers.UserMapper;
import com.minhkuku.userservice.repositories.RoleRepository;
import com.minhkuku.userservice.repositories.UserRepository;
import com.minhkuku.utils.OTPGenerator;
import com.nimbusds.jose.JOSEException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    JwtService jwtService;
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    KafkaTemplate<String, Object> kafkaTemplate;

    public UserSignupResponse createUser(UserCreationRequest request) {
        User user = userMapper.toUser(request);
        String otp = OTPGenerator.generateNumericOTP(6);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<Role> roles = new HashSet<>();

        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);

        user.setRoles(roles);
        user.setVerified(false);
        user.setOtp(otp);

        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        NotificationEvent notificationEvent = NotificationEvent.builder()
                .channel("SMS")
                .recipient(request.getPhone())
                .subject("Welcome to bookteria")
                .body("Your registration verification code is" + otp)
                .build();

        // Publish message to kafka
        kafkaTemplate.send("notify-sms-register", notificationEvent);

        return userMapper.toUserSignupResponse(user);
    }

    public AuthResponse userSignin(UserSigninRequest request) {
        User user = userRepository
                .findByPhone(request.getPhone())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!authenticated) throw new AppException(ErrorCode.UNAUTHENTICATED);

        return mapUserToAuthResponse(user);
    }

    public AuthResponse confirmSignup(OtpRequest request) {
        User user = userRepository
                .findByPhone(request.getPhone())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if (user.getOtp() != request.getOtp()) throw new AppException(ErrorCode.OTP_INCORRECT);
        user.setVerified(true);
        userRepository.save(user);
        return mapUserToAuthResponse(user);
    }

    private AuthResponse mapUserToAuthResponse(User user) {
        AuthResponse userResponse = userMapper.toAuthResponse(user);
        userResponse.setToken(jwtService.generateToken(user));
        return userResponse;
    }

    public IntrospectResponse introspect(IntrospectRequest request) {
        var token = request.getToken();
        boolean isValid = true;

        try {
            jwtService.verifyToken(token, false);
        } catch (AppException | JOSEException | ParseException e) {
            isValid = false;
        }

        return IntrospectResponse.builder().valid(isValid).build();
    }
}
