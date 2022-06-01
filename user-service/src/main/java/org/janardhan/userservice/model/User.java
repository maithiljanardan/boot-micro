package org.janardhan.userservice.model;


import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class User {
  @NotNull(message = "First name cannot be left blank")
  @Size(min = 2, message = "First name cannot be of less than 2 characters")
  private String firstName;
  private String lastName;
  private String password;
  private String email;
}
