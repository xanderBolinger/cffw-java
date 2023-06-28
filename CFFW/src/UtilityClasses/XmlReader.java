package UtilityClasses;

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

public class XmlReader {
	static final String ABILITY = "ability";
	static final String NAME = "name";
	static final String SPECIAL = "special";
	static final String MASTERY = "mastery";
	static final String SUPPORT = "support";
	static final String SKILL = "skill";
	// static final String INTERACTIVE = "interactive";

	public List<Ability> readAbilities(String abilityFile) {
		List<Ability> items = new ArrayList<Ability>();

		try {
			// First, create a new XMLInputFactory
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			// Setup a new eventReader
			InputStream in = new FileInputStream(abilityFile);
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
			// read the XML document
			Ability ability = null;

			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();

				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					// If we have an item element, we create a new item
					String elementName = startElement.getName().getLocalPart();

					switch (elementName) {

					case ABILITY:

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
						break;
					}
				}

				// If we reach the end of an item element, we add it to the list
				if (event.isEndElement()) {
					EndElement endElement = event.asEndElement();
					if (endElement.getName().getLocalPart().equals(ABILITY)) {
						items.add(ability);
					}
				}
				

			}
		} catch (FileNotFoundException | XMLStreamException e) {
			e.printStackTrace();
		}

		return items;
	}

}
