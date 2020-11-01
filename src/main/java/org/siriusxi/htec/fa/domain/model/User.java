package org.siriusxi.htec.fa.domain.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

/**
 * Class represents User domain model, as JPA Entity.
 *
 * @author Mohamed Taman
 */
@Entity
@Table(
    catalog = "FLIGHTDB",
    schema = "PUBLIC",
    uniqueConstraints = {
      @UniqueConstraint(columnNames = {"USER_UUID"}),
      @UniqueConstraint(columnNames = {"USERNAME"})
    })
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = {"userUuid","username", "passkey","comments"})
public class User implements Serializable {

  enum Type {
    CLIENT,
    ADMIN
  }

  @Serial private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(nullable = false)
  private Integer id;

  @NonNull
  @Basic(optional = false)
  @Column(name = "USER_UUID", nullable = false, updatable = false)
  private String userUuid;

  @NonNull
  @Basic(optional = false)
  @Column(name = "FIRST_NAME", nullable = false, length = 100)
  private String firstName;

  @NonNull
  @Basic(optional = false)
  @Column(name = "LAST_NAME", nullable = false, length = 100)
  private String lastName;

  @NonNull
  @Basic(optional = false)
  @Column(nullable = false)
  private Type type;

  @NonNull
  @Email(message = "The accepted value is a valid email.")
  @Basic(optional = false)
  @Column(nullable = false, length = 150)
  private String username;
  
  @NonNull
  @Min(value = 10, message = "Minimum password length is 10")
  @Basic(optional = false)
  @Column(nullable = false)
  private String passkey;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = LAZY)
  private List<Comment> comments;
}
