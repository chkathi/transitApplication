package edu.csus.csc131.transit.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Trip {
    @Id
    private String id;
    private String serviceId;
    private String routeId;
    private int directionId;

    public Trip() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public int getDirectionId() {
        return directionId;
    }    

    public void setDirectionId(int directionId) {
        this.directionId = directionId;
    }

    @Override
    public String toString() {
        return "Trip [id=" + id + ", serviceId=" + serviceId + ", routeId=" + routeId + ", directionId=" + directionId + "]";
    }
}
