import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Eventful {
	
	public static String type;

	public static void main(String[] args) throws InterruptedException, UnirestException {
		
		
		
		
		
		
		
		Map event = new HashMap();
		
		WebDriver driver = new FirefoxDriver();
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		driver.navigate().to("http://sydney.eventful.com/events/categories");
		
		
		
		
		WebElement category = driver.findElement(By.id("category"));
		category.click();
		
		WebElement category_popover = driver.findElement(By.id("category-popover"));
		
		List<WebElement> links = new ArrayList<WebElement>();
		
		
		
		links =  category_popover.findElements(By.tagName("li"));
		
		
		for(int i=0; i<links.size(); i++)
		{
			
			category = driver.findElement(By.id("category"));
			if(i!=0)
			{
			category.click();
			}
			
			category_popover = driver.findElement(By.id("category-popover"));
			category_popover.findElements(By.tagName("li"));
			links =  category_popover.findElements(By.tagName("li"));
			
			System.out.println(links.get(i).getText());
			type=links.get(i).getText();
			
			
			WebElement a = links.get(i).findElement(By.tagName("a"));
			
			
			a.click();
			
			
			Thread.sleep(5000);
			
			
			WebElement event_list = driver.findElement(By.id("events-list"));
			
			List<WebElement> events = new ArrayList<WebElement>();
			events =  event_list.findElements(By.tagName("li"));
			
			
			
			for(int j=0 ; j<events.size()-1; j++)
			{
				
				Thread.sleep(5000);
				
				event_list = driver.findElement(By.id("events-list"));
				events =  event_list.findElements(By.tagName("li"));
				
				WebElement a2 = events.get(j).findElement(By.tagName("a"));
				a2.click();
				
				
				if(driver.findElements(By.id("panel-site-interrupter")).size() != 0)
				{
					WebElement close = driver.findElement(By.className("close"));
					try{
					close.click();
					}
					catch(ElementNotVisibleException enve){}
					
					scrap(driver, event);
					
					driver.navigate().back();
				}
				
				else{
					
					
					scrap(driver, event);
					
					driver.navigate().back();
				}
				
				
				
			}
			
			
			
			
			
			
		}
		
		
		
		

	}
	
	
	public static void scrap(WebDriver driver, Map event) throws UnirestException{
		
		event.put("taggedby", "Eventful");
		event.put("type", type);
		
		if(driver.findElements(By.className("has_performers")).size() != 0)
		{
			WebElement performer_list = driver.findElement(By.className("event-performers-list"));
			WebElement performerSpan = performer_list.findElement(By.xpath(".//span[@itemprop='name']"));
			
			event.put("performer", performerSpan.getText());
			
		}
		
		else
		{
			event.put("performer", "not any");
		}
		
		if(driver.findElements(By.xpath("//a[@data-ga-label='Venue Title Link']")).size() != 0)
		{
		
		WebElement organizer = driver.findElement(By.xpath("//a[@data-ga-label='Venue Title Link']"));
		event.put("organizer", organizer.getText());
		}
		
		else
		{
			event.put("organizer", "not any");
		}
		
		if(driver.findElements(By.className("event-date")).size() != 0)
		{
		
		WebElement date = driver.findElement(By.className("event-date"));
		event.put("date", date.getText());
		}
		
		else{
			event.put("date", "not any");
		}
		
		if(driver.findElements(By.xpath("//h1[@itemprop='name']")).size() != 0)
		{
		
		WebElement name = driver.findElement(By.xpath("//h1[@itemprop='name']"));
		WebElement nameSpan = name.findElement(By.tagName("span"));
		event.put("name", nameSpan.getText());
		
		}
		
		else{
			event.put("name", "not any");
		}
		
		String addr2 = null;
		
		if(driver.findElements(By.xpath("//p[@itemprop='address']")).size() != 0)
		{
		
		WebElement addr = driver.findElement(By.xpath("//p[@itemprop='address']"));
		event.put("addr", addr.getText());
		
		addr2 = addr.getText().replace(" ", "+").replace("\n", "").replace("\r", "");
		
		}
		
		else{
			event.put("addr", "not any");
		}
		
		if(driver.findElements(By.xpath("//span[@itemprop='streetAddress']")).size() != 0)
		{
		
		WebElement region = driver.findElement(By.xpath("//span[@itemprop='streetAddress']"));
		event.put("region", region.getText());
		}
		
		else{
			event.put("region", "not any");
			
		}
		
		if(driver.findElements(By.xpath("//span[@itemprop='addressLocality']")).size()!=0)
		{
		
		WebElement city = driver.findElement(By.xpath("//span[@itemprop='addressLocality']"));
		event.put("city", city.getText());
		}
		
		else
		{
			event.put("city", "not any");
		}
		
		event.put("country", "Australia");
		
		
		
		try{
			
			
			
			HttpResponse<JsonNode> response = Unirest.get("https://maps.googleapis.com/maps/api/geocode/json?address="+ addr2 +"&key=AIzaSyCV1fQD2VC6HoNbuuSPkE0q_QZvDf117PY")
					  
					  .asJson();
					
					JSONArray results = (JSONArray) response.getBody().getObject().get("results");
					JSONObject resultsobj = (JSONObject) results.get(0);
					JSONObject geometry = (JSONObject) resultsobj.get("geometry");
					JSONObject location = (JSONObject) geometry.get("location");
					
					event.put("lat", location.get("lat"));
					event.put("lng", location.get("lng"));
			
			
			
		}
		catch(Exception e)
		{
			event.put("lat", "null");
			event.put("lng", "null");
			
			System.out.println(e.getMessage());
			
		}
		
		Map event2 = new HashMap(event);
		
		
		event2.put("type", "event");
		
		JSONObject send = new JSONObject();
		send.put("data", event2);
		send.put("data2", event);
		
		System.out.println(send);
		
		HttpResponse<String> response = Unirest.post("http://localhost/eventTag").header("Content-Type","application/json").body(send).asString();
		
		System.out.println(response.getBody());
		
		HttpResponse<String> response1 = Unirest.post("http://localhost/addEventToIndex/"+response.getBody().toString()).asString();
		HttpResponse<String> response2 = Unirest.post("http://localhost/addEventToOntology/"+response.getBody().toString()).asString();
		
		System.out.println(response1.getBody());
		System.out.println(response2.getBody());

		
		System.out.println(event);
		
	}

}
