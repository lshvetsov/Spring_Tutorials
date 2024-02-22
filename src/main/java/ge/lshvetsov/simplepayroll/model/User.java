package ge.lshvetsov.simplepayroll.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
@Table(name = "Users")
public class User {

  @Id
  @GeneratedValue
  private Long id;
  private String externalId;
  private String username;
  private String email;
  @Column(unique = true)
  private String namespace;
  private Boolean isEnabled;
}
