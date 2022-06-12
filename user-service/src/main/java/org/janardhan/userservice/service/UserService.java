package org.janardhan.userservice.service;

import org.janardhan.userservice.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService  extends UserDetailsService {
  UserDto createUser(UserDto userdetails);
  UserDto getUserDetailsByEmail(String email);
}
