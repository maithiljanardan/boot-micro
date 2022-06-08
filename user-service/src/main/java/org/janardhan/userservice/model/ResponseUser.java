package org.janardhan.userservice.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ResponseUser {
  private String firstName;
  private String lastName;
  private String userId;
  private String email;
}
