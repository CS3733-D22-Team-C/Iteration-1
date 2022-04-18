package edu.wpi.cs3733.D22.teamC.models.patient;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.models.employee.EmployeeTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.generic.TableDisplay;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PatientTableDisplay extends TableDisplay<Patient>{
    public class PatientTableEntry extends TableDisplayEntry {
        // Properties
        private StringProperty patientID;
        private StringProperty firstName;
        private StringProperty lastName;
        private StringProperty emergencyContact;
        private StringProperty phone;
        //private StringProperty DOB;
        private StringProperty location;



        public PatientTableEntry(Patient patient) {
            super(patient);
            patientID = new SimpleStringProperty((patient.getID()));
            firstName = new SimpleStringProperty(patient.getFirstName());
            lastName = new SimpleStringProperty(patient.getLastName());
            emergencyContact = new SimpleStringProperty(patient.getEmergencyContact());
            phone = new SimpleStringProperty(patient.getPhone());
            location = new SimpleStringProperty(patient.getLocationID());
            //DOB = new SimpleStringProperty(patient.getDOB().toString());

        }

        @Override
        public void RefreshEntry() {
            firstName.setValue(object.getFirstName());
            lastName.setValue(object.getLastName());
            phone.setValue(object.getPhone());
            patientID.setValue(object.getID());

            emergencyContact.setValue(object.getEmergencyContact());
            location.setValue(object.getLocationID());
         //   DOB.setValue(object.getDOB());


        }
    }

    public PatientTableDisplay(JFXTreeTableView table) {
        super(table);
    }

    @Override
    public void addObject(Patient object) {
        addEntry(new PatientTableDisplay.PatientTableEntry(object));
    }

    @Override
    protected void setColumns(JFXTreeTableView table) {
        // Insert Columns for Table
        addColumn(
                table,
                "Last Name",
                1f * Integer.MAX_VALUE * 25,
                (PatientTableDisplay.PatientTableEntry entry) -> {return entry.lastName;}
        );

        addColumn(
                table,
                "First Name",
                1f * Integer.MAX_VALUE * 25,
                (PatientTableDisplay.PatientTableEntry entry) -> {return entry.firstName;}
        );
//DOB
//        addColumn(
//                table,
//                "Role",
//                1f * Integer.MAX_VALUE * 25,
//                (EmployeeTableDisplay.EmployeeTableEntry entry) -> {return entry.role;}
//        );
    }
}