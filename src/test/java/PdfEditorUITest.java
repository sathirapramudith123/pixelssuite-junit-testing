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

public class PdfEditorUITest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test(priority = 1)
    public void verifyPdfEditorPageLoadsSuccessfully() {
        driver.get("https://www.pixelssuite.com/pdf-editor");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

        String title = driver.getTitle();
        String currentUrl = driver.getCurrentUrl();

        System.out.println("Page Title: " + title);
        System.out.println("Current URL: " + currentUrl);

        Assert.assertFalse(title.trim().isEmpty(), "Page title is empty");
        Assert.assertTrue(currentUrl.contains("pdf-editor"), "URL does not contain pdf-editor");
    }

    @Test(priority = 2)
    public void verifyPdfEditorUIElementsDisplayed() {
        driver.get("https://www.pixelssuite.com/pdf-editor");

        WebElement body = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
        Assert.assertTrue(body.isDisplayed(), "Page body is not displayed");

        WebElement chooseFileInput = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='file']")));
        Assert.assertTrue(chooseFileInput.isDisplayed(), "Choose File input is not displayed");

        List<WebElement> buttons = driver.findElements(By.tagName("button"));
        System.out.println("Total buttons found: " + buttons.size());
        Assert.assertTrue(buttons.size() > 0, "No buttons found on the PDF Editor page");
    }

    @Test(priority = 3)
    public void verifyToolbarOptionsDisplayed() {
        driver.get("https://www.pixelssuite.com/pdf-editor");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

        List<WebElement> selects = driver.findElements(By.tagName("select"));
        List<WebElement> ranges = driver.findElements(By.xpath("//input[@type='range']"));
        List<WebElement> buttons = driver.findElements(By.tagName("button"));

        System.out.println("Select count: " + selects.size());
        System.out.println("Range input count: " + ranges.size());
        System.out.println("Button count: " + buttons.size());

        Assert.assertTrue(buttons.size() > 0, "Toolbar buttons are not displayed");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}