package edu.wpi.cs3733.D22.teamC.entity.service_request;

import java.sql.Time;
import java.sql.Date;
import java.sql.Timestamp;

public class ServiceRequest {
    protected int requestID;
    protected String creatorID;     // TODO: Link to Employee
    protected String assigneeID;    // TODO: Link to Employee
    protected String location;      // TODO: Link to Location
    protected Timestamp creationTimestamp;
    protected String status;        // TODO: Make Enum
    protected String priority;      // TODO: Make Enum
    protected String requestType;   // TODO: Make Enum
    protected String description;

    public ServiceRequest(){}
    
    public ServiceRequest(ServiceRequest serviceRequest) {
        this.requestID = serviceRequest.getRequestID();
        this.creatorID = serviceRequest.getCreatorID();
        this.assigneeID = serviceRequest.getAssigneeID();
        this.location = serviceRequest.getLocation();
        this.creationTimestamp = serviceRequest.getCreationTimestamp();
        this.status = serviceRequest.getStatus();
        this.priority = serviceRequest.getPriority();
        this.requestType = serviceRequest.getRequestType();
        this.description = serviceRequest.getDescription();
    }
    
    public ServiceRequest(int requestID) {
        this.requestID = requestID;
    }
    
    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public String getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }

    public String getAssigneeID() {
        return assigneeID;
    }

    public void setAssigneeID(String assigneeID) {
        this.assigneeID = assigneeID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Timestamp getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Timestamp creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
