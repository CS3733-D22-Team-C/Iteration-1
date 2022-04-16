package edu.wpi.cs3733.D22.teamC.controller.service_request;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.models.employee.EmployeeSelectorWindow;
import edu.wpi.cs3733.D22.teamC.models.location.MapSelectorWindow;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.utils.ComponentWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Locale;

public class BaseServiceRequestCreateController<T extends ServiceRequest> implements ServiceRequestController {
    // FXML
    @FXML
    private TextField assigneeID;

    @FXML
    private TextField locationID;

    @FXML
    private JFXTextArea description;

    @FXML
    private Button goBackButton;

    @FXML
    private Label title;

    @FXML
    private JFXComboBox<String> priority;

    @FXML
    private Button resetButton;

    @FXML
    private Button submitButton;

    @FXML
    private VBox fieldsBox;

    @FXML
    private JFXTreeTableView<?> table;

    @FXML
    private JFXButton employeeTableButton;

    // References
    private InsertServiceRequestCreateController<T> insertController;

    // Variables
    private ServiceRequestTableDisplay<T> tableDisplay;
    private ServiceRequest.RequestType requestType;
    private EmployeeSelectorWindow employeeSelectorWindow;

    private Location location;
    private Employee assignee;


    public void setup(ServiceRequest.RequestType requestType) {
        this.requestType = requestType;
        switch (requestType)
        {
            case Medical_Equipment:
                title.setText("Create Medical Equipment Service Request");
                break;
            case Facility_Maintenance:
                title.setText("Create Facility Maintenance Service Request");
                break;
            case Lab_System:
                title.setText("Create Lab System Service Request");
                break;
            case Medicine_Delivery:
                title.setText("Create Medicine Delivery Service Request");
                break;
            case Security:
                title.setText("Create Security Service Request");
                break;
            case Sanitation:
                title.setText("Create Sanitation Service Request");
                break;
            case Delivery_System:
                title.setText("Create Delivery System Service Request");
            default:
        }

        // Setup Insert
        setInsert(requestType);

        // Priority dropdown
        for (ServiceRequest.Priority pri : ServiceRequest.Priority.values()) {
            priority.getItems().add(pri.toString());
        }

        // Restrict ID TextFields to only contain numeric values
        //setIDFieldToNumeric(assigneeID);
        //ComponentWrapper.setIDFieldToNumeric(locationField);

        // Limit the length of TextFields and TextAreas so that users can input a limited number of characters:
        ComponentWrapper.setTextLengthLimiter(assigneeID, 10);
        //ComponentWrapper.setTextLengthLimiter(locationField, 10);
        // Limit the length of TextFields and TextAreas so that users can input a limited number of characters:
        ComponentWrapper.setTextLengthLimiter(description, 100);

        // Setup table
        tableDisplay = insertController.setupTable(table);

        // Insert to DB
        DAO<T> serviceRequestDAO = insertController.createServiceRequestDAO();
        for (T serviceRequest : serviceRequestDAO.getAll()) {
            tableDisplay.addObject(serviceRequest);
        }
    }


    boolean requiredFieldsPresent(){
        if (priority.getValue() == null && priority.getPromptText().equals(""))
            return false;
        if (assigneeID.getText().equals(""))
            return false;
        if (locationID.getText().equals(""))
            return false;
        return insertController.requiredFieldsPresent();
    }


    public void setInsert(ServiceRequest.RequestType requestType) {
        String viewFile = "view/service_request/" + requestType.toString().toLowerCase(Locale.ROOT) + "/create_insert.fxml";

        App.View<InsertServiceRequestCreateController<T>> view = App.instance.loadView(viewFile);
        fieldsBox.getChildren().add(view.getNode());
        insertController = view.getController();
    }

    private void clearFields() {
        // Clearing Fields
        assigneeID.clear();
        assignee = null;
        locationID.clear();
        location = null;
        description.setText("");

        // Clearing Dropdowns
        priority.valueProperty().set(null);

        insertController.clearFields();
    }

    //#region Selector Window Updaters
        public void setEmployee(Employee employee){
            assignee = employee;

            String employeeName = "";
            if (employee != null) {
                employeeName = employee.getLastName() + ", " + employee.getFirstName();
            }

            assigneeID.setText(employeeName);
        }

        public void setLocation(Location location) {
            this.location = location;

            String locationName = "";
            if (location != null) {
                locationName = location.getShortName();
            }

            locationID.setText(locationName);
        }
    //#endregion

    private void createServiceRequest() {
        // Create Service Request
        T serviceRequest = insertController.createServiceRequest();

        serviceRequest.setAssignee(assignee);
        serviceRequest.setLocation(location.getID());
        serviceRequest.setDescription(description.getText());
        serviceRequest.setPriority(ServiceRequest.Priority.valueOf(priority.getValue()));

        if (requiredFieldsPresent()) serviceRequest.setStatus(ServiceRequest.Status.Processing);
        else serviceRequest.setStatus(ServiceRequest.Status.Blank);

        serviceRequest.setCreationTimestamp(new Timestamp(System.currentTimeMillis()));
        serviceRequest.setCreator(App.instance.getUserAccount());
        serviceRequest.setModifiedTimestamp(new Timestamp(System.currentTimeMillis()));
        serviceRequest.setModifier(App.instance.getUserAccount());

        serviceRequest.setRequestType(requestType);

        // Insert to DB
        DAO<T> serviceRequestDAO = insertController.createServiceRequestDAO();
        serviceRequest.setID(serviceRequestDAO.insert(serviceRequest));

        // Add to TableDisplay
        tableDisplay.addObject(serviceRequest);

        clearFields();
    }

    //#region FXML Buttons
        @FXML
        void clickGoBack(ActionEvent event) {
            App.instance.setView(App.SERVICE_REQUEST_LANDING_PAGE);
        }

        @FXML
        void clickReset(ActionEvent event) {
            clearFields();
        }

        @FXML
        void clickSubmit(ActionEvent event) {
            createServiceRequest();
            clearFields();
        }

        @FXML
        void goToEmployeeTable(ActionEvent event) throws IOException {
            new EmployeeSelectorWindow(employee -> this.setEmployee(employee));
        }


        @FXML
        void goToMapView(ActionEvent event) {
            new MapSelectorWindow(this::setLocation);
        }
    //#endregion
}
