package edu.csus.csc131.transit.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Transfer {
  @Id
  private String id;
  private String fromStopId;
  private String toStopId;
  private int minTransferTime;
  private String fromRouteId;
  private String toRouteId;

  public Transfer() {
    super();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFromStopId() {
    return fromStopId;
  }

  public void setFromStopId(String fromStopId) {
    this.fromStopId = fromStopId;
  }

  public String getToStopId() {
    return toStopId;
  }

  public void setToStopId(String toStopId) {
    this.toStopId = toStopId;
  }

  public int getMinTransferTime() {
    return minTransferTime;
  }

  public void setMinTransferTime(int minTransferTime) {
    this.minTransferTime = minTransferTime;
  }

  public String getFromRouteId() {
    return fromRouteId;
  }

  public void setFromRouteId(String fromRouteId) {
    this.fromRouteId = fromRouteId;
  }

  public String getToRouteId() {
    return toRouteId;
  }

  public void setToRouteId(String toRouteId) {
    this.toRouteId = toRouteId;
  }

  @Override
  public String toString() {
    return "Transfer [id=" + id + ", fromStopId=" + fromStopId + ", toStopId=" + toStopId +
        ", minTransferTime=" + minTransferTime + ", fromRouteId=" + fromRouteId + ", toRouteId=" + toRouteId + "]";
  }

}
