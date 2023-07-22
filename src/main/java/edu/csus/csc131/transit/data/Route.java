package edu.csus.csc131.transit.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Route {
  @Id
  private String id;
  private String shortName;
  private String longName;

  public Route() {
    super();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public String getLongName() {
    return longName;
  }

  public void setLongName(String longName) {
    this.longName = longName;
  }

  @Override
  public String toString() {
    return "Route [id=" + id + ", shortName=" + shortName + ", longName=" + longName + "]";
  }

}
