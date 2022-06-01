package org.janardhan.userservice.data;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

  private static final long serialVersionUID = 4216385281053491892L;
  @Id
  @GeneratedValue
  private Long id;
  @Column(nullable = false, unique = true)
  private String userId;
  @Column(nullable = false, length = 50)
  private String firstName;
  @Column(nullable = false, length = 50)
  private String lastName;
  @Column(nullable = false, length = 120, unique = true)
  private String email;
  @Column(nullable = false, unique = true)
  private String encryptedPassword;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    UserEntity that = (UserEntity) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
