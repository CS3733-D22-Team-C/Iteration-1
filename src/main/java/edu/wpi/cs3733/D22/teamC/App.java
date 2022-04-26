package edu.wpi.cs3733.D22.teamC;

import edu.wpi.cs3733.D22.teamC.controller.SkeletonController;
import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.employee.EmployeeDAO;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipmentDAO;
import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.entity.patient.PatientDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAO;
import edu.wpi.cs3733.D22.teamC.fileio.csv.CSVFacade;
import edu.wpi.cs3733.D22.teamC.models.patient.PatientSelectorWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class App extends Application {
    public static class View<T> {
        Node node;
        T controller;

        public View(Node node, T controller) {
            this.node = node;
            this.controller = (T) controller;
        }

        public Node getNode() {
            return node;
        }

        public T getController() {
            return controller;
        }
        public void setController(T controller){
            this.controller = controller;
        }
    }

    // Constants
    public static final String BASE_COMPONENT_PATH = "view/component/base.fxml";
    public static final String TITLE_BAR_COMPONENT_PATH = "view/component/title_bar.fxml";
    public static final String DRAWER_PATH = "view/component/drawer.fxml";
    public static final String DRAWER_CONTENT_PATH = "view/component/drawer_content.fxml";
    public static final String LOGIN_PATH = "view/general/login.fxml";
    public static final String DASHBOARD_PATH = "view/general/dashboard.fxml";
    public static final String VIEW_LOCATIONS_PATH = "view/location/view_locations.fxml";
    public static final String VIEW_SERVICE_REQUESTS_PATH = "view/service_request/service_request_landing_page.fxml";
    public static final String SERVICE_REQUEST_LANDING_PAGE = "view/service_request/service_request_landing_page.fxml";
    public static final String MAP_DASHBOARD_PATH = "view/location/map/base_side_map_view.fxml";
    public static final String DATABASE_PAGE_PATH = "view/general/edit_databases_page.fxml";
    public static final String MAP_PATH = "view/map/floor_map.fxml";
    public static final String BASE_CSS_PATH = "css/base.css";
    public static final String USER_PROFILE = "view/general/profile_page/user_profile.fxml";
    public static final String MAP_SIDEBAR = "view/map/floor_map.fxml";
    public static final String SHORTCUT_EDIT = "view/service_request/skeleton/sr_shortcut.fxml";
    //public static final String IMAGE_PATH = "static/images/BrighamAndWomensHospital.png";

    // Singleton Instance
    public static App instance;

    //Employee
    private Employee userAccount;

    // Variables
    private Stage stage;
    private Stage activeStage;
    private Scene scene;

    @Override
    public void init() {
        SessionManager.switchDatabase(SessionManager.DBMode.EMBEDDED);
        log.info("Starting Up");
    }

    @Override
    public void start(Stage primaryStage) {
        // Create singleton instance
        instance = this;
        // Store window as stage
        stage = primaryStage;
        stage.setFullScreen(true);
        setViewStatic(LOGIN_PATH);
        //setViewStatic(MAP_DASHBOARD_PATH);
    }

    @Override
    public void stop() {
        log.info("Shutting Down");
    }


    /**
     * Set a skeleton file for a view.
     * @param skeletonFile The path to the skeleton view.
     * @param insertFile The path to the insert view.
     */
    @SuppressWarnings("unchecked")
    public void setSkeletonView(String skeletonFile, String insertFile) {
        View view = loadView(skeletonFile);
        SkeletonController skeletonController = (SkeletonController) view.getController();
        skeletonController.setUp(insertFile);
        setView(view.getNode());
    }

    /**
     * Set view for window from a file.
     * @param viewFile Path to the FXML file to be displayed.
     */
    public void setView(String viewFile){
        Node node = loadView(viewFile).getNode();
        setView(node);
    }

    public void setViewStatic(String viewFile)
    {
        Node node = loadView(viewFile).getNode();
        setViewStatic(node);
    }

    /**
     * Set view for window from a node.
     * @param viewNode Node to be displayed.
     */
    public void setView(Node viewNode) {
        // Load Base Node
        BorderPane baseNode = (BorderPane) loadView(BASE_COMPONENT_PATH).getNode();

        // TODO: Find a way to only change the center of the baseNode, nothing else

        // Load drawer Menu
        Node drawerNode = loadView(DRAWER_PATH).getNode();

        // Embed views and components
        //baseNode.setTop(menuBarNode);
        baseNode.setCenter(viewNode);
        baseNode.setLeft(drawerNode);
        baseNode.autosize();

        if (scene != null) scene.setRoot(baseNode);
        else scene = new Scene(baseNode);
        stage.setScene(scene);
        stage.show();
    }

    public void setViewStatic(Node viewNode)
    {
        // Load Base Node
        BorderPane baseNode = (BorderPane) loadView(BASE_COMPONENT_PATH).getNode();

        // Embed views and components
        //baseNode.setTop(menuBarNode);
        baseNode.setCenter(viewNode);
        baseNode.autosize();

        if (scene != null) scene.setRoot(baseNode);
        else scene = new Scene(baseNode);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Load a view from a file.
     * @param viewFile Path to the FXML file to be loaded.
     * @return Loaded FXML file wrapped in a View as a Node and Controller.
     */
    @SuppressWarnings("unchecked")
    public View loadView(String viewFile) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource(viewFile));
            return new View(loader.load(), loader.getController());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Load a view from a file, setting its controller.
     * @param viewFile Path to the FXML file to be loaded.
     * @param controller Controller to be attached to the FXML file.
     * @return Loaded FXML file wrapped in a View as a Node and Controller.
     */
    public <T> View<T> loadView(String viewFile, T controller) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource(viewFile));
            loader.setController(controller);
            return new View(loader.load(), loader.getController());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Stage getStage() {
        return stage;
    }

    public void setActiveStage(Stage activeStage) {
        this.activeStage = activeStage;
    }

    public Stage getActiveStage() {
        return (activeStage == null) ? stage : activeStage;
    }

    public Employee getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(Employee userAccount) {
        this.userAccount = userAccount;
    }
}
