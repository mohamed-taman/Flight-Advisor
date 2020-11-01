package org.siriusxi.htec.fa.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author mohamed_taman
 */
@Entity
@Table(name = "CITY_COMMENT", catalog = "FLIGHTDB", schema = "PUBLIC")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Comment implements Serializable {

  @Serial private static final long serialVersionUID = -4628882357786781599L;

  @EmbeddedId protected CommentPK commentPK;

  @NonNull
  @Basic(optional = false)
  @Column(nullable = false, length = 1000)
  private String description;

  @Basic(optional = false)
  @Column(name = "CREATED_AT", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt = new Date();

  @Column(name = "UPDATED_ON")
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedOn;

  @JoinColumn(
      name = "CITY_ID",
      referencedColumnName = "ID",
      nullable = false,
      insertable = false,
      updatable = false)
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private City city;

  @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private User user;

  public Comment(int cityId, String description) {
    this.setCommentPK(new CommentPK(cityId));
    this.description = description;
  }
}
