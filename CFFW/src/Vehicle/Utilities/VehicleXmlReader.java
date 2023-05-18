package Vehicle.Utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import Vehicle.Utilities.VehicleDataUtility.CrewPositionType;

public class VehicleXmlReader {

	static final String COMPARTMENT = "compartment";
	static final String NAME = "name";
	static final String SHIELDED = "shielded";
	static final String POSITION = "position";
	static final String MASTERY = "mastery";
	static final String SUPPORT = "support";
	static final String SKILL = "skill";

	public static Vehicle readVehicle(String vehicleName) {
		
		List<CrewCompartment> compartments = new ArrayList<CrewCompartment>();

		CrewCompartment compartmentToAdd = null;
		String compartmentName = "";
		boolean shielded = false;
		
		List<CrewPosition> crewPositions = new ArrayList<>();
		
		String positionName;
		List<CrewPositionType> positionTypes = new ArrayList<>();
		List<HexDirection> viewDirections = new ArrayList<>();
		
		try {
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			InputStream in = new FileInputStream("Vehicles/"+vehicleName+".xml");
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();

				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					// If we have an item element, we create a new item
					String elementName = startElement.getName().getLocalPart();
					//System.out.println("Start Element name: "+elementName);
					switch (elementName) {

					case COMPARTMENT:
						
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							if (attribute.getName().toString().equals(NAME)) {
								compartmentName = attribute.getValue();
							}
							if (attribute.getName().toString().equals(SHIELDED)) {
								shielded = Boolean.getBoolean(attribute.getValue());
							}
						}
						
						break;
					
					/*case ABILITY:

						ability = new Ability();
						ability.rank = 1;

						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							if (attribute.getName().toString().equals(NAME)) {
								ability.name = attribute.getValue();
							}
						}

						break;
					case SPECIAL:
						event = eventReader.nextEvent();
						if(!event.isEndElement())
							ability.special = event.asCharacters().getData();
						break;
					case MASTERY:
						event = eventReader.nextEvent();
						if(!event.isEndElement())
							ability.mastery = event.asCharacters().getData();
						break;
					case SUPPORT:
						event = eventReader.nextEvent();
						break;
					case SKILL:
						event = eventReader.nextEvent();
						//System.out.println("Skill: "+event.asCharacters().getData());
						if(!event.isEndElement())
							ability.skillSupport.add(event.asCharacters().getData());
						break;*/
					}
				}

				// If we reach the end of an item element, we add it to the list
				if (event.isEndElement()) {
					EndElement endElement = event.asEndElement();
					var elementName = endElement.getName();
					//System.out.println("End Element name: "+elementName);
					if (endElement.getName().getLocalPart().equals(COMPARTMENT)) {
						//items.add(ability);
					}
				}
				

			}
		} catch (FileNotFoundException | XMLStreamException e) {
			e.printStackTrace();
		}

		return new Vehicle(vehicleName, null);
	}
	
}

