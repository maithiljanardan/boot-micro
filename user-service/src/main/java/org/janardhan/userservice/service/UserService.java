package org.janardhan.userservice.service;

import org.janardhan.userservice.dto.UserDto;

public interface UserService {
  UserDto createUser(UserDto userdetails);
}
