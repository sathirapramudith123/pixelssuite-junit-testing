import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;



public class PixelsSuiteTestNG {

	WebDriver driver;
	WebDriverWait wait;
	
	@BeforeMethod
	public void setUp() {
		//System.setProperty("webdriver.chorme.driver", "C:\\\\chromedriver\\\\chromedriver.exe");
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	}
	
	@Test(priority = 1)
	public void verifyHomePageLoadsSuccessfully() {
		driver.get("https://www.pixelssuite.com/");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
		
		String title = driver.getTitle();
		String currentUrl = driver.getCurrentUrl();
		
		System.out.println("Page Title"+ title);
		System.out.println("Current URL"+ currentUrl);
		
		Assert.assertFalse(title.trim().isEmpty(), "Page title is empty");
		Assert.assertTrue(currentUrl.contains("pixelssuite.com"), "URL does not contain pixelssuite.com");
	}
	
	
	@Test(priority = 2)
	public void verifyHomePageContentDisplayed() {
		driver.get("https://www.pixelssuite.com/");
		
		WebElement body = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
		Assert.assertTrue(body.isDisplayed(), "Page body is not displayed");
		
		List<WebElement> links = driver.findElements(By.tagName("a"));
		System.out.println("Total links found: " + links.size());
		Assert.assertTrue(links.size()>0, "No links found on the homepage");
	}
	

	
	@AfterMethod
	public void tearDown() {
		if(driver != null) {
			driver.quit();
		}
	}
	
}
