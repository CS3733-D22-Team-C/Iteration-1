package edu.wpi.cs3733.D22.teamC.controller.map.data.medical_equipment;

import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipmentDAO;
import edu.wpi.cs3733.D22.teamC.models.notifications.NotificationBuilder;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.SVGPath;

import java.util.List;

public class MedicalEquipmentCounter {
    // General Components
    @FXML public Group root;
    @FXML private SVGPath icon;
    @FXML private Label counter;

    // Edit Components
    @FXML private Group editGroup;
    @FXML private SVGPath upArrow;
    @FXML private SVGPath downArrow;

    // Variables
    private MedicalEquipment.EquipmentType equipmentType;
    private List<MedicalEquipment> medicalEquipments;

    // References
    private MedicalEquipmentNode parentNode;

    public void setup(MedicalEquipmentNode medicalEquipmentNode) {
        this.parentNode = medicalEquipmentNode;
    }

    public void setPosition(int x, int y) {
        root.setTranslateX(x);
        root.setTranslateY(y);
    }

    public void setType(MedicalEquipment.EquipmentType equipmentType) {
        this.equipmentType = equipmentType;
    }

    //#region Medical Equipment Manipulation
        public void setMedicalEquipments(List<MedicalEquipment> medicalEquipments) {
            this.medicalEquipments = medicalEquipments;

            updateCounter();
        }

        public MedicalEquipment removeMedicalEquipment() {
            MedicalEquipment medicalEquipment = medicalEquipments.remove(0);
            updateCounter();
            return medicalEquipment;
        }

        public void addMedicalEquipment(MedicalEquipment medicalEquipment) {
            medicalEquipments.add(medicalEquipment);
            updateCounter();
        }
    //#endregion

    public void setEditable(boolean editable) {
        editGroup.setVisible(editable);
    }

    public void setVisible(boolean visible) {
        root.setVisible(visible);
    }

    public void updateCounter() {
        counter.setText(Integer.toString(getCount()));
        if (this.parentNode != null) {
            downArrow.setDisable((medicalEquipments.size() == 0));
            upArrow.setDisable(((MedicalEquipmentManager) this.parentNode.getManager()).getFreeCount(equipmentType) == 0);
        }
    }

    public int getCount() {
        return medicalEquipments.size();
    }

    //#region FXML Events
        @FXML
        public void onUpArrowClicked(MouseEvent event) {
            MedicalEquipment medicalEquipment = ((MedicalEquipmentManager) parentNode.getManager()).removeFree(equipmentType);
            addMedicalEquipment(medicalEquipment);

            medicalEquipment.setLocationID(parentNode.getLocation().getID());
            new MedicalEquipmentDAO().update(medicalEquipment);
            parentNode.getManager().onUpdateDataEvents.forEach(Runnable::run);

            // Send Notification
            String notification = medicalEquipment.getEquipmentType().toString() + " #" + medicalEquipment.getTypeNumber() + " moved to " + parentNode.getLocation().getShortName() + ".";
            if (parentNode.getLocation().getNodeType().equals(Location.NodeType.DIRT)) {
                if (!medicalEquipment.getStatus().equals(MedicalEquipment.EquipmentStatus.Dirty)) {
                    notification += " This is a DIRT location, mark this equipment as Dirty?";
                }
            } else if (parentNode.getLocation().getNodeType().equals(Location.NodeType.STOR)) {
                if (!medicalEquipment.getStatus().equals(MedicalEquipment.EquipmentStatus.Available)) {
                    notification += " This is a STOR location, mark this equipment as Available?";
                }
            } else {
                if (!medicalEquipment.getStatus().equals(MedicalEquipment.EquipmentStatus.Unavailable)) {
                    notification += " Mark this equipment as Unavailable?";
                }
            }
            NotificationBuilder.createNotification("Medical Equipment Moved", notification, parentNode.getManager().getMapController().getMap());

            updateCounter();

            event.consume();
        }

        @FXML
        public void onDownArrowClicked(MouseEvent event) {
            MedicalEquipment medicalEquipment = removeMedicalEquipment();
            ((MedicalEquipmentManager) parentNode.getManager()).addFree(medicalEquipment);

            medicalEquipment.setLocationID("");
            new MedicalEquipmentDAO().update(medicalEquipment);
            parentNode.getManager().onUpdateDataEvents.forEach(Runnable::run);

            updateCounter();

            event.consume();
        }
    //#endregion
}
