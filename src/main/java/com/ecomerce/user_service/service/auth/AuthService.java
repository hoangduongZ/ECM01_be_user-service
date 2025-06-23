package com.ecomerce.user_service.service.auth;

import com.ecomerce.user_service.dto.request.RegisterRequest;
import com.ecomerce.user_service.entity.User;
import com.ecomerce.user_service.exception.DataNotFoundException;
import com.ecomerce.user_service.kafka.event.GenericEventWrapper;
import com.ecomerce.user_service.kafka.event.UserSummaryEvent;
import com.ecomerce.user_service.kafka.producer.UserEventProducer;
import com.ecomerce.user_service.mapper.UserMapper;
import com.ecomerce.user_service.repository.UserRepository;
import com.ecomerce.user_service.util.EventUtil;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.ecomerce.user_service.entity.User.UserStatus.inactive;

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
        UserSummaryEvent event = userMapper.entityToUserSummaryEvent(user);
        GenericEventWrapper<UserSummaryEvent> eventWrapper = EventUtil.createEvent("UserCreated",
                event, "user-service", null);
        userEventProducer.sendUserCreatedEvent(eventWrapper);
    }

    @Override
    public void deactivateUser(UUID uuid) {
        User user= userRepository.findByUuid(uuid).orElseThrow(
                ()-> new DataNotFoundException("User not found!"));
        user.setStatus(inactive);
        userRepository.save(user);
        System.out.println("handle deactivate user!");
    }
}
