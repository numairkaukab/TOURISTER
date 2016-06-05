package GATE;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.ontology.impl.DatatypePropertyImpl;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.AnonId;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.RDFVisitor;
import org.apache.jena.rdf.model.Resource;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Corpus;
import gate.CorpusController;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.creole.ANNIEConstants;
import gate.creole.ConditionalSerialAnalyserController;
import gate.creole.ResourceInstantiationException;
import gate.creole.SerialAnalyserController;
import gate.creole.ontology.GateOntologyException;
import gate.creole.ontology.Ontology;
import gate.util.GateException;
import gate.util.SimpleFeatureMapImpl;
import gate.util.persistence.PersistenceManager;
import jena.query;



public class SemanticSearch {
	
	public String prefix = "http://tourister.space/ontologies/tourism#";
	public static CorpusController controller;
	public static String searchFor;

	public List<String> search(String query) throws GateException, IOException {
		
		System.out.println(query);

		

		
		MultiMap classesAndInstances = new MultiValueMap();
		
	

		Corpus corpus = Factory.newCorpus("Search Corpus");
		Document doc = Factory.newDocument(query);

		corpus.add(doc);

		controller.setCorpus(corpus);
		controller.execute();

		Document annotations = corpus.get(0);

		AnnotationSet defaultAnnotSet = annotations.getAnnotations();

		
        System.out.println(defaultAnnotSet);
		

		SimpleFeatureMapImpl sfmiforclasses = new SimpleFeatureMapImpl();
		sfmiforclasses.put("type", "class");

		AnnotationSet a = defaultAnnotSet.get("Lookup", sfmiforclasses);

		Iterator<Annotation> iterator = a.iterator();
		

		while (iterator.hasNext()) {
			
			String classStr = iterator.next().getFeatures().get("URI").toString().replace(prefix, "");
			
			System.out.println(classStr);
			
			if(classesAndInstances.containsKey(classStr)){
				
			}
			else{
				
				
			
			classesAndInstances.put(classStr, "");
			
			 searchFor = classStr;
			
			
			}
			

		}
		
		SimpleFeatureMapImpl sfmiforinstances = new SimpleFeatureMapImpl();
		sfmiforinstances.put("type", "instance");
		
		AnnotationSet b = defaultAnnotSet.get("Lookup", sfmiforinstances);
		
		System.out.println(b);
		
       Iterator<Annotation> iterator2 = b.iterator();
		

		while (iterator2.hasNext()) {
			
			Annotation current = iterator2.next();
			
			String classStr = current.getFeatures().get("classURI").toString().replace(prefix, "");
			String propertyValue = current.getFeatures().get("propertyValue").toString();
			
			System.out.println(classStr + " " + propertyValue);
			
            if(classesAndInstances.containsKey(classStr) && classesAndInstances.containsValue(propertyValue)){
				
			}
			else{
				
				if(classStr.equals("HotelType"))
				{
					classesAndInstances.put("Hotel", "");
					
					classesAndInstances.remove("Restaurant");
					
				}
			
			classesAndInstances.put(classStr, propertyValue);
			}
			

		}
		
		
		
		
		 List<String> searchResults = buildSPARQL(classesAndInstances);
		 return searchResults;
		

	}
	
	
			
			
			
			
			
		
		
	}


