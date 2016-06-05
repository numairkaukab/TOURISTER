import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.math.util.MathUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.firefox.FirefoxDriver;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.body.MultipartBody;
import com.mashape.unirest.request.body.RequestBodyEntity;



public class Booking {

	
	
	
	public static void main(String[] args) throws UnirestException, InterruptedException, IOException {
		
		

		
		
		
		
	
		
		WebDriver driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		 
		
		    Map<String, String> hotel = new HashMap<String, String>();
		
		    
		
		
			driver.navigate().to("http://www.booking.com");
			
			WebElement currency = driver.findElement(By.className("long_currency_text"));
			currency.click();
			WebElement usd = driver.findElement(By.className("currency_USD"));
			usd.click();
			
			
			
			
			
			
			
			WebElement calender = driver.findElement(By.className("sb-date-picker__dates"));
			calender.click();
			WebElement today = driver.findElement(By.className("c2-day-s-today"));
			today.click();
			
			
			
			WebElement calender2 = driver.findElement(By.className("sb-date-picker__dates"));
			calender2.click();
			
			WebElement today2 = driver.findElement(By.className("c2-day-s-last-in-range"));
			today2.click();
			
            WebElement ss = driver.findElement(By.id("ss"));
			
			ss.sendKeys("sydney");
			ss.sendKeys(Keys.RETURN);
			
		  
            
		   String parent_window = driver.getWindowHandle();	
		
		
		
		
		
		try{
		
			
			
		WebElement disamb = driver.findElement(By.className("disambiguation-image"));
		
		WebElement item_name = driver.findElement(By.linkText("Sydney"))	;
		item_name.click();
		
		
		
		scrap(driver, hotel, parent_window);
		
		
		
		}
		catch(NoSuchElementException e)
		{
			
		     scrap(driver, hotel, parent_window);
		}
		
			
			
			
			
		
		
	    }
		
		

	
		
		
		
	
	
	
	public static void scrap(WebDriver driver, Map hotel, String parent_window) throws UnirestException, InterruptedException, IOException{
		
		
		WebElement next_page = driver.findElement(By.className("paging-next"));
		
	
		
		WebElement pages = driver.findElement(By.className("x-list"));
		WebElement last_page = pages.findElement(By.cssSelector("li:last-child a"));
		
		System.out.println(last_page.getText());
		System.out.println(next_page.getText());
		int pageSize = Integer.parseInt(last_page.getText());
		
		for(int l=0; l<pageSize; l++)
		{
		
		List<WebElement> hotel_names = driver.findElements(By.className("sr-hotel__name"));
		
		next_page = driver.findElement(By.className("paging-next"));
		
		System.out.println(hotel_names.size());
		
		
		
	
		
		for(int i=0; i<hotel_names.size(); i++)
		{
			
			
			
			
			
			
			
			hotel_names.get(i).click();
			
			
			
			 
			
			
			
			
			String subWindowHandler = null;
			Set<String> handles = driver.getWindowHandles(); 
			Iterator<String> iterator = handles.iterator();
			while (iterator.hasNext()){
				 subWindowHandler = iterator.next();
			}
			
			driver.switchTo().window(subWindowHandler);
			
			String starsStr = null;
			String priceStr;
			String roomTypeStr;
			
			if(driver.findElements(By.className("stars")).size() != 0  )
			{
				WebElement stars = driver.findElement(By.className("stars"));
				WebElement stars2 = stars.findElement(By.xpath(".//span"));
				starsStr = stars.getText().toString().replace("stars", "").trim(); 
			}
			
			else
			{
				WebElement stars = driver.findElement(By.className("ratings_circles_any"));
				WebElement stars2 = stars.findElement(By.xpath(".//span"));
				starsStr = stars2.getText().toString().replace("Unofficial rating: ", "").trim(); 
			}
			
			
			if(driver.findElements(By.className("rooms-table-room-price")).size()!=0)
			{
				WebElement price = driver.findElement(By.className("rooms-table-room-price"));
				String priceStr2 = price.getText().toString().replace("US$", "").trim();
				int Price = Integer.parseInt(priceStr2);
				int priceRounded = (int) MathUtils.round(Price, -2);
				priceStr = Integer.toString(priceRounded);
				
			}
			
			else{
				 priceStr = "null";
			}
			

			if(driver.findElements(By.className("roomType")).size() != 0)
			{
				WebElement roomType = driver.findElement(By.className("roomType"));
				WebElement roomType2 = roomType.findElement(By.className("jqrt"));
				 roomTypeStr = roomType2.getText().toString();
			}
			
			else{
				
				 roomTypeStr = "null";
			
			}

				
			
			
			
			
			WebElement region = driver.findElement(By.className("hp_address_subtitle"));
			
			String regionStr[] =region.getText().toString().split(",");
			String region1 = regionStr[0];
			String region2 = regionStr[1];
			
			String regionFinal = region1.concat(","+region2);
			
			hotel.put("region", regionFinal);
			hotel.put("addr", regionFinal);
			
			List<WebElement> hotel_features = driver.findElements(By.className("facilitiesChecklistSection"));
			StringBuilder features = new StringBuilder();
			
			for(int j=0; j<hotel_features.size(); j++)
			{
				List<WebElement> hotel_features2 = hotel_features.get(j).findElements(By.xpath(".//li"));
				
				for(int k=0; k<hotel_features2.size(); k++)
				{
					
					
					
					features.append(hotel_features2.get(k).getText().replace(" ", "-").replace(".", ""));
					features.append(",");
				}
				
			}
			
			hotel.put("features", features.toString());
			
			Object lat =   ((JavascriptExecutor)driver).executeScript("return booking.env.b_map_center_latitude;");
			Object lng =   ((JavascriptExecutor)driver).executeScript("return booking.env.b_map_center_longitude;");
			Object type =   ((JavascriptExecutor)driver).executeScript("return window.utag_data.atnm;");
			Object city =   ((JavascriptExecutor)driver).executeScript("return window.utag_data.city_name;");
			Object country =   ((JavascriptExecutor)driver).executeScript("return window.utag_data.country_name;");
			
			
			String latStr= lat.toString();
			String lngStr = lng.toString();
			String typeStr = type.toString().substring(0, type.toString().length()-1) + " ";;
			String cityStr = city.toString();
			String countryStr = country.toString();
			
			
			   
			String typeFinal = typeStr.trim();
			
			
				
			
			
			
			hotel.put("lat", latStr);
			hotel.put("lng", lngStr);
			hotel.put("type", typeFinal);
			hotel.put("city", cityStr);
			hotel.put("country", countryStr);
			
			hotel.put("taggedby", "Booking.com");
			
			
			
			String[] roomFirstTwo = roomTypeStr.split(" ");
			StringBuilder room_type = new StringBuilder();
			
			for(int m=0; m<roomFirstTwo.length; m++)
			{
				room_type.append(roomFirstTwo[m] + " ");
				
				if(m==1)
				{
					break;
				}
			}
			
			String room_type_final = room_type.toString().trim();
			
			
			hotel.put("stars", starsStr);
			hotel.put("total", priceStr);
			hotel.put("room_type", room_type_final);
			
			
			driver.close();
			driver.switchTo().window(parent_window);
			
			String hotel_name = hotel_names.get(i).getText();
			
			
			
			
			
			
			
			hotel.put("name", hotel_name);
			
		
			
			System.out.println(hotel);
			
			Map hotel2 = new HashMap(hotel);
			
			hotel2.put("type", "hotel");
			
			JSONObject send = new JSONObject();
			send.put("data", hotel2);
			send.put("data2", hotel);
			
			System.out.println(send);
			
			HttpResponse<String> response = Unirest.post("http://localhost/hotelTag").header("Content-Type","application/json").body(send).asString();
			
			System.out.println(response.getBody());
			
			HttpResponse<String> response1 = Unirest.post("http://localhost/addHotelToIndex/"+response.getBody().toString()).asString();
			HttpResponse<String> response2 = Unirest.post("http://localhost/addHotelToOntology/"+response.getBody().toString()).asString();
			
			System.out.println(response1.getBody());
			System.out.println(response2.getBody());
			
		
			
		
		}
		
		try{
			next_page.click();
			
			Thread.sleep(10000);
			
			
			}
			catch(org.openqa.selenium.WebDriverException wde)
			{
				
			}
		
		
		
		
		
		}
		
		
	}
	
}
