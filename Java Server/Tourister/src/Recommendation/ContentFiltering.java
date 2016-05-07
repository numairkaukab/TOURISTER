package Recommendation;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;

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
		 
		 String prpn = a.get("prpn").toString();
		 String price = a.get("price").toString();
		 
		 String rating = a.get("rating").toString();
		 String stars = a.get("stars").toString();
		 
		 doc.add(new Field("name",(String) a.get("name"), Field.Store.YES,Field.Index.NO));
		 doc.add(new Field("id",id, Field.Store.YES,Field.Index.NO));
		 
		 doc.add(new Field("prpn",prpn, Field.Store.YES,Field.Index.ANALYZED));
		 doc.add(new Field("price",price, Field.Store.YES,Field.Index.ANALYZED));
		 
		 doc.add(new Field("rating",rating, Field.Store.YES,Field.Index.ANALYZED));
		 doc.add(new Field("stars",stars, Field.Store.YES,Field.Index.ANALYZED));
		 
		 doc.add(new Field("addr",(String) a.get("addr"), Field.Store.YES,Field.Index.ANALYZED));
		 
		 doc.add(new Field("type",(String) a.get("type"), Field.Store.YES,Field.Index.ANALYZED));
		 doc.add(new Field("room_type",(String) a.get("room_type"), Field.Store.YES,Field.Index.ANALYZED));
		 
		 doc.add(new Field("features",(String) a.get("facilities"), Field.Store.YES,Field.Index.ANALYZED));
		 
		
		 
		 writer.addDocument(doc);
		
		 writer.close();
		 
		 System.out.println("Done!");
		 
		 
		
		
		
	}
	
	public String hotelFilter(String id) throws CorruptIndexException, IOException{
		
		System.out.println("Hotel Id: " + id);
		
		String return_id = null;
		
		File path = new File("C:\\TouristerWorkspace\\Index");
		Directory directory = new SimpleFSDirectory(path);
		
		IndexReader reader = IndexReader.open(directory);
		IndexSearcher searcher = new IndexSearcher(reader);
		
		MoreLikeThis mlt = new MoreLikeThis(reader);
		
		mlt.setFieldNames(new String[] {"prpn", "price", "rating", "stars", "addr" , "type", "room_type", "features"});
		mlt.setMinTermFreq(1);
		mlt.setMinDocFreq(1);
		
		
		for(int i=0; i<reader.numDocs(); i++)
		{
			Document doc = reader.document(i);
			
			if(doc.getField("id").stringValue().equals(id)){
				
				System.out.println("Found The Document!");
				System.out.println(doc.getField("name").toString());
				
				Query query = mlt.like(i);
				TopDocs similarDocs = searcher.search(query, 10);
				
				if(similarDocs.totalHits == 0)
				{
					System.out.println("No Similar Items Found!");
				}
				
				
				doc = reader.document(similarDocs.scoreDocs[0].doc);
				
				return_id = doc.getField("id").stringValue();
				
			}
		}
		
		return return_id;
		
	}
	

}
