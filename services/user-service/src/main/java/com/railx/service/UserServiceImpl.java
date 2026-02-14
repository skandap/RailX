package com.railx.service;

import com.railx.entity.*;
import com.railx.exception.CredentialException;
import com.railx.exception.NoDataException;
import com.railx.exception.OtpException;
import com.railx.repository.OTPRepository;
import com.railx.repository.UserRepository;
import com.railx.service.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public OTPRepository otpRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponse register(UserInputDTO userInputDTO) {
        UserEntity user = userRepository.findByEmail(userInputDTO.getEmail());
        if (user != null) {
            throw new CredentialException("User is already registered");
        }
        UserEntity entity = UserMapper.mapToEntity(userInputDTO);
        entity.setPassword(passwordEncoder.encode(userInputDTO.getPassword()));
        userRepository.save(entity);
        return UserMapper.mapToUserData(entity);
    }


    @Override
    public VerificationResponse verifyOtp(UUID userId, String otp) {

        OTPVerificationDto otpEntity = otpRepository
                .findTopByUserIdOrderByCreatedAtDesc(userId)
                .orElseThrow(() -> new NoDataException("User not found"));

        otpEntity.setAttempts(otpEntity.getAttempts() + 1);
        if (otpEntity.getAttempts() >= 3) {
            throw new OtpException("OTP attempts exceeded");
        }
        if (!otpEntity.getOtp().equals(otp)) {
            otpRepository.save(otpEntity);
            return VerificationResponse.builder()
                    .status(UserStatus.PENDING_ACTIVATION.name())
                    .verified("Invalid OTP")
                    .userId(userId)
                    .build();
        }

        if (otpEntity.isVerified()) {
            throw new OtpException("OTP already verified");
        }
        if (otpEntity.getExpiryTime().isBefore(LocalDateTime.now())) {
            otpEntity.setVerified(false);
            throw new OtpException("OTP expired");
        }
        otpEntity.setVerified(true);
        otpRepository.save(otpEntity);

        return VerificationResponse.builder()
                .status(UserStatus.ACTIVE.name())
                .verified("OTP verified successfully")
                .userId(userId)
                .build();
    }


    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        UserEntity user = userRepository.findByEmail(loginRequest.getEmail());
        if (user == null) {
            throw new NoDataException("User not found");
        }

        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new CredentialException("Invalid credentials");
        }

        Optional<OTPVerificationDto> latestOtp =
                otpRepository.findTopByUserIdOrderByCreatedAtDesc(user.getUserId());

        if (latestOtp.isPresent() && latestOtp.get().isVerified()) {
            user.setStatus(UserStatus.ACTIVE.name());
            userRepository.save(user);
            return LoginResponse.builder()
                    .userId(user.getUserId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .role(user.getRole())
                    .status(UserStatus.ACTIVE.name())
                    .otpRequired(false)
                    .build();
        }

        String otp = generateOtp();

        OTPVerificationDto otpEntity = OTPVerificationDto.builder()
                .userId(user.getUserId())
                .otp(otp)
                .attempts(0)
                .verified(false)
                .createdAt(LocalDateTime.now())
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .build();

        otpRepository.save(otpEntity);

        logger.info("OTP generated (expires in 5 mins): {}", otp);

        return UserMapper.mapToLogin(user);
    }


    private String generateOtp() {
        return String.format("%06d", new SecureRandom().nextInt(1_000_000));
    }


    @Override
    public UserResponse fetchOwnUserById(UUID userId) {
        OTPVerificationDto otpEntity = otpRepository
                .findTopByUserIdOrderByCreatedAtDesc(userId)
                .orElseThrow(() -> new NoDataException("user not found"));
        if (otpEntity.getExpiryTime().isBefore(LocalDateTime.now())) {
            otpEntity.setVerified(false);
            otpRepository.save(otpEntity);
            throw new OtpException("OTP expired");
        }
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new NoDataException("No user found for the id"));
        return UserMapper.mapToUserData(user);
    }


    @Override
    public List<UserResponse> fetchAllUsers() {
        List<UserEntity> user = userRepository.findAll();
        return user.stream().map(UserMapper::mapToUserData)
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public String updateOwnUser(UserUpdateDTO userInputDTO, UUID userId) {
        OTPVerificationDto otpEntity = otpRepository
                .findTopByUserIdOrderByCreatedAtDesc(userId)
                .orElseThrow(() -> new NoDataException("user not found"));
        if (otpEntity.getExpiryTime().isBefore(LocalDateTime.now())) {
            otpEntity.setVerified(false);
            otpRepository.save(otpEntity);
            throw new OtpException("OTP expired");
        }
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new NoDataException("No user found"));
        user.setName(userInputDTO.getName());
        user.setPhoneNumber(userInputDTO.getPhoneNumber());
        userRepository.save(user);
        return "Updated Sucessfully";
    }

    @Override
    public UserResponse updateUserRoleStatus(UUID userId, AdminUpdateUserDTO updateUserDTO) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new NoDataException("No data found"));
        user.setStatus(updateUserDTO.getStatus());
        user.setRole(updateUserDTO.getRole());
        userRepository.save(user);
        return UserMapper.mapToUserData(user);
    }

    @Override
    public DeleteUserResponse deleteUser(UUID userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new NoDataException("No user found"));
        user.setStatus(UserStatus.DELETED.name());
        userRepository.save(user);
        return DeleteUserResponse.builder().userId(userId).status(user.getStatus()).build();
    }
}
