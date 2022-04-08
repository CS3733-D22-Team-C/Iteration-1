package edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "MEDICINE_DELIVERY_SR")
public class MedicineDeliverySR extends ServiceRequest {


    @Column (name = "Medicine")
    protected String medicine;

    @Column (name = "Dosage")
    protected String dosage;

    @Column (name = "PatientID")
    protected String patientID;     // TODO: Link to Patient

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }
}
