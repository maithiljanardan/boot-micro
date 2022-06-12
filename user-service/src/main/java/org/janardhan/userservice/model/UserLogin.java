package org.janardhan.userservice.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserLogin {
  private String email;
  private String password;

}
