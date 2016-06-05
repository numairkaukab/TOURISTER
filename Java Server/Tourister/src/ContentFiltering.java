package Recommendation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similar.MoreLikeThis;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.store.SimpleFSDirectory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import de.daslaboratorium.machinelearning.classifier.Classifier;
import de.daslaboratorium.machinelearning.classifier.bayes.BayesClassifier;

public class ContentFiltering {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		final String JAVABRIDGE_PORT="8088";
		  final php.java.bridge.JavaBridgeRunner runner = 
		    php.java.bridge.JavaBridgeRunner.getInstance(JAVABRIDGE_PORT);
		  
		  System.out.println("Waiting For PHP");
		  
		  runner.waitFor();
		  
		  
		
	}
	
	public void addHotelToIndex(Map a) throws CorruptIndexException, LockObtainFailedException, IOException{
		
		System.out.println(a);
		
		File path = new File("C:\\TouristerWorkspace\\Index");
		Directory directory = new SimpleFSDirectory(path);
		 
		
		 
		 
		 
		 IndexWriter writer = new IndexWriter(directory, new WhitespaceAnalyzer(), IndexWriter.MaxFieldLength.UNLIMITED);
		 
		 Document doc = new Document();
		 
		 String id = a.get("id").toString();
		 
		 
		 String price = a.get("price").toString();
		 
		 
		 String stars = a.get("stars").toString();
		 
		 doc.add(new Field("name",(String) a.get("name"), Field.Store.YES,Field.Index.NO));
		 doc.add(new Field("id",id, Field.Store.YES,Field.Index.NO));
		 
		 
		 
		 doc.add(new Field("price",price, Field.Store.YES,Field.Index.ANALYZED));
		 
		 
		 doc.add(new Field("stars",stars, Field.Store.YES,Field.Index.ANALYZED));
		 
		 doc.add(new Field("addr",(String) a.get("addr"), Field.Store.YES,Field.Index.ANALYZED));
		 
		 doc.add(new Field("city",(String) a.get("city"), Field.Store.YES,Field.Index.ANALYZED));
		 doc.add(new Field("country",(String) a.get("country"), Field.Store.YES,Field.Index.ANALYZED));
		 doc.add(new Field("region",(String) a.get("region"), Field.Store.YES,Field.Index.ANALYZED));
		 
		 
		 doc.add(new Field("type",(String) a.get("type"), Field.Store.YES,Field.Index.ANALYZED));
		 doc.add(new Field("room_type",(String) a.get("room_type"), Field.Store.YES,Field.Index.ANALYZED));
		 
		 doc.add(new Field("features",(String) a.get("facilities"), Field.Store.YES,Field.Index.ANALYZED));
		 
		
		 
		 writer.addDocument(doc);
		
		 writer.close();
		 
		 System.out.println("Done!");
		 
		 
		
		
		
	}
	
	
