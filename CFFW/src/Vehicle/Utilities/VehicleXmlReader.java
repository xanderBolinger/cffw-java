package Vehicle.Utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import CharacterBuilder.Ability;
import HexGrid.HexDirectionUtility.HexDirection;
import Vehicle.Vehicle;
import Vehicle.Data.CrewCompartment;
import Vehicle.Data.CrewPosition;
import Vehicle.Data.ShieldGenerator;
import Vehicle.Data.VehicleMovementData;
import Vehicle.Utilities.VehicleDataUtility.CrewPositionType;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class VehicleXmlReader {

	public static Vehicle readVehicle(String vehicleCallSign, String vehicleType)
			throws Exception {
		var vehicleData = getVehicleData(vehicleType);
		var vehicleNode = (Element) vehicleData.getElementsByTagName("vehicle_data").item(0);
		boolean shielded = Boolean.parseBoolean(
				(vehicleNode).getAttribute("shielded"));
		
		var vehicle = new Vehicle(vehicleType, getCrewCompartments(vehicleData));
		
		if(shielded) {
			int maxValue = Integer.parseInt(vehicleNode.getAttribute("maxValue"));
			int rechargeRate = Integer.parseInt(vehicleNode.getAttribute("rechargeRate"));
			vehicle.AddShieldGenerator(new ShieldGenerator(maxValue,maxValue,rechargeRate));
		}
		
		vehicle.setVehicleCallsign(vehicleCallSign);
		vehicle.movementData = getVehicleMovementData(vehicleData);
		
		return vehicle;
	}

	
	private static VehicleMovementData getVehicleMovementData(Document vehicleData) throws Exception {
		
		var vmd = new VehicleMovementData();
		
		setMovementDataFeatures(vmd, vehicleData);
		
		vmd.acceleration = Integer.parseInt(vehicleData.getElementsByTagName("acceleration").item(0).getTextContent());
		vmd.deceleration = Integer.parseInt(vehicleData.getElementsByTagName("deceleration").item(0).getTextContent());
		
		vmd.hullTurnRateFullSpeed = Integer.parseInt(vehicleData.getElementsByTagName("hull_turn_rate_full_speed").item(0).getTextContent());
		vmd.hullTurnRateHalfSpeed = Integer.parseInt(vehicleData.getElementsByTagName("hull_turn_rate_half_speed").item(0).getTextContent());
		vmd.hullTurnRateNoSpeed = Integer.parseInt(vehicleData.getElementsByTagName("hull_turn_rate_no_speed").item(0).getTextContent());
		
		var boostElement = (Element) vehicleData.getElementsByTagName("boost").item(0);
		
		vmd.boostRecovery = Integer.parseInt(boostElement.getAttribute("recovery"));
		vmd.boostAcceleration =  Integer.parseInt(boostElement.getTextContent());
		
		return vmd;
	}
	
	private static void setMovementDataFeatures(VehicleMovementData vmd, Document vehicleData) {
		NodeList terrainList = vehicleData.getElementsByTagName("terrain");
		for (int i = 0; i < terrainList.getLength(); i++) {
            Element terrain = (Element) terrainList.item(i);
            String feature = terrain.getAttribute("feature");
            int value = Integer.parseInt(terrain.getTextContent());
            vmd.movementSpeeds.put(feature, value);
        }
	}
	
	
	private static List<CrewCompartment> getCrewCompartments(Document vehicleData) throws Exception {
		List<CrewCompartment> compartments = new ArrayList<CrewCompartment>();


		NodeList compartmentList = vehicleData.getElementsByTagName("compartment");
		for (int i = 0; i < compartmentList.getLength(); i++) {
            Element compartmentData = (Element) compartmentList.item(i);
            
            // Get the compartment name and shielded attribute
            String compartmentName = compartmentData.getAttribute("name");
            
            // Get the crew_positions element
            Element crewPositionsData = (Element) compartmentData.getElementsByTagName("crew_positions").item(0);
            
            var crewPositions = getCrewPositions(crewPositionsData);
            
            boolean shielded = Boolean.parseBoolean(compartmentData.getAttribute("shielded"));
            
            var compartment = new CrewCompartment(compartmentName, crewPositions);
            
            if(shielded) {
            	Element shieldData = (Element) compartmentData.getElementsByTagName("shield_generator").item(0);
            	var maxValue = Integer.parseInt(shieldData.getAttribute("maxValue"));
            	var rechargeRate = Integer.parseInt(shieldData.getAttribute("rechargeRate"));
            	var shieldGenerator = new ShieldGenerator(maxValue, maxValue, rechargeRate);
            	compartment.AddShieldGenerator(shieldGenerator);
            } 
            compartments.add(compartment);
            
        }
		
		return compartments;
	}
	
	
	private static List<CrewPosition> getCrewPositions(Element crewPositionsData) throws Exception {
		List<CrewPosition> crewPositions = new ArrayList<>();
		
		// Get all the position elements
        NodeList positionList = crewPositionsData.getElementsByTagName("position");
        for (int j = 0; j < positionList.getLength(); j++) {
            Element position = (Element) positionList.item(j);
            crewPositions.add(getCrewPosition(position));
        }
		
		return crewPositions;
	}
	
	private static CrewPosition getCrewPosition(Element position) throws Exception {
		
		// Get the position name
        String positionName = position.getAttribute("name");
        
        //System.out.println("\tPosition Name: " + positionName);
        
        // Get the position_types element
        Element positionTypeNodes = (Element) position.getElementsByTagName("position_types").item(0);
        
        var positionTypes = getCrewPositionTypes(positionTypeNodes);
        
        // Get the view_directions element
        Element viewDirectionNodes = (Element) position.getElementsByTagName("view_directions").item(0);
        
        var viewDirections = getPositionViewDirections(viewDirectionNodes);
        
        
        return new CrewPosition(positionName, positionTypes, viewDirections);
	}
	
	
	private static List<CrewPositionType> getCrewPositionTypes(Element positionTypeNodes) throws Exception {
		
		List<CrewPositionType> crewPositionTypes = new ArrayList<>();
		
		// Get all the type elements
        NodeList typeList = positionTypeNodes.getElementsByTagName("type");
        for (int k = 0; k < typeList.getLength(); k++) {
            Element type = (Element) typeList.item(k);
            
            // Get the type value
            String typeValue = type.getTextContent();
            
            crewPositionTypes.add(getPositionType(typeValue));
        }
        
        return crewPositionTypes;
	}
	
	private static List<HexDirection> getPositionViewDirections(Element viewDirectionNodes) throws Exception {
		
		List<HexDirection> viewDirections = new ArrayList<>();
		
		// Get all the direction elements
        NodeList directionList = viewDirectionNodes.getElementsByTagName("direction");
        for (int k = 0; k < directionList.getLength(); k++) {
            Element direction = (Element) directionList.item(k);
            
            // Get the direction value
            String directionValue = direction.getTextContent();
            
            viewDirections.add(getDirection(directionValue));
            
        }
        
        return viewDirections;
	}
	
	
	private static CrewPositionType getPositionType(String positionType) throws Exception {
		
		for(var dir : CrewPositionType.values()) {
			if(dir.toString().equals(positionType)) 
				return dir;
		}
		
		throw new Exception("Crew position type not found for position: "+positionType);
	}
	
	private static HexDirection getDirection(String hexDirection) throws Exception {
		
		for(var dir : HexDirection.getDirections()) {
			if(dir.toString().equals(hexDirection)) 
				return dir;
		}
		
		throw new Exception("Hex direction not found for direction: "+hexDirection);
	}
	
	
	private static Document getVehicleData(String vehicleType)
			throws ParserConfigurationException, SAXException, IOException {

		File file = new File("Vehicles/" + vehicleType + ".xml");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(file);
		doc.getDocumentElement().normalize();

		return doc;
	}

}
