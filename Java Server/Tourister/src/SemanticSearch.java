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

	
	
	public List<String> buildSPARQL(Map a){
		
		 System.out.println(a);
		 
		 List<String> searchResults = new ArrayList<String>();
		
		 OntModel m = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM );
		 m.read("C:\\TouristerWorkspace\\Ontologies\\Tourism1.0.0.owl");
		 
		 
		
		 List<String> whereClauses = new ArrayList<String>();  
		    
		 
		 StringBuilder queryString = new StringBuilder("PREFIX tourister: <"+prefix+"> SELECT ?a WHERE {");
		    
		 
		 
		 String domain = null;
		    String range = null;
		 
		 
			Iterator<OntProperty> it = m.listAllOntProperties();
			
			char var = 'b';
			
			while(it.hasNext()){
				
				OntProperty op = it.next();
				
				try{
					
					domain = op.getDomain().toString().replace(prefix, "");
					
					
				    
				     range = op.getRange().toString().replace(prefix, "");
					
					
				}
				
				catch(NullPointerException ne){
					
				}
			    
			    
			    
			    
			    Set classes = a.keySet();
			    Object[] arr = classes.toArray();
			    
			   
			    
			    
			    for(int i=0; i<arr.length; i++){
			    	
			    	if(domain.equals(arr[i])){
			    		
			    		for(int j=0; j<arr.length; j++)
			    		{
			    			
			    						    			
			    				if(range.equals(arr[j]))
			    				{
			    					
			    					
			    					String[] values = a.get(range).toString().split(",");
			    					
			    					if(values.length > 1)
			    					{
			    						
			    						
			    						for(int k=0; k<values.length; k++)
			    						{
			    							if(k==values.length-1)
			    							{
			    								String where = "{ ?a tourister:"+ op.getLocalName() +" ?"+var+". ?"+var+" ?"+ ++var +" '"+values[k].toString().replace("[", "").replace("]", "").trim()+"'@string } . ";	
							    				whereClauses.add(where);
							    				var++;
			    								
			    							}
			    							
			    							else{
			    							String where = " { ?a tourister:"+ op.getLocalName() +" ?"+var+". ?"+var+" ?"+ ++var +" '"+values[k].toString().replace("[", "").replace("]", "").trim()+"'@string } UNION ";	
						    				whereClauses.add(where);
						    				var++;
			    							}
			    							
			    						}
			    						
			    					}
			    				
			    					else
			    					{
			    				
			    			String where = " ?a tourister:"+ op.getLocalName() +" ?"+var+". ?"+var+" ?"+ ++var +" '"+a.get(range).toString().replace("[", "").replace("]", "")+"'@string .";	
			    				whereClauses.add(where);
			    				var++;
			    					}
			    				
			    				}
			    				
			    				
			    			}
			    		}
			    		
			    	}
			    	
			    	
			    	
			
			    
			    	
			    	
			    	
			    	
			    }
			
			for(int i=0; i<whereClauses.size(); i++)
			{
				
					queryString = queryString.append(whereClauses.get(i));
				
				
				
				
				
				
			}
			
			queryString.append("}");
			
			
			System.out.println(queryString.toString());
			
			
			Query query = QueryFactory.create(queryString.toString()) ;
			
			
			QueryExecution qexec = QueryExecutionFactory.create(query, m);
		    ResultSet results = qexec.execSelect() ;
		    
		    if(searchFor.equals("Hotel"))
		    {
		    
		    searchResults.add("Hotel");
		    }
		    
		    else if(searchFor.equals("Restaurant"))
		    {
		    	searchResults.add("Restaurant");
		    }
		    
	      
		    RDFVisitor rv = new RDFVisitor() {
				
				@Override
				public Object visitURI(Resource arg0, String arg1) {
					
					if(searchFor.equals("Hotel"))
					{
					
						
						
					OntProperty hasID = m.getOntProperty(prefix + "hasID");
					
					System.out.println(arg0.getRequiredProperty(hasID).getString());
					
					searchResults.add(arg0.getRequiredProperty(hasID).getString());
					}
					
					else if(searchFor.equals("Restaurant"))
					{
						
						
						
						OntProperty hasID = m.getOntProperty(prefix + "hasRestaurantID");
						
						System.out.println(arg0.getRequiredProperty(hasID).getString());
						
						searchResults.add(arg0.getRequiredProperty(hasID).getString());
						
						
					}
					
					
					return null;
				}

				@Override
				public Object visitBlank(Resource arg0, AnonId arg1) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Object visitLiteral(Literal arg0) {
					// TODO Auto-generated method stub
					return null;
				}
				
				
			};
		    
		    while (results.hasNext() )
		    {
		    	
		    	
		    	QuerySolution soln = results.nextSolution() ;
		        RDFNode x = soln.get("a");
		        x.visitWith(rv);
		        
		        
		        
		          
		       
		        
		       
	         }
			
		    System.out.println(searchResults);
		    return searchResults;
			
			}
			
			
			
			
			
		
		
	}