public void addEventToIndex(Map a) throws CorruptIndexException, LockObtainFailedException, IOException{
		
		System.out.println(a);
		
		File path = new File("C:\\TouristerWorkspace\\EventIndex");
		Directory directory = new SimpleFSDirectory(path);
		 
		
		 
		 
		 
		 IndexWriter writer = new IndexWriter(directory, new WhitespaceAnalyzer(), IndexWriter.MaxFieldLength.UNLIMITED);
		 
		 Document doc = new Document();
		 
		 String id = a.get("id").toString();
		 
		 
		 String performer = a.get("performer").toString();
		 String city = a.get("city").toString();
		 
		 
		 String organizer = a.get("organizer").toString();
		 
		 String type = a.get("type").toString();
		 
		 
		 doc.add(new Field("id",id, Field.Store.YES,Field.Index.NO));
		 
		 
		 
		 doc.add(new Field("performer",performer, Field.Store.YES,Field.Index.ANALYZED));
		 
		 doc.add(new Field("city",city, Field.Store.YES,Field.Index.ANALYZED));
		 
		 doc.add(new Field("organizer",organizer, Field.Store.YES,Field.Index.ANALYZED));
		 
		 doc.add(new Field("type",type, Field.Store.YES,Field.Index.ANALYZED));
		 
		
		 
		
		 
		 writer.addDocument(doc);
		
		 writer.close();
		 
		 System.out.println("Done!");
		 
		 
		
		
		
	}
	
	
	
	public List<String> generateUserProfile(Map a) throws IOException{
		
		System.out.println(a);
		
		List<String> return_items = new ArrayList<String>(); 
		
		File path = new File("C:\\TouristerWorkspace\\Index");
		Directory directory = new SimpleFSDirectory(path);
		
		IndexReader reader = IndexReader.open(directory);
		IndexSearcher searcher = new IndexSearcher(reader);
		
		String price = a.get("price").toString();
		String city = a.get("location").toString();
		String stars = a.get("stars").toString();
		String type = a.get("type").toString();
		String facilities = a.get("facilities").toString();
		String room_type = a.get("room_type").toString();
		
		Term cityTerm = new Term("city", city);
		Query cityQuery = new TermQuery(cityTerm);
		
		Term priceTerm = new Term("price", price);
		Query priceQuery = new TermQuery(priceTerm);
		
		Term starsTerm = new Term("stars", stars);
		Query starsQuery = new TermQuery(starsTerm);
		
		Term typeTerm = new Term("type", type);
		Query typeQuery = new TermQuery(typeTerm);
		
		Term facilityTerm = new Term("features", facilities);
		Query facilitiesQuery = new TermQuery(facilityTerm);
		
		Term roomTerm = new Term("room_type", room_type);
		Query roomQuery = new TermQuery(roomTerm);
	
		BooleanQuery b1 = new BooleanQuery();
		b1.add(cityQuery, BooleanClause.Occur.MUST);
		
		BooleanQuery b2 = new BooleanQuery();
		b2.add(priceQuery, BooleanClause.Occur.SHOULD);
		
		BooleanQuery b3 = new BooleanQuery();
		b3.add(starsQuery, BooleanClause.Occur.SHOULD);
		
		BooleanQuery b4 = new BooleanQuery();
		b4.add(typeQuery, BooleanClause.Occur.SHOULD);
		
		BooleanQuery b5 = new BooleanQuery();
		b5.add(facilitiesQuery, BooleanClause.Occur.SHOULD);
		
		BooleanQuery b6 = new BooleanQuery();
		b6.add(roomQuery, BooleanClause.Occur.SHOULD);
		
		
		
		
		Query finalQuery = BooleanQuery.mergeBooleanQueries(b1,b4,b3,b2,b5,b6);
		
		TopDocs similarDocs = searcher.search(finalQuery, 5);
		
		System.out.println(similarDocs.totalHits);
		
		if(similarDocs.totalHits == 0)
		{
			System.out.println("No Similar Items Found!");
		}
		
		Document doc = new Document();
		
		for(int j=0; j<5; j++)
		{
			doc = reader.document(similarDocs.scoreDocs[j].doc);
			
		
			
				
				
					
					return_items.add(doc.getField("id").stringValue());
				
				
				
				
			
		
		}
		
		return return_items;
		
		
	}
	
	
