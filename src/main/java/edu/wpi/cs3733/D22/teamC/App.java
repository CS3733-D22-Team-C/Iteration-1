package edu.wpi.cs3733.D22.teamC;

import edu.wpi.cs3733.D22.teamC.controller.location.LocationSelectController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import java.sql.*;
import java.io.IOException;

@Slf4j
public class App extends Application {
    // Declare singleton instance
    public static App instance;

    // Constants
    private final String BASE_VIEW_PATH = "view/general/base-view.fxml";
    private final String MENU_BAR_COMPONENT_PATH = "component/menu-bar.fxml";
    private final String MEDICAL_EQUIPMENT = "view/service_request/medical-equipment.fxml";
    private final String LAB_SYSTEM = "view/service_request/lab-system-view.fxml";
    private final String LOCATION_SELECT = "view/general/location-select-view.fxml";

    // Variables
    private Stage stage;

    @Override
    public void init() {
    log.info("Starting Up");
    }

    @Override
    public void start(Stage primaryStage) {
        // Create singleton instance
        instance = this;
        // Store window as stage
        stage = primaryStage;


        // Initialize Database Manager
        DBManager.startup();

        setView(LOCATION_SELECT);
    }

    @Override
    public void stop() {
        log.info("Shutting Down");
    }

    public void setView(String viewFile) {
        try {
            // Load Base Page
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(BASE_VIEW_PATH));
            BorderPane baseNode = loader.load();

            // Load View
            loader = new FXMLLoader(); // might not need this
            loader.setLocation(getClass().getResource(viewFile));
            Node viewNode = loader.load();

            // Load Menu Bar
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(MENU_BAR_COMPONENT_PATH));
            Node menuBarNode = loader.load();

            // Embed views and components
            baseNode.setCenter(viewNode);
            baseNode.setTop(menuBarNode);

            Scene scene = new Scene(baseNode);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
          System.out.println("Could not load file " + viewFile);
          e.printStackTrace();
        }
    }
  
    public Stage getStage() {
        return stage;
    }
}
