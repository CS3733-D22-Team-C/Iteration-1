package edu.wpi.cs3733.D22.teamC.validation.service_request.medical_equipment;

import edu.wpi.cs3733.D22.teamC.error.error_item.service_request.SRErrorItem;
import edu.wpi.cs3733.D22.teamC.error.error_record.service_request.SRErrorRecord;
import edu.wpi.cs3733.D22.teamC.validation.service_request.SRFormEvaluator;
import javafx.scene.control.SingleSelectionModel;

import java.util.ArrayList;

public class MedicalEquipmentSRFormEvaluator extends SRFormEvaluator {

    public MedicalEquipmentSRFormEvaluator() {}

    public ArrayList<SRErrorItem> getMedicalEquipmentSRValidationTestResult(String location, String assigneeID, SingleSelectionModel priority, SingleSelectionModel status, SingleSelectionModel equipType, SingleSelectionModel equipID)
    {
        ArrayList <SRErrorItem> errorList = new ArrayList <SRErrorItem> ();

        errorList.addAll(super.getBasicRequiredFieldsFilledValidationResult(location, assigneeID, priority, status));
        errorList.add(super.getValidateAssigneeIDResult(assigneeID));
        errorList.add(super.getValidateLocationIDResult(location));
        errorList.add(checkEquipmentIDFilled(equipID));
        errorList.add(checkEquipmentTypeFilled(equipType));

        return errorList;
    }

    /**
     * Determine if the Equipment Type of Medical Equipment Service Request has been filled
     * @param equipType
     * @return ServiceRequestUserInputValidationErrorItem
     */
   public SRErrorItem checkEquipmentTypeFilled(SingleSelectionModel equipType)
   {
       if(equipType.isEmpty())
       {
           return SRErrorRecord.serviceRequestUserInputValidationErrorList[8];
       }
       else
       {
           return null;
       }
   }

    /**
     * Determine if the Equipment ID of a Medical Equipment Service Request has been filled
     * @param equipID
     * @return ServiceRequestUserInputValidationErrorItem
     */
   public SRErrorItem checkEquipmentIDFilled(SingleSelectionModel equipID)
   {
       if(equipID.isEmpty())
       {
            return SRErrorRecord.serviceRequestUserInputValidationErrorList[9];
       }
       else
       {
           return null;
       }
   }

    @Override
    public boolean noServiceRequestFormUserInputErrors(ArrayList<SRErrorItem> l) {
        return super.noServiceRequestFormUserInputErrors(l);
    }
}