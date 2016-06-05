import java.util.*;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class TripAdvisor {

	public static void main(String[] args) throws InterruptedException, UnirestException {
		
		
		Map restaurant = new HashMap();
		WebDriver driver = new FirefoxDriver();
		
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.navigate().to("https://www.tripadvisor.com/Restaurants");
		
		WebElement search = driver.findElement(By.id("SEARCHBOX"));
		
		search.sendKeys("sydney");
		
		
		WebElement submit = driver.findElement(By.id("SUBMIT_RESTAURANTS"));
		
		Thread.sleep(3000);
		
		WebElement choice = driver.findElement(By.className("typeahead-choice"));
		choice.click();
		
		
		submit.click();
		
		
		String parent_window = driver.getWindowHandle();	
		
		
		
		
		
		WebElement availability = driver.findElement(By.id("availability"));
		availability.click();
		
		Thread.sleep(5000);
	
		List<WebElement> title = driver.findElements(By.className("property_title"));
		
		System.out.println(title.size());
		
		while(true)
		{
			
			title = driver.findElements(By.className("property_title"));
			System.out.println(title.size());
			
		
		for(int i=0; i<title.size(); i++)
		{
			
			
			
			try{
				title.get(i).click();
				
				
				String subWindowHandler = null;
				Set<String> handles = driver.getWindowHandles(); 
				Iterator<String> iterator = handles.iterator();
				while (iterator.hasNext()){
					 subWindowHandler = iterator.next();
				}
				
				driver.switchTo().window(subWindowHandler);
				
				
				WebElement heading = driver.findElement(By.id("HEADING"));
				restaurant.put("name", heading.getText());
				
				WebElement slim_ranking = driver.findElement(By.className("slim_ranking"));
				WebElement a = slim_ranking.findElement(By.xpath(".//a"));
				String rank[] = a.getText().split(" ");
				
				restaurant.put("type", rank[0]);
				
				WebElement c = driver.findElement(By.xpath("//*[contains(text(), 'Cuisine')]"));
				WebElement p = c.findElement(By.xpath(".."));
				WebElement cuisine = p.findElement(By.xpath(".//div[@class='content']"));
				
				
				StringBuilder cuisinestr = new StringBuilder(cuisine.getText());
				
				for(int j=0; j<cuisinestr.length(); j++)
				{
					if(cuisinestr.charAt(j) == ' ')
					{
						if(cuisinestr.charAt(j-1) == ',')
						{}
						
						else
						{
							cuisinestr.setCharAt(j, '-');
						}
						
					}
						
				}
				
				System.out.println(cuisinestr.toString());
				restaurant.put("cuisine", cuisinestr.toString());
				
				
				WebElement c2 = driver.findElement(By.xpath("//*[contains(text(), 'Restaurant features')]"));
				WebElement p2 = c2.findElement(By.xpath(".."));
				WebElement features = p2.findElement(By.xpath(".//div[@class='content']"));
				
				StringBuilder featurestr = new StringBuilder(features.getText());
				
				for(int j=0; j<featurestr.length(); j++)
				{
					if(featurestr.charAt(j) == ' ')
					{
						if(featurestr.charAt(j-1) == ',')
						{}
						
						else
						{
							featurestr.setCharAt(j, '-');
						}
						
					}
						
				}
				
				System.out.println(featurestr.toString());
				restaurant.put("facilities", featurestr.toString());
				
				
				Thread.sleep(5000);
				
				WebElement map = driver.findElement(By.id("STATIC_MAP"));
				WebElement img = map.findElement(By.xpath(".//img"));
				
				String lat = null;
				String lng= null;
				
				try{
				
				 lat = (String) img.getAttribute("src").substring(img.getAttribute("src").indexOf("center=") + 7, img.getAttribute("src").indexOf(","));
				 lng = (String) img.getAttribute("src").substring(img.getAttribute("src").indexOf(",") + 1, img.getAttribute("src").indexOf("&m"));
				
				}
				
				catch(Exception e)
				{
					driver.close();
					driver.switchTo().window(parent_window);
					
				}
				
				System.out.println(lat);
				System.out.println(lng);
				
				restaurant.put("lat", lat);
				restaurant.put("lng", lng);
				
				
				WebElement city = driver.findElement(By.xpath("//span[@property='addressLocality']"));
				WebElement country = driver.findElement(By.xpath("//span[@property='addressCountry']"));
				WebElement region = driver.findElement(By.xpath("//span[@property='streetAddress']"));
				WebElement addr = driver.findElement(By.xpath("//span[@property='address']"));
				
				System.out.println(city.getText());
				System.out.println(country.getText());
				System.out.println(region.getText());
				
				restaurant.put("city", "Sydney");
				restaurant.put("country", "Australia");
				restaurant.put("region", region.getText());
				restaurant.put("addr", addr.getText());
				
				StringBuilder tagsstr = new StringBuilder();
				
				List<WebElement> tags = driver.findElements(By.className("ui_tagcloud"));
				for(int j=1; j<tags.size(); j++)
				{
					
					String tag = tags.get(j).getText().replace(" ", "-");
					tagsstr.append(tag);
					tagsstr.append(", ");
					
					
				}
				
				System.out.println(tagsstr.toString());
				restaurant.put("tags", tagsstr.toString());
				
				restaurant.put("price", "");
				
				System.out.println(restaurant);
				
				
				
				
				
				
				
				
				
				driver.close();
				
				driver.switchTo().window(parent_window);
				
				
				Map restaurant2 = new HashMap(restaurant);
				
				restaurant2.put("type", "restaurant");
				restaurant2.put("taggedby", "TripAdvisor");
				
				JSONObject send = new JSONObject();
				send.put("data", restaurant2);
				send.put("data2", restaurant);
				
				System.out.println(send);
				
				HttpResponse<String> response = Unirest.post("http://localhost/restaurantTag").header("Content-Type","application/json").body(send).asString();
				
				System.out.println(response.getBody());
				
				HttpResponse<String> response1 = Unirest.post("http://localhost/addRestaurantToIndex/"+response.getBody().toString()).asString();
				HttpResponse<String> response2 = Unirest.post("http://localhost/addRestaurantToOntology/"+response.getBody().toString()).asString();
				
				System.out.println(response1.getBody());
				System.out.println(response2.getBody());
				
				
				
				
				
				
				
				
				
			}
			
			catch(WebDriverException wde){
				
				if(driver.findElements(By.className("xCloseGreen")).size() != 0)
				{
				
				driver.findElement(By.className("xCloseGreen")).click();
				}
			}
			
			
		
		
		
			
			
			
			
			
		}
		
		
		
		
		WebElement next = driver.findElement(By.className("next"));
		next.click();
		
		}
		

	}

}
