package edu.wpi.cs3733.D22.teamC.userinputvalidation.service_request.lab_system;

import edu.wpi.cs3733.D22.teamC.error.error_item.ServiceRequestValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.userinputvalidation.service_request.ServiceRequestFormEvaluator;

public class LabSystemSRFormEvaluator extends ServiceRequestFormEvaluator {

    private static final ServiceRequestValidationErrorItem[] labSystemServiceRequestValidationErrorItemList = {
            new ServiceRequestValidationErrorItem(8, "Lab Type needs to be filled"),
            new ServiceRequestValidationErrorItem(9, "Patient ID needs to be filled")
    };

    public LabSystemSRFormEvaluator() {}

    @Override
    public ServiceRequestValidationErrorItem getValidateAssigneeIDResult(int ID) {
        return super.getValidateAssigneeIDResult(ID);
    }

    @Override
    public ServiceRequestValidationErrorItem getValidateLocationIDResult(int location) {
        return super.getValidateLocationIDResult(location);
    }

    @Override
    public ServiceRequestValidationErrorItem checkAssigneeIDFilled(int assigneeID) {
        return super.checkAssigneeIDFilled(assigneeID);
    }

    @Override
    public ServiceRequestValidationErrorItem checkLocationIDFilled(int locationID) {
        return super.checkLocationIDFilled(locationID);
    }

    @Override
    public ServiceRequestValidationErrorItem checkStatusFilled(String status) {
        return super.checkStatusFilled(status);
    }

    @Override
    public ServiceRequestValidationErrorItem checkPriorityFilled(String priority) {
        return super.checkPriorityFilled(priority);
    }

    public ServiceRequestValidationErrorItem checkLabTypeFilled(String labType)
    {
        return null;
    }

    public ServiceRequestValidationErrorItem checkPatientIDFilled(int patientID)
    {
        return null;
    }
}