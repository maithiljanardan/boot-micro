package org.janardhan.userservice.service;

import org.janardhan.userservice.data.UserEntity;
import org.janardhan.userservice.data.UserRepository;
import org.janardhan.userservice.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDto createUser(UserDto userDetails) {
    userDetails.setUserId(UUID.randomUUID().toString());
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
    userEntity.setEncryptedPassword("test");
    userRepository.save(userEntity);
    return userDetails;
  }
}
