package edu.wpi.cs3733.D22.teamC;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySR;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;

public class HibernateTest {

	@BeforeEach
	void setUp() {
        SessionManager.getSession();
    }

	@AfterEach
	void tearDown() {
        // Kill Session Factory after every test. Note: this will drop the tables!
        SessionManager.killSessionFactory();
    }

    //region Location Tests
	@Test
	void testInsertLocation() {
        // Insert a new object into the database
		Location newLoc = new Location();
        int insertedID = HibernateManager.insertObj(newLoc);
        assertNotEquals(-1, insertedID);
	}

	@Test
	void testGetLocation() {
		// Insert a new object into the database
		Location newLoc = new Location();
		newLoc.setBuilding("TOWER");
		newLoc.setNodeType(Location.NodeType.STOR);
		newLoc.setLongName("YourMom69");
		newLoc.setX(1000);
		newLoc.setY(1000);
		int insertedID = HibernateManager.insertObj(newLoc);
        assertNotEquals(-1, insertedID);

		// Retrieve the object
		Location retrievedLoc = HibernateManager.getObjByID(insertedID, Location.class);
		assertNotNull(retrievedLoc);

		// Verify attributes
		assertEquals(insertedID, retrievedLoc.getNodeID());
		assertEquals(newLoc.getBuilding(), retrievedLoc.getBuilding());
		assertEquals(newLoc.getNodeType(), retrievedLoc.getNodeType());
		assertEquals(newLoc.getLongName(), retrievedLoc.getLongName());
		assertEquals(newLoc.getX(), retrievedLoc.getX());
		assertEquals(newLoc.getY(), retrievedLoc.getY());
	}
    
    @Test
    void testGetLocations() {
        // Insert a new object into the database
        Location newLoc = new Location();
        newLoc.setBuilding("TOWER");
        newLoc.setNodeType(Location.NodeType.STOR);
        newLoc.setLongName("YourMom69");
        newLoc.setX(1000);
        newLoc.setY(1000);
        int insertedID = HibernateManager.insertObj(newLoc);
        assertNotEquals(-1, insertedID);
    
        HibernateManager.insertObj(new Location());
        HibernateManager.insertObj(new Location());
        
        // Get All
        List<Location> getList = HibernateManager.filterQuery("from Location");
        System.out.println(getList.size());
    }
    
	@Test
	void testUpdateLocation() {
		// Insert a new object into the database
		Location newLoc = new Location();
		newLoc.setBuilding("TOWER");
		newLoc.setNodeType(Location.NodeType.STOR);
		newLoc.setLongName("YourMom69");
		newLoc.setX(1000);
		newLoc.setY(1000);
		int insertedID = HibernateManager.insertObj(newLoc);

		// Retrieve the object
		Location retrievedLoc = HibernateManager.getObjByID(insertedID, Location.class);
		assertNotNull(retrievedLoc);

		// Update attributes
		Location.NodeType newNodeType = Location.NodeType.LABS;
		String newLongName = "NotYourMom";
		String newShortName = "NYM";
		int newX = 500;
		int newY = 500;

		retrievedLoc.setNodeID(insertedID);
		retrievedLoc.setNodeType(newNodeType);
		retrievedLoc.setLongName(newLongName);
		retrievedLoc.setShortName(newShortName);
		retrievedLoc.setX(newX);
		retrievedLoc.setY(newY);
		HibernateManager.updateObj(retrievedLoc);

		// Verify attributes
		assertEquals(newNodeType, retrievedLoc.getNodeType());
		assertEquals(newLongName, retrievedLoc.getLongName());
		assertEquals(newShortName, retrievedLoc.getShortName());
		assertEquals(newX, retrievedLoc.getX());
		assertEquals(newY, retrievedLoc.getY());
	}

	@Test
	void testDeleteLocation() {
		Location newLoc = new Location();
		newLoc.setBuilding("TOWER");
		newLoc.setLongName("deleteMe");

		int insertedID = HibernateManager.insertObj(newLoc);
        assertNotEquals(-1, insertedID);

		HibernateManager.deleteObj(newLoc);
	}
    //endregion
    
    @Test
    void testInsertSR() {
        // Insert a new object into the database
        String creatorID = "bshin100";
        String assigneeID = "nick1234";
        String locationID = "FOISIE";
        Timestamp creationTimeStamp = new Timestamp(694201234);
        ServiceRequest.Status status = ServiceRequest.Status.Blank;
        ServiceRequest.Priority priority = ServiceRequest.Priority.High;
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Security;
        String description = "soft eng is spain without the s";
        
        SecuritySR insertSR = new SecuritySR();
        //ServiceRequest insertSR = new ServiceRequest();
        insertSR.setCreatorID(creatorID);
        insertSR.setAssigneeID(assigneeID);
        insertSR.setLocation(locationID);
        insertSR.setCreationTimestamp(creationTimeStamp);
        insertSR.setStatus(status);
        insertSR.setPriority(priority);
        insertSR.setRequestType(requestType);
        insertSR.setDescription(description);
        insertSR.setSecurityType(SecuritySR.SecurityType.Intruder);
        
        int insertedID = HibernateManager.insertObj(insertSR);
        assertNotEquals(-1, insertedID);
    }
}
