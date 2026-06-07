package org.example.dags.realestate.mlit;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Mlit.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({
  @JsonSubTypes.Type(RealEstatesPrice.class),
  @JsonSubTypes.Type(StandardLandPrice.class)
})
public abstract class Mlit {}
