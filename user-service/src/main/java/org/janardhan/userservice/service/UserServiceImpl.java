package org.janardhan.userservice.service;

import org.janardhan.userservice.data.UserEntity;
import org.janardhan.userservice.data.UserRepository;
import org.janardhan.userservice.dto.UserDto;
import org.janardhan.userservice.model.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {


  private UserRepository userRepository;
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDto createUser(UserDto userDetails) {
    userDetails.setUserId(UUID.randomUUID().toString());
    userDetails.setEncryptedPassword(passwordEncoder.encode(userDetails.getPassword()));
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
    userRepository.save(userEntity);

    UserDto returnUser = modelMapper.map(userEntity, UserDto.class);
    return returnUser;
  }
}
