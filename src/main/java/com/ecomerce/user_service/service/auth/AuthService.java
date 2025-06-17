package com.ecomerce.user_service.service.auth;

import com.ecomerce.user_service.dto.request.LoginRequest;
import com.ecomerce.user_service.dto.request.RegisterRequest;
import com.ecomerce.user_service.entity.User;
import com.ecomerce.user_service.kafka.event.UserCreatedEvent;
import com.ecomerce.user_service.kafka.producer.UserEventProducer;
import com.ecomerce.user_service.mapper.UserMapper;
import com.ecomerce.user_service.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import java.time.ZonedDateTime;

@Service
public class AuthService implements IAuthService {

    private final UserRepository userRepository;
    private final UserEventProducer userEventProducer;
    private final UserMapper userMapper;

    public AuthService(UserRepository userRepository, UserEventProducer userEventProducer, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userEventProducer = userEventProducer;
        this.userMapper = userMapper;
    }

    @Override
    public void registerUser(RegisterRequest request) {
        User user = userMapper.toEntity(request);
        userRepository.save(user);
        UserCreatedEvent event = new UserCreatedEvent(user.getId(), user.getEmail(), user.getFirstName()
                , user.getLastName(), user.getUserName(),user.getRole().getName(), ZonedDateTime.now());
        userEventProducer.sendUserCreatedEvent(event);
    }
}