public List<String> classifyHotel(Map userPreferences, Map itemToClassify){
		
		Classifier<String, String> bayes = new BayesClassifier<String, String>();
		
		
		
		
		for(int i=0; i<userPreferences.size(); i++){
			
			Map b = (Map) userPreferences.get(i);
			
			List<String> likesAndDislikes = new ArrayList<String>();
			
			
			String price = b.get("price").toString();
			
			String priceConcat = price.concat("_price");
			likesAndDislikes.add(priceConcat);
			
			
			
			String stars = b.get("stars").toString();
			String starsConcat = stars.concat("_stars");
			likesAndDislikes.add(starsConcat);
			
			String type = b.get("type").toString();
			String typeConcat = type.concat("_type");
			likesAndDislikes.add(typeConcat);
			
            String room_type = b.get("room_type").toString();
            String roomTypeConcat = room_type.concat("_room_type");
			likesAndDislikes.add(roomTypeConcat);
			
			String facilities = b.get("facilities").toString();
			String facilitiesTrim = facilities.replace(" ","");
			
			String[] facilitiesBroken = facilitiesTrim.split(",");
			
			for(int j=0; j< facilitiesBroken.length; j++){
				
				String s = facilitiesBroken[j].concat("_facility");
				likesAndDislikes.add(s);
			}
			
			String preference = b.get("preference").toString();
			
			if(preference.equals("like"))
			{
			
			bayes.learn("like", likesAndDislikes);
			}
			
			else
			{
				bayes.learn("dislike", likesAndDislikes);
			}
			
			
			
			
		}
		
		

       List<String> return_array = new ArrayList<String>();

          for(int i=0; i<itemToClassify.size(); i++)
          {
        	  List<String> hotelToClassify = new ArrayList<String>();
        	  Map d = (Map) itemToClassify.get(i);
        	  
        	  String price = d.get("price").toString().concat("_price");
        	  String stars = d.get("stars").toString().concat("_stars");
        	  String type = d.get("type").toString().concat("_type");
        	  String roomType = d.get("room_type").toString().concat("_room_type");
        	  
        	  hotelToClassify.add(price);
        	  hotelToClassify.add(stars);
        	  hotelToClassify.add(type);
        	  hotelToClassify.add(roomType);
        	  
        	  String facilities = d.get("facilities").toString();
  			  
        	  String facilitiesTrim = facilities.replace(" ","");
  			
  			String[] facilitiesBroken = facilitiesTrim.split(",");
  			
  			for(int j=0; j< facilitiesBroken.length; j++){
  				
  				String s = facilitiesBroken[j].concat("_facility");
  				hotelToClassify.add(s);
  				
  				
  				
  			}
          
  			
  			if(bayes.classify(hotelToClassify).getCategory().equals("like")){
  				
  				return_array.add(d.get("id").toString());
  				
  			}
          
          }


          return return_array;
          
}




