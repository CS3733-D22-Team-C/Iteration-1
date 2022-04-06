package edu.wpi.cs3733.D22.teamC.fileio.csv;

import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;

public class FloorCSVReader extends CSVReader<Floor> {

    /**
     * Maps Floor (header, value) pairs to a value to change for the object.
     * @param object The object to be modified.
     * @param header The header to be mapped to an attribute.
     * @param value The value for the current attribute.
     * @return The Floor modified with the value at the corresponding attribute.
     */
    @Override
    protected Floor parseAttribute(Floor object, String header, String value) {
        switch (header) {
            case "id":
                object.setFloorID(Integer.parseInt(value));
                break;
            case "order":
                object.setOrder(Integer.parseInt(value));
                break;
            case "longName":
                object.setLongName(value);
                break;
            case "shortName":
                object.setShortName(value);
                break;
            default:
                break;
        }
        return object;
    }

    /**
     * Wrapper for Floor constructor.
     * @return Newly created Floor Object.
     */
    @Override
    protected Floor createObject() {
        return new Floor();
    }
}