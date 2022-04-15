package edu.wpi.cs3733.D22.teamC.controller.map.data;

import edu.wpi.cs3733.D22.teamC.controller.map.MapViewController;
import edu.wpi.cs3733.D22.teamC.controller.map.data.floor.FloorManager;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public abstract class Manager<T> {
        // Variables
    // Tracks the currently selected object for this manager.
    protected T current;
    // All data for this manager.
    protected List<T> all;
    // On change events for the manager to invoke when current changes.
    protected List<BiConsumer<T, T>> onChangeCurrentEvents = new ArrayList<>();

        // References
    // A reference to the overall map view controller.
    protected MapViewController mapViewController;


    public Manager(MapViewController mapViewController) {
        this.mapViewController = mapViewController;
    }

    //#region Current Object
        /**
         * Changes the currently tracked object for this manager.
         * @param object The new object to be tracked by this manager.
         */
        public void changeCurrent(T object) {
            onChangeCurrentEvents.forEach(event -> event.accept(current, object));
            current = object;
        }

        public T getCurrent() {
            return current;
        }
    //#endregion

    //#region All Objects
        public List<T> getAll() {
            return all;
        }
    //#endregion

    //#region Current Object Change Events
        public void addChangeCurrentEvent(BiConsumer<T, T> consumer) {
            onChangeCurrentEvents.add(consumer);
        }
    //#endregion

    //#region Managers
        public Pane getMap() {
            return mapViewController.getMap();
        }
    //#endregion
}
