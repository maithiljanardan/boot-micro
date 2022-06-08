package org.janardhan.userservice.controller;

import org.janardhan.userservice.data.UserEntity;
import org.janardhan.userservice.dto.UserDto;
import org.janardhan.userservice.model.ResponseUser;
import org.janardhan.userservice.model.User;
import org.janardhan.userservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private Environment environment;

  @Autowired
  private UserService userService;

  @GetMapping("/status/check")
  public String status() {
    return "running on " + environment.getProperty("local.server.port");
  }

  @PostMapping
  public ResponseEntity<ResponseUser> registerUser(@RequestBody User user){

    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    UserDto userDto = modelMapper.map(user, UserDto.class);
    UserDto returnedUserDto =userService.createUser(userDto);
    ResponseUser responseUser = modelMapper.map(returnedUserDto, ResponseUser.class);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
  }
}
