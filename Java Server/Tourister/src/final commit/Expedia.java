import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Expedia {

	public static void main(String[] args) {
		
		
		
		  
        XSSFWorkbook workbook = new XSSFWorkbook(); 
         
        
        XSSFSheet sheet = workbook.createSheet("Attractions Data");
          
      
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        data.put("1", new Object[] {"name", "price", "type", "duration", "organizer", "desc", "highlights", "includes", "addr"});
       
        WebDriver driver = new FirefoxDriver();  
        
        driver.navigate().to("https://www.expedia.co.in/things-to-do/?location=sydney");
        
        String parent_window = driver.getWindowHandle();	
        
       while(driver.findElement(By.className("pagination-next")).isEnabled())
       {
    	   
    	   
    	   List<WebElement> activities = driver.findElements(By.cssSelector("div.flex-card.activity-tile"));
    	   System.out.println(activities.size());
    	   
    	   for(int i=0; i<activities.size(); i++)
    	   {
    		   
    		   
    		   activities.get(i).click();
    		   
    		   String subWindowHandler = null;
   			Set<String> handles = driver.getWindowHandles(); 
   			Iterator<String> iterator = handles.iterator();
   			while (iterator.hasNext()){
   				 subWindowHandler = iterator.next();
   			}
   			
   			driver.switchTo().window(subWindowHandler);
    		   
    		 WebElement name = driver.findElement(By.id("activityTitle"));
    		 System.out.println(name.getText());
    		 
    		 
    		 
    		 driver.close();
    		 
    		 driver.switchTo().window(parent_window);
    		   
    		   
    	   }
    	   
    	   
    	   WebElement next = driver.findElement(By.className("pagination-next"));
    	   next.click();
    	   
    	   
    	   
    	   
    	   
    	   
    	   
       }
        
        
        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset)
        {
            Row row = sheet.createRow(rownum++);
            Object [] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr)
            {
               Cell cell = row.createCell(cellnum++);
               if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Integer)
                    cell.setCellValue((Integer)obj);
            }
        }
        try
        {
            
            FileOutputStream out = new FileOutputStream(new File("C:\\Test\\scraped_attractions.xlsx"));
            workbook.write(out);
            out.close();
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
		
		
		

	}

}
