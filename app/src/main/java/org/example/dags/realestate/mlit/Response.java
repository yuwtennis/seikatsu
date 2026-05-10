package org.example.dags.realestate.mlit;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

public class Response {
  @JsonProperty private String status;

  @JsonProperty private ArrayList<Mlit> data;

  public String getStatus() {
    return status;
  }

  public ArrayList<Mlit> getData() {
    return data;
  }
}
