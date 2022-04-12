package edu.wpi.cs3733.D22.teamC.fileio.csv;

import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.employee.EmployeeDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;

import java.sql.Timestamp;

public class LabSystemSRCSVReader extends CSVReader<LabSystemSR> {
    private EmployeeDAO employeeDAO = new EmployeeDAO();
    /**
     * Maps ServiceRequest (header, value) pairs to a value to change for the object.
     * @param serviceRequest The object to be modified.
     * @param header The header to be mapped to an attribute.
     * @param value The value for the current attribute.
     * @return The Location modified with the value at the corresponding attribute.
     */
    @Override
    protected LabSystemSR parseAttribute(LabSystemSR serviceRequest, String header, String value) {
        switch(header) {
            case "requestID":
                serviceRequest.setRequestID(value);
                break;
            case "creatorID":
                Employee creator = employeeDAO.getByID(value);
                serviceRequest.setCreator(creator);
                break;
            case "assigneeID":
                Employee assignee = employeeDAO.getByID(value);
                serviceRequest.setAssignee(assignee);
                break;
            case "location":
                serviceRequest.setLocation(value);
                break;
            case "creationTimestamp":
                serviceRequest.setCreationTimestamp(Timestamp.valueOf(value));
                System.out.println(value);
                break;
            case "status":
                serviceRequest.setStatus(ServiceRequest.Status.valueOf(value));
                break;
            case "priority":
                serviceRequest.setPriority(ServiceRequest.Priority.valueOf(value));
                break;
            case "requestType":
                serviceRequest.setRequestType(ServiceRequest.RequestType.valueOf(value));
                break;
            case "description":
                serviceRequest.setDescription(value);
                break;
            case "labType":
                serviceRequest.setLabType(LabSystemSR.LabType.valueOf(value));
                break;
            case "patientID":
                serviceRequest.setPatientID(value);
                break;
            case "modifierID":
                Employee modifier = employeeDAO.getByID(value);
                serviceRequest.setModifier(modifier);
                break;
            case "modifiedTimestamp":
                serviceRequest.setModifiedTimestamp(Timestamp.valueOf(value)); //TODO: Handle the modified time
                System.out.println(value);
                break;
        }
        return serviceRequest;
    }

    @Override
    protected LabSystemSR createObject() {
        return new LabSystemSR();
    }
}
