package edu.wpi.cs3733.D22.teamC;

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
    // Declare singleton instance
    public static App instance;

    // Constants
    public static final String BASE_VIEW_PATH = "view/general/base-view.fxml";
    public static final String MENU_BAR_COMPONENT_PATH = "component/menu-bar.fxml";
    public static final String MEDICAL_EQUIPMENT = "view/service_request/medical-equipment-view.fxml";
    public static final String LAB_SYSTEM = "view/service_request/lab-system-view.fxml";

    // Variables
    private Stage stage;

    @Override
    public void init() {
        // Initialize Database Manager
        DBManager.startup();

        log.info("Starting Up");
    }

    @Override
    public void start(Stage primaryStage) {
        // Create singleton instance
        instance = this;
        // Store window as stage
        stage = primaryStage;

        setView(LAB_SYSTEM);

        // Initialize Database Manager
        DBManager.startup();
    }

    @Override
    public void stop() {
        // Shutdown Database Manager
        DBManager.shutdown();

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
