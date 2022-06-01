package org.janardhan.userservice.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UserDto implements Serializable {

  private static final long serialVersionUID = -7406090377737747746L;
  private String userId;
  private String firstName;
  private String lastName;
  private String password;
  private String email;
  private String encryptedPassword;
}
