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
	
	public void addHotelToOntology(Map a) throws FileNotFoundException{
	
		System.out.println("Adding Hotel to Ontology!");
	
	     String name = a.get("name").toString().replace(" ", "");
	     String id =   a.get("id").toString();
	     
	     String city = a.get("city").toString();
	     String country = a.get("country").toString();
	     String region = a.get("region").toString();
	     
	     String stars =   a.get("stars").toString();
	     String price =  a.get("price").toString();
	     String type = a.get("type").toString();
	     String room_type = a.get("room_type").toString();
	     String facilitiesStr = a.get("facilities").toString();
	     String[] facilities = facilitiesStr.split(",");
		 
		 OntModel m = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM );
		 m.read("C:\\TouristerWorkspace\\Ontologies\\Tourism1.0.0.owl");
		 
		 OntClass Hotel = m.getOntClass(prefix + "Hotel");
		 
		 
		 
		 OntClass Feature = m.getOntClass(prefix + "Feature");
		 OntClass Rating = m.getOntClass(prefix + "Rating");
		 OntClass Price = m.getOntClass(prefix + "Price");
		 OntClass roomType = m.getOntClass(prefix + "Room");
		 OntClass Location = m.getOntClass(prefix + "Location");
		 OntClass hotelType = m.getOntClass(prefix + "HotelType");
		 
		 OntProperty hasFeature = m.getOntProperty(prefix + "hasFeature");
		 OntProperty hasLocation = m.getOntProperty(prefix + "hasLocation");
		 OntProperty hasPrice = m.getOntProperty(prefix + "hasPrice");
		 OntProperty hasRoom = m.getOntProperty(prefix + "hasRoom");
		 OntProperty hasRating = m.getOntProperty(prefix + "hasRating");
		 
		 OntProperty hasName = m.getOntProperty(prefix + "hasName");
		 
		 OntProperty hasFeatureName = m.getOntProperty(prefix + "hasFeatureName");
		 OntProperty hasRoomTypeName = m.getOntProperty(prefix + "hasRoomTypeName");
		 OntProperty hasHotelTypeName = m.getOntProperty(prefix + "hasHotelTypeName");
		 
		 OntProperty hasID = m.getOntProperty(prefix + "hasID");
		 OntProperty hasCity = m.getOntProperty(prefix + "hasCity");
		 OntProperty hasCountry = m.getOntProperty(prefix + "hasCountry");
		 OntProperty hasRegion = m.getOntProperty(prefix + "hasRegion");
		 OntProperty hasStarRating = m.getOntProperty(prefix + "hasStarRating");
		 OntProperty hasUnit = m.getOntProperty(prefix + "hasUnit");
		 OntProperty hasUserRating = m.getOntProperty(prefix + "hasUserRating");
		 OntProperty hasValue = m.getOntProperty(prefix + "hasValue");
		 OntProperty hasType = m.getOntProperty(prefix + "hasType");
		 
		 Individual hotelInd = Hotel.createIndividual(prefix + name);
		 
		 
		 
		
		 Individual featureInd = Feature.createIndividual(prefix + name + "Feature");
		 Individual ratingInd = Rating.createIndividual(prefix + name + "Rating");
		 Individual priceInd = Price.createIndividual(prefix + name + "Price");
		 Individual roomTypeInd = roomType.createIndividual(prefix + name + "Room");
		 Individual hotelTypeInd = hotelType.createIndividual(prefix + name + "Type");
		 Individual locationInd = Location.createIndividual(prefix + name + "Location");
		 
		 locationInd.addProperty(hasCity, city, "string");
		 locationInd.addProperty(hasCountry, country, "string");
		 locationInd.addProperty(hasRegion, region, "string");
		 
		 hotelTypeInd.addProperty(hasHotelTypeName, type, "string");
		 
		 hotelInd.addProperty(hasLocation, locationInd);
		 
		 hotelInd.addProperty(hasName, name, "string");
		 hotelInd.addProperty(hasID, id, "string");
		 
		 hotelInd.addProperty(hasRating, ratingInd);
		 hotelInd.addProperty(hasType, hotelTypeInd);
		 
		 ratingInd.addProperty(hasStarRating, stars , "string");
		 
		 hotelInd.addProperty(hasPrice, priceInd);
		 
		 priceInd.addProperty(hasValue, price, "string");
		 
		 
		 
		 
		 
		 for(int i=0; i<facilities.length; i++){
			 
			 String facilitiesTrim = facilities[i].trim().replace("-", " ").toLowerCase();
			 featureInd.addProperty(hasFeatureName, facilitiesTrim, "string");
			 
		 }
		 
		 hotelInd.addProperty(hasFeature, featureInd);
		 
		 
		 roomTypeInd.addProperty(hasRoomTypeName, room_type, "string");
		 hotelInd.addProperty(hasRoom, roomTypeInd);
		 
		 
		 
		 OutputStream out = new FileOutputStream("C:\\TouristerWorkspace\\Ontologies\\Tourism1.0.0.owl");
		 m.write(out, "RDF/XML");
		 
		 
		 System.out.println("Success: Adding Hotel to Ontology!");
	 
	
    
	}
	
	
	  
	

}
