package org.siriusxi.htec.fa.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author mohamed_taman
 */
@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class CommentPK implements Serializable {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(nullable = false)
  private int id;

  @NonNull
  @Basic(optional = false)
  @Column(name = "CITY_ID", nullable = false)
  private int cityId;
}
