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
	
	
	
	
	 
	public void addRestaurantToOntology(Map a) throws MalformedURLException, GateException, FileNotFoundException{
		
		
		System.out.println("Adding Restaurant to Ontology!");
		
		 String name = a.get("name").toString().replace(" ", "");
	     String id =   a.get("id").toString();
		
		
		 String city = a.get("city").toString();
	     String country = a.get("country").toString();
	     String region = a.get("region").toString();
	     
	     String[] cuisine =   a.get("cuisine").toString().split(",");
	     String price =   a.get("price").toString();
	     String type =   a.get("type").toString().toLowerCase();
	     
	     String[] facilities = a.get("facilities").toString().split(",");
		
		
		 OntModel m = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM );
		 m.read("C:\\TouristerWorkspace\\Ontologies\\Tourism1.0.0.owl");
		 
		 OntClass Restaurant = m.getOntClass(prefix + "Restaurant");
		 OntClass Location = m.getOntClass(prefix + "Location");
		 OntClass RestaurantType = m.getOntClass(prefix + "RestaurantType");
		 OntClass RestaurantCuisine = m.getOntClass(prefix + "Cuisine");
		 OntClass RestaurantPrice = m.getOntClass(prefix + "RestaurantPrice");
		 OntClass RestaurantFacility = m.getOntClass(prefix + "RestaurantFacility");
		 
		 
		 OntProperty hasRestaurantType = m.getOntProperty(prefix + "hasRestaurantType");
		 OntProperty hasRestaurantLocation = m.getOntProperty(prefix + "hasRestaurantLocation");
		 OntProperty hasRestaurantCuisine = m.getOntProperty(prefix + "hasCuisine");
		 OntProperty hasRestaurantPrice = m.getOntProperty(prefix + "hasRestaurantPrice");
		 OntProperty hasRestaurantFacility = m.getOntProperty(prefix + "hasRestaurantFacilities");
		 
		 
		 OntProperty hasRestaurantPriceAmount = m.getOntProperty(prefix + "hasRestaurantPriceAmount");
		 OntProperty hasRestaurantName = m.getOntProperty(prefix + "hasRestaurantName");
		 OntProperty hasCuisineName = m.getOntProperty(prefix + "hasCuisineName");
		 OntProperty hasRestaurantTypeName = m.getOntProperty(prefix + "hasRestaurantTypeName");
		 OntProperty hasRestaurantFacilityName = m.getOntProperty(prefix + "hasRestaurantFacilityName");
		
		 
		 OntProperty hasCity = m.getOntProperty(prefix + "hasCity");
		 OntProperty hasCountry = m.getOntProperty(prefix + "hasCountry");
		 OntProperty hasRegion = m.getOntProperty(prefix + "hasRegion");
		 OntProperty hasRestaurantID = m.getOntProperty(prefix + "hasRestaurantID");
		
		 
		 Individual RestaurantInd = Restaurant.createIndividual(prefix + name + "Restaurant");
		 Individual LocationInd = Location.createIndividual(prefix + name + "Location");
		 Individual RestaurantTypeInd = RestaurantType.createIndividual(prefix + name + "RestaurantType");
		 Individual RestaurantCuisineInd = RestaurantCuisine.createIndividual(prefix + name + "Cuisine");
		 Individual RestaurantPriceInd = RestaurantPrice.createIndividual(prefix + name + "RestaurantPrice");
		 Individual RestaurantFacilityInd = RestaurantFacility.createIndividual(prefix + name + "RestaurantFacility");
		 
		 

		 LocationInd.addProperty(hasCity, city, "string");
		 LocationInd.addProperty(hasCountry, country, "string");
		 LocationInd.addProperty(hasRegion, region, "string");
		 
		 RestaurantInd.addProperty(hasRestaurantLocation, LocationInd);
		 
		 
for(int i=0; i<cuisine.length; i++){
			 
			 String cuisineTrim = cuisine[i].trim().replace("-", " ").toLowerCase();
			 RestaurantCuisineInd.addProperty(hasCuisineName, cuisineTrim, "string");
			 
		 }
		 
		 
		 
		 RestaurantInd.addProperty(hasRestaurantCuisine, RestaurantCuisineInd);
		 
		 
		 
		 RestaurantInd.addProperty(hasRestaurantType, RestaurantTypeInd);
		 RestaurantTypeInd.addProperty(hasRestaurantTypeName, type, "string"); 
        
		 
		 
		 
      for(int i=0; i<facilities.length; i++){
			 
			 String facilitiesTrim = facilities[i].trim().replace("-", " ").toLowerCase();
			 RestaurantFacilityInd.addProperty(hasRestaurantFacilityName, facilitiesTrim, "string");
			 
		 }
		 
		 
		 RestaurantInd.addProperty(hasRestaurantFacility, RestaurantFacilityInd);
		 
        
		 RestaurantPriceInd.addProperty(hasRestaurantPriceAmount, price);
		 RestaurantInd.addProperty(hasRestaurantPrice, RestaurantPriceInd);
		 
		 
		 
		 RestaurantInd.addProperty(hasRestaurantName, name, "string");
		 RestaurantInd.addProperty(hasRestaurantID, id, "string");
		 
		 
		 
		 
		 OutputStream out = new FileOutputStream("C:\\TouristerWorkspace\\Ontologies\\Tourism1.0.0.owl");
		 m.write(out, "RDF/XML");
		
		
		
		
		
		

	    
	    System.out.println("Done!");

 


		
		
	}
	  
	

}
