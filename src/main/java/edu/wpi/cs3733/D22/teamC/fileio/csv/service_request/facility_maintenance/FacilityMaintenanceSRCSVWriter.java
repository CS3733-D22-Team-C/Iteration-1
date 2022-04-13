package edu.wpi.cs3733.D22.teamC.fileio.csv.service_request.facility_maintenance;

import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.service_request.facility_maintenance.FacilityMaintenanceSR;
import edu.wpi.cs3733.D22.teamC.fileio.csv.CSVWriter;

public class FacilityMaintenanceSRCSVWriter extends CSVWriter<FacilityMaintenanceSR> {
    /**
     * Manually define headers of attributes output to CSV.
     * @return The array of headers to be output to CSV.
     */
    @Override
    protected String[] compileHeaders() {
        return new String[]{
                "requestID",
                "creatorID",
                "assigneeID",
                "location",
                "creationTimestamp",
                "status",
                "priority",
                "requestType",
                "description",
                "maintenanceType",
                "modifierID",
                "modifiedTimestamp"
        };
    }

    /**
     * Maps headers to a value to get from the object.
     * @param serviceRequest The object to be read from.
     * @param header The header to be mapped to an attribute.
     * @return The retrieved value to be output to the CSV.
     */
    @Override
    protected String compileAttribute(FacilityMaintenanceSR serviceRequest, String header) {
        String output = "";
        switch (header) {
            case "requestID":
                output = serviceRequest.getRequestID();
                break;
            case "creatorID":
                Employee employee = serviceRequest.getCreator();
                output = (employee != null) ? employee.getEmployeeID() : "";
                break;
            case "assigneeID":
                Employee assignee = serviceRequest.getAssignee();
                output = (assignee != null) ? assignee.getEmployeeID() : "";
                break;
            case "location":
                output = serviceRequest.getLocation();
                break;
            case "creationTimestamp":
                output = (serviceRequest.getCreationTimestamp() == null) ? "" : serviceRequest.getCreationTimestamp().toString();
                break;
            case "status":
                output = serviceRequest.getStatus().toString();
                break;
            case "priority":
                output = serviceRequest.getPriority().toString();
                break;
            case "requestType":
                output = serviceRequest.getRequestType().toString();
                break;
            case "description":
                output = serviceRequest.getDescription();
                break;
            case "maintenanceType":
                output = serviceRequest.getMaintenanceType().toString();
                break;
            case "modifierID":
                Employee modifier = serviceRequest.getModifier();
                output = (modifier != null) ? modifier.getEmployeeID() : "";
                break;
            case "modifiedTimestamp":
                output = (serviceRequest.getModifiedTimestamp() == null) ? "" : serviceRequest.getModifiedTimestamp().toString();
                break;
            default:
                break;
        }
        return output;
    }
}