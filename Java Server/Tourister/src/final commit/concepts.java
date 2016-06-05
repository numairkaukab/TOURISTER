package Ontology;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import gate.Factory;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.tdb.TDBFactory;

import gate.FeatureMap;
import gate.Gate;
import gate.creole.ResourceInstantiationException;
import gate.creole.ontology.DatatypeProperty;
import gate.creole.ontology.Literal;
import gate.creole.ontology.OClass;
import gate.creole.ontology.OConstants;
import gate.creole.ontology.OInstance;
import gate.creole.ontology.OURI;
import gate.creole.ontology.ObjectProperty;
import gate.creole.ontology.Ontology;
import gate.creole.ontology.URI;
import gate.util.GateException;

import java.util.*;

public class touristerOntology {
	
	public String prefix = "http://tourister.space/ontologies/tourism#";
	
	
	
	public void addEventToOntology(Map a) throws FileNotFoundException{
		
		 String name = a.get("name").toString().replace(" ", "");
	     String id =   a.get("id").toString();
		
		
		 String city = a.get("city").toString();
	     String country = a.get("country").toString();
	     String region = a.get("region").toString();
	     
	     String performer =   a.get("performer").toString();
	     String organizer =   a.get("organizer").toString();
	     String type =   a.get("type").toString();
	     
	     String date = a.get("date").toString();
		
		
		 OntModel m = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM );
		 m.read("C:\\TouristerWorkspace\\Ontologies\\Tourism1.0.0.owl");
		 
		 OntClass Event = m.getOntClass(prefix + "Event");
		 OntClass EventType = m.getOntClass(prefix + "EventType");
		 OntClass EventTime = m.getOntClass(prefix + "EventTime");
		 OntClass EventDate = m.getOntClass(prefix + "EventDate");
		 OntClass EventPerformer = m.getOntClass(prefix + "EventPerformer");
		 OntClass EventOrganizer = m.getOntClass(prefix + "EventOrganizer");
		 OntClass Location = m.getOntClass(prefix + "Location");
		 
		 OntProperty hasEventType = m.getOntProperty(prefix + "hasEventType");
		 OntProperty hasEventTime = m.getOntProperty(prefix + "hasEventTime");
		 OntProperty hasEventDate = m.getOntProperty(prefix + "hasEventDate");
		 OntProperty hasEventPerformer = m.getOntProperty(prefix + "hasEventPerformer");
		 OntProperty hasEventOrganizer = m.getOntProperty(prefix + "hasEventOrganizer");
		 OntProperty hasEventLocation = m.getOntProperty(prefix + "hasEventLocation");
		 
		 OntProperty hasDate = m.getOntProperty(prefix + "hasDate");
		 OntProperty hasEventName = m.getOntProperty(prefix + "hasEventName");
		 OntProperty hasEventTypeName = m.getOntProperty(prefix + "hasEventTypeName");
		 OntProperty hasOrganizer = m.getOntProperty(prefix + "hasOrganizer");
		 OntProperty hasPerformer = m.getOntProperty(prefix + "hasPerformer");
		 OntProperty hasTime = m.getOntProperty(prefix + "hasTime");
		 OntProperty hasCity = m.getOntProperty(prefix + "hasCity");
		 OntProperty hasCountry = m.getOntProperty(prefix + "hasCountry");
		 OntProperty hasRegion = m.getOntProperty(prefix + "hasRegion");
		 OntProperty hasEventID = m.getOntProperty(prefix + "hasEventID");
		
		 
		 Individual eventInd = Event.createIndividual(prefix + name + "Event");
		 Individual eventTypeInd = EventType.createIndividual(prefix + name + "EventType");
		 Individual eventTimeInd = EventTime.createIndividual(prefix + name + "EventTime");
		 Individual eventDateInd = EventDate.createIndividual(prefix + name + "EventDate");
		 Individual eventPerformerInd = EventPerformer.createIndividual(prefix + name + "EventPerformer");
		 Individual eventOrganizerInd = EventOrganizer.createIndividual(prefix + name + "EventOrganizer");
		 Individual locationInd = Location.createIndividual(prefix + name + "Location");
		 
		 locationInd.addProperty(hasCity, city, "string");
		 locationInd.addProperty(hasCountry, country, "string");
		 locationInd.addProperty(hasRegion, region, "string");
		 
		 eventInd.addProperty(hasEventLocation, locationInd);
		 
		 eventPerformerInd.addProperty(hasPerformer, performer);
		 
		 eventInd.addProperty(hasEventPerformer, eventPerformerInd);
		 
         eventOrganizerInd.addProperty(hasOrganizer, organizer);
		 
		 eventInd.addProperty(hasEventOrganizer, eventOrganizerInd);
		 
         eventTypeInd.addProperty(hasEventTypeName, type);
		 
		 eventInd.addProperty(hasEventType, eventTypeInd);
		 
		 eventInd.addProperty(hasEventName, name);
		 eventInd.addProperty(hasEventID, id, "string");
		 
		 
		 eventDateInd.addProperty(hasDate, date);
		 eventInd.addProperty(hasEventDate, eventDateInd);
		 
		 OutputStream out = new FileOutputStream("C:\\TouristerWorkspace\\Ontologies\\Tourism1.0.0.owl");
		 m.write(out, "RDF/XML");
		 
		
	}
	 
	
	  
	

}
