package Recommendation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
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
		 
		 doc.add(new Field("type",(String) a.get("type"), Field.Store.YES,Field.Index.ANALYZED));
		 doc.add(new Field("room_type",(String) a.get("room_type"), Field.Store.YES,Field.Index.ANALYZED));
		 
		 doc.add(new Field("features",(String) a.get("facilities"), Field.Store.YES,Field.Index.ANALYZED));
		 
		
		 
		 writer.addDocument(doc);
		
		 writer.close();
		 
		 System.out.println("Done!");
		 
		 
		
		
		
	}
	
	public void generateUserProfile(Map a) throws IOException{
		
		System.out.println(a);
		
		
		String id = a.get("user").toString();
		
		File path = new File("C:\\TouristerWorkspace\\Index");
		Directory directory = new SimpleFSDirectory(path);
		
		Document user_profile = new Document();
		
		IndexReader reader = IndexReader.open(directory);
		
		
		for(int i=0; i<reader.numDocs(); i++)
		{
			Document doc = reader.document(i);
			
			try{
			
			if(doc.getField("user_id").stringValue().equals(id))
			{
				
				System.out.println("Found The Document!");
				
				
			   IndexWriter writer = new IndexWriter(directory, new WhitespaceAnalyzer(), IndexWriter.MaxFieldLength.UNLIMITED);
			   
				writer.deleteDocuments(new Term("user_id",id));
				
				writer.close();
				
				System.out.println("Deleted Exisiting User Profile");
				
				
			}
			
			}
			
			catch(NullPointerException ne){
				
			}
			
			
		}
		
		IndexWriter writer = new IndexWriter(directory, new WhitespaceAnalyzer(), IndexWriter.MaxFieldLength.UNLIMITED);
		 
		
		String stars = a.get("stars").toString();
		String price = a.get("price").toString();
		 
		
		
		 
		 
		
		 
		 user_profile.add(new Field("user_id", id, Field.Store.YES, Field.Index.NOT_ANALYZED));
		 user_profile.add(new Field("stars", stars, Field.Store.YES, Field.Index.ANALYZED));
		 
		 user_profile.add(new Field("price", price, Field.Store.YES, Field.Index.ANALYZED));
		 user_profile.add(new Field("addr", (String) a.get("location"), Field.Store.YES, Field.Index.ANALYZED));
		 user_profile.add(new Field("type", (String) a.get("type"), Field.Store.YES, Field.Index.ANALYZED));
		 user_profile.add(new Field("features", (String) a.get("facilities"), Field.Store.YES, Field.Index.ANALYZED));
		 user_profile.add(new Field("room_type", (String) a.get("room_type"), Field.Store.YES, Field.Index.ANALYZED));
		 user_profile.add(new Field("prpn", "", Field.Store.YES, Field.Index.ANALYZED));
		 user_profile.add(new Field("rating", "", Field.Store.YES, Field.Index.ANALYZED));
		 
		 
		 writer.addDocument(user_profile);
		 writer.close();
		 
		 System.out.println("Done!");
		
		 
		
		//return hotelFilter(id);
		
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
            String roomTypeConcat = room_type.concat("_room_type");
			likes.add(roomTypeConcat);
			
			String facilities = b.get("facilities").toString();
			String facilitiesTrim = facilities.replace(" ","");
			
			String[] facilitiesBroken = facilitiesTrim.split(",");
			
			for(int j=0; j< facilitiesBroken.length; j++){
				
				String s = facilitiesBroken[j].concat("_facility");
				likes.add(s);
			}
			
			
			bayes.learn("like", likes);
			
			
			
			
		}
		
		
		HashMultimap<String,String> inferredProfileTemp = HashMultimap.create();
		Map<String, Set<String>> inferredProfile = new HashMap();
		
		Set<String> features = bayes.getFeatures();
		Iterator<String> iterator = features.iterator();
		while(iterator.hasNext())
		{
			String s = iterator.next();
			
			
			
			if(s.contains("_stars")){
				if(bayes.featureWeighedAverage(s, "like") > 0.5)
				{
					String stars = s.replace("_stars","");
					inferredProfileTemp.put("rating", stars);
					
				}
				
			}
			
			if(s.contains("_price")){
				if(bayes.featureWeighedAverage(s, "like") > 0.5)
				{
					String price = s.replace("_price","");
					inferredProfileTemp.put("price", price);
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
			
			if(s.contains("_room_type")){
				if(bayes.featureWeighedAverage(s, "like") > 0.5)
				{
					String room_type = s.replace("_room_type","");
					inferredProfileTemp.put("room_type", room_type);
				}
				
			}
			
			if(s.contains("_facility")){
				if(bayes.featureWeighedAverage(s, "like") > 0.5)
				{
					String facility = s.replace("_facility","");
					inferredProfileTemp.put("facilities", facility);
				}
				
			}
			
			
			
		}
		
		
		inferredProfile = Multimaps.asMap(inferredProfileTemp); 
		return inferredProfile;
		
	}
	
	
	
	public List<String> hotelFilter(String id) throws CorruptIndexException, IOException{
		
		System.out.println("Item Id " + id);
		
		List<String> return_items = null; 
		
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
				TopDocs similarDocs = searcher.search(query, 10);
				
				System.out.println(similarDocs.totalHits);
				
				if(similarDocs.totalHits == 0)
				{
					System.out.println("No Similar Items Found!");
				}
				
				
				for(int j=0; j<similarDocs.totalHits; j++)
				{
					doc = reader.document(similarDocs.scoreDocs[j].doc);
					if(doc.getField("id").stringValue().equals(id))
					{
						
					}
					
					else{
						return_items = new ArrayList<String>();
						return_items.add(doc.getField("id").stringValue());
					}
					
				}
				
				System.out.println(return_items);
				
				
			}
		
			
			
		}
		
		
		
		return return_items;
		
	}
	

}
