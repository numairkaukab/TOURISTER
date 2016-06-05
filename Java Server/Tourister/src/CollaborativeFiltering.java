package Recommendation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class CollaborativeFiltering {
	
	
	
	public List<Integer> filter(int user_id, int[] item_ids) throws IOException, TasteException {
		
		
		System.out.println("Collaborative Filter for User " + user_id);
		
		List<Integer> return_array = new ArrayList<Integer>();
		
		
		File preferenceValues = new File("C:\\Bitnami\\wampstack-5.6.19-0\\frameworks\\laravel\\storage\\exports\\hotel_preferences.csv");
		DataModel recommendationModel = new FileDataModel(preferenceValues);
		
		UserSimilarity euclideanDistance = new EuclideanDistanceSimilarity(recommendationModel);
		UserNeighborhood nh = new ThresholdUserNeighborhood(0.1, euclideanDistance, recommendationModel);
		
		long[] userNeighborhood = nh.getUserNeighborhood(user_id);
		
		UserBasedRecommender recommender = new GenericUserBasedRecommender(recommendationModel, nh, euclideanDistance);
		
		for(int i=0; i<userNeighborhood.length; i++)
		{
			System.out.println("Similar Users: ");
			System.out.println(userNeighborhood[i]);
			
			
		}
		
		
		for(int i=0; i<item_ids.length; i++)
		{
			System.out.println("Item Id: " + item_ids[i]);
			float preference = recommender.estimatePreference(user_id, item_ids[i]);
			System.out.println(preference);
			
			if(preference >= 3)
			{
				return_array.add(item_ids[i]);
			}
			
		}
		
		
		
		
		
		return return_array;

		
		
		
	}
	
	
	
	
public List<Integer> eventFilter(int user_id, int[] item_ids) throws IOException, TasteException {
		
		
		System.out.println("Collaborative Filter for User " + user_id);
		
		List<Integer> return_array = new ArrayList<Integer>();
		
		
		File preferenceValues = new File("C:\\Bitnami\\wampstack-5.6.19-0\\frameworks\\laravel\\storage\\exports\\event_preferences.csv");
		DataModel recommendationModel = new FileDataModel(preferenceValues);
		
		UserSimilarity euclideanDistance = new EuclideanDistanceSimilarity(recommendationModel);
		UserNeighborhood nh = new ThresholdUserNeighborhood(0.1, euclideanDistance, recommendationModel);
		
		long[] userNeighborhood = nh.getUserNeighborhood(user_id);
		
		UserBasedRecommender recommender = new GenericUserBasedRecommender(recommendationModel, nh, euclideanDistance);
		
		for(int i=0; i<userNeighborhood.length; i++)
		{
			System.out.println("Similar Users: ");
			System.out.println(userNeighborhood[i]);
			
			
		}
		
		
		for(int i=0; i<item_ids.length; i++)
		{
			System.out.println("Item Id: " + item_ids[i]);
			float preference = recommender.estimatePreference(user_id, item_ids[i]);
			System.out.println(preference);
			
			if(preference >= 3)
			{
				return_array.add(item_ids[i]);
			}
			
		}
		
		
		
		
		
		return return_array;

		
		
		
	}
	

public List<Integer> restaurantFilter(int user_id, int[] item_ids) throws IOException, TasteException {
	
	
	System.out.println("Collaborative Filter for User " + user_id);
	
	List<Integer> return_array = new ArrayList<Integer>();
	
	
	File preferenceValues = new File("C:\\Bitnami\\wampstack-5.6.19-0\\frameworks\\laravel\\storage\\exports\\restaurant_preferences.csv");
	DataModel recommendationModel = new FileDataModel(preferenceValues);
	
	UserSimilarity euclideanDistance = new EuclideanDistanceSimilarity(recommendationModel);
	UserNeighborhood nh = new ThresholdUserNeighborhood(0.1, euclideanDistance, recommendationModel);
	
	long[] userNeighborhood = nh.getUserNeighborhood(user_id);
	
	UserBasedRecommender recommender = new GenericUserBasedRecommender(recommendationModel, nh, euclideanDistance);
	
	for(int i=0; i<userNeighborhood.length; i++)
	{
		System.out.println("Similar Users: ");
		System.out.println(userNeighborhood[i]);
		
		
	}
	
	
	for(int i=0; i<item_ids.length; i++)
	{
		System.out.println("Item Id: " + item_ids[i]);
		float preference = recommender.estimatePreference(user_id, item_ids[i]);
		System.out.println(preference);
		
		if(preference >= 3)
		{
			return_array.add(item_ids[i]);
		}
		
	}
	
	
	
	
	
	return return_array;

	
	
	
}
	
	
	
	
	

}
