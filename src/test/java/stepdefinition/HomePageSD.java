package stepdefinition;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import scrapers.SharedContext;
import io.cucumber.java.en.Then;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qa.factory.*;
import com.qa.util.ConfigReader;

import java.io.IOException;
import java.util.List;

public class HomePageSD {
	private WebDriver driver = DriverFactory.getDriver();
	private SharedContext sharedContext = new SharedContext(); 
	private ConfigReader config;
	public HomePageSD() {
        config = new ConfigReader();
        config.init_prop(); 
    }
	
	 @Given("the user navigates to recipe page")
	    public void navigateTohomePage() throws IOException {
	     System.out.println(config.getUrl()); 
	     String url = config.getUrl();
		 driver.get(url);
		 Document document =Jsoup.connect(url).userAgent("chrome").get();
		 Elements links = document.select("a[href]");
		 for(Element link : links)
		 {
			 System.out.println(link.attr("href"));
		 }
	    }

	    @When("the user extracts recipe titles")
	    public void extractRecipeTitles() {

	    	List<WebElement> titleElements = driver.findElements(By.cssSelector(".card__title"));
	        SharedContext.setRecipeTitles(titleElements);
	    }

	    @Then("the user verifies the extracted titles")
	    public void verifyExtractedTitles() {
	    	
	        List<WebElement> titleElements = SharedContext.getRecipeTitles();
	        Assert.assertEquals(titleElements.size() > 0, true, "At least one recipe title should be present");
	        System.out.println("Extracted Recipe Titles:");
	        for (WebElement titleElement : titleElements) {
	        	String title= titleElement.getText();
	            System.out.println("- " + titleElement.getText());
	            sharedContext.writeToExcel(title);
	        }
	    }
}