public List<String> classifyEvent(Map userPreferences, Map itemToClassify){
	
	Classifier<String, String> bayes = new BayesClassifier<String, String>();
	
	
	
	
	for(int i=0; i<userPreferences.size(); i++){
		
		Map b = (Map) userPreferences.get(i);
		
		List<String> likesAndDislikes = new ArrayList<String>();
		
		
		String performer = b.get("performer").toString();
		
		String performerConcat = performer.concat("_performer");
		likesAndDislikes.add(performerConcat);
		
		
		
		String organizer = b.get("organizer").toString();
		String organizerConcat = organizer.concat("_organizer");
		likesAndDislikes.add(organizerConcat);
		
		String type = b.get("type").toString();
		String typeConcat = type.concat("_type");
		likesAndDislikes.add(typeConcat);
		
       
		
		String preference = b.get("preference").toString();
		
		if(preference.equals("like"))
		{
		
		bayes.learn("like", likesAndDislikes);
		}
		
		else
		{
			bayes.learn("dislike", likesAndDislikes);
		}
		
		
		
		
	}
	
	
	

   List<String> return_array = new ArrayList<String>();

      for(int i=0; i<itemToClassify.size(); i++)
      {
    	  List<String> eventToClassify = new ArrayList<String>();
    	  Map d = (Map) itemToClassify.get(i);
    	  
    	  String performer = d.get("performer").toString().concat("_performer");
    	  String organizer = d.get("organizer").toString().concat("_organizer");
    	  String type = d.get("type").toString().concat("_type");
    	  
    	  
    	  eventToClassify.add(performer);
    	  eventToClassify.add(organizer);
    	  eventToClassify.add(type);
    	  
    	  
            System.out.println(bayes.classify(eventToClassify).getCategory().toString());
			
			if(bayes.classify(eventToClassify).getCategory().equals("like")){
				
				return_array.add(d.get("id").toString());
				
			}
      
      }


      return return_array;
      
}






	
	
	
	public Map<String, Set<String>> learnProfile(Map a){
		
		Classifier<String, String> bayes = new BayesClassifier<String, String>();
		
		
		for(int i=0; i<a.size(); i++){
			
			Map b = (Map) a.get(i);
			
			List<String> likes = new ArrayList<String>();
			
			
			String price = b.get("price").toString();
			
			String priceConcat = price.concat("_price");
			likes.add(priceConcat);
			
			
			
			String stars = b.get("stars").toString();
			String starsConcat = stars.concat("_stars");
			likes.add(starsConcat);
			
			String type = b.get("type").toString();
			String typeConcat = type.concat("_type");
			likes.add(typeConcat);
			
            String room_type = b.get("room_type").toString();
            String roomTypeConcat = room_type.concat("_roomtype");
			likes.add(roomTypeConcat);
			
			String facilities = b.get("facilities").toString();
			
			
			String[] facilitiesBroken = facilities.split(",");
			
			for(int j=0; j< facilitiesBroken.length; j++){
				
				String s = facilitiesBroken[j].concat("_facility");
				likes.add(s);
			}
			
			
			String pref = b.get("preference").toString();
			
			if(pref.equals("like"))
			{
			
			bayes.learn("like", likes);
			}
			
			else
			{
				bayes.learn("dislike", likes);
			}
			
			
			
			
		}
		
		System.out.println(bayes.getFeatures());
		
		HashMultimap<String,String> inferredProfileTemp = HashMultimap.create();
		Map<String, Set<String>> inferredProfile = new HashMap();
		
		
		
		Set<String> features = bayes.getFeatures();
		Iterator<String> iterator = features.iterator();
		while(iterator.hasNext())
		{
			String s = iterator.next();
			
			
			
			if(s.contains("_stars")){
				if(bayes.featureWeighedAverage(s, "like") >= 0.5)
				{
					
					
					String stars = s.replace("_stars","");
					inferredProfileTemp.put("rating", stars);
					
				}
				
			}
			
			if(s.contains("_price")){
				if(bayes.featureWeighedAverage(s, "like") >= 0.5)
				{
					String price = s.replace("_price","");
					inferredProfileTemp.put("price", price);
				}
				
			}
			
			if(s.contains("_type")){
				if(bayes.featureWeighedAverage(s, "like") >= 0.5)
				{
					String type = s.replace("_type","");
					String typeReplace = type.replace(" ", "");
					inferredProfileTemp.put("type", typeReplace);
				}
				
			}
			
			if(s.contains("_roomtype")){
				if(bayes.featureWeighedAverage(s, "like") >= 0.5)
				{
					String room_type = s.replace("_roomtype","");
					inferredProfileTemp.put("room_type", room_type);
				}
				
			}
			
			if(s.contains("_facility")){
				if(bayes.featureWeighedAverage(s, "like") >= 0.5)
				{
					String facility = s.replace("_facility","");
					inferredProfileTemp.put("facilities", facility);
				}
				
			}
			
			
			
		}
		
		System.out.println(inferredProfileTemp);
		
		inferredProfile = Multimaps.asMap(inferredProfileTemp); 
		System.out.println(inferredProfile);
		return inferredProfile;
		
	}
	
	
	
	
	
	
	
	
