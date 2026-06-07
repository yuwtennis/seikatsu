package org.example.dags.realestate.mlit;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

/** Response. */
public class Response {
  /** Status. */
  @JsonProperty private String status;

  /** Data. */
  @JsonProperty private ArrayList<Mlit> data;

  public String getStatus() {
    return status;
  }

  public ArrayList<Mlit> getData() {
    return data;
  }
}