public Map<String, Set<String>> learnEventProfile(Map a){
		
		Classifier<String, String> bayes = new BayesClassifier<String, String>();
		
		
		for(int i=0; i<a.size(); i++){
			
			Map b = (Map) a.get(i);
			
			List<String> likes = new ArrayList<String>();
			
			
			String performer = b.get("performer").toString();
			
			String performerConcat = performer.concat("_performer");
			likes.add(performerConcat);
			
			
			
			String organizer = b.get("organizer").toString();
			String organizerConcat = organizer.concat("_organizer");
			likes.add(organizerConcat);
			
			String type = b.get("type").toString();
			String typeConcat = type.concat("_type");
			likes.add(typeConcat);
		
			
			
			bayes.learn("like", likes);
			
			
			
			
		}
		
		System.out.println(bayes.getFeatures());
		
		HashMultimap<String,String> inferredProfileTemp = HashMultimap.create();
		Map<String, Set<String>> inferredProfile = new HashMap();
		
		Set<String> features = bayes.getFeatures();
		Iterator<String> iterator = features.iterator();
		while(iterator.hasNext())
		{
			String s = iterator.next();
			
			
			
			if(s.contains("_performer")){
				if(bayes.featureWeighedAverage(s, "like") > 0.5)
				{
					String performer = s.replace("_performer","");
					inferredProfileTemp.put("performer", performer);
					
				}
				
			}
			
			if(s.contains("_organizer")){
				if(bayes.featureWeighedAverage(s, "like") > 0.5)
				{
					String organizer = s.replace("_organizer","");
					inferredProfileTemp.put("organizer", organizer);
				}
				
			}
			
			if(s.contains("_type")){
				if(bayes.featureWeighedAverage(s, "like") > 0.5)
				{
					String type = s.replace("_type","");
					String typeReplace = type.replace(" ", "");
					inferredProfileTemp.put("type", typeReplace);
				}
				
			}
			
			
			
			
			
		}
		
		
		inferredProfile = Multimaps.asMap(inferredProfileTemp); 
		return inferredProfile;
		
	}
	
	
	
	
	
	
	
	
	public List<String> hotelFilter(String id, String city) throws CorruptIndexException, IOException{
		
		System.out.println("Item Id " + id);
		System.out.println("City " + city);
		
		List<String> return_items = new ArrayList<String>(); 
		
		File path = new File("C:\\TouristerWorkspace\\Index");
		Directory directory = new SimpleFSDirectory(path);
		
		IndexReader reader = IndexReader.open(directory);
		IndexSearcher searcher = new IndexSearcher(reader);
		
		
		
		MoreLikeThis mlt = new MoreLikeThis(reader);
		
		mlt.setFieldNames(new String[] {"price", "stars", "addr" , "type", "room_type", "features"});
		mlt.setMinTermFreq(1);
		mlt.setMinDocFreq(1);
		
		
		for(int i=0; i<reader.numDocs(); i++)
		{
			Document doc = reader.document(i);
			
			
			
			if(doc.getField("id").stringValue().equals(id)){
				
			
			
			
				
				System.out.println("Found The Document!");
				
				
				Query query = mlt.like(i);
				TopDocs similarDocs = searcher.search(query, 5);
				
				System.out.println(similarDocs.totalHits);
				
				if(similarDocs.totalHits == 0)
				{
					System.out.println("No Similar Items Found!");
				}
				
				
				for(int j=0; j<5; j++)
				{
					doc = reader.document(similarDocs.scoreDocs[j].doc);
					if(doc.getField("id").stringValue().equals(id))
					{
						
					}
					
					else{
						
						if(doc.get("city").equals(city))
						{
							
							return_items.add(doc.getField("id").stringValue());
						}
						
						else{
							
						}
						
					}
					
				}
				
				
				
				
			}
		
			
			
		}
		
		
		System.out.println(return_items.size());
		return return_items;
		
	}
	
	
	
public List<String> eventFilter(String id, String city, String type) throws CorruptIndexException, IOException{
		
		System.out.println("Item Id " + id);
		System.out.println("City " + city);
		
		List<String> return_items = new ArrayList<String>(); 
		
		File path = new File("C:\\TouristerWorkspace\\EventIndex");
		Directory directory = new SimpleFSDirectory(path);
		
		IndexReader reader = IndexReader.open(directory);
		IndexSearcher searcher = new IndexSearcher(reader);
		
		
		
		
		
		
		
		
		for(int i=0; i<reader.numDocs(); i++)
		{
			Document doc = reader.document(i);
			
			
			
			if(doc.getField("id").stringValue().equals(id)){
				
			
			
			
				
				System.out.println("Found The Document!");
				
				

				Term typeTerm = new Term("type", type);
				Query typeQuery = new TermQuery(typeTerm);
			
				BooleanQuery b1 = new BooleanQuery();
				b1.add(typeQuery, BooleanClause.Occur.SHOULD);
				
				
				
				
				TopDocs similarDocs = searcher.search(b1, 10);
				
				System.out.println(similarDocs.totalHits);
				
				if(similarDocs.totalHits == 0)
				{
					System.out.println("No Similar Items Found!");
				}
				
				
				for(int j=0; j<1; j++)
				{
					
					
					doc = reader.document(similarDocs.scoreDocs[j].doc);
					if(doc.getField("id").stringValue().equals(id))
					{
						
					}
					
					else{
						
						if(doc.get("city").equals(city))
						{
							
							return_items.add(doc.getField("id").stringValue());
						}
						
						else{
							
						}
						
					}
					
				}
				
				
				
				
			}
		
			
			
		}
		
		
		System.out.println(return_items.size());
		return return_items;
		
	}
	
	
	
	
	

}
