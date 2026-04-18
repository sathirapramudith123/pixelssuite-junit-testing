import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PixelsSuiteTestNG {

    WebDriver driver;
    WebDriverWait wait;

    private final String URL = "https://www.pixelssuite.com/";

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private void openHomePage() {
        driver.get(URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
    }

    private boolean isTextPresent(String text) {
        return driver.findElements(By.xpath("//*[contains(text(),'" + text + "')]")).size() > 0;
    }

    // =========================
    // TC01 - Page Load
    // =========================
    @Test(priority = 1)
    public void verifyHomePageLoadsSuccessfully() {
        openHomePage();

        String title = driver.getTitle();
        String currentUrl = driver.getCurrentUrl();

        System.out.println("Page Title: " + title);
        System.out.println("Current URL: " + currentUrl);

        Assert.assertFalse(title.trim().isEmpty(), "Page title is empty");
        Assert.assertTrue(currentUrl.contains("pixelssuite.com"), "URL is incorrect");
    }

    // =========================
    // TC02 - Content Display
    // =========================
    @Test(priority = 2)
    public void verifyHomePageContentDisplayed() {
        openHomePage();

        WebElement body = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

        Assert.assertTrue(body.isDisplayed(), "Page body is not displayed");

        List<WebElement> links = driver.findElements(By.tagName("a"));
        Assert.assertTrue(links.size() > 0, "No links found");
    }

    // =========================
    // TC03 - Navigation Links
    // =========================
    @Test(priority = 3)
    public void verifyNavigationLinksClickable() {
        openHomePage();

        List<WebElement> links = driver.findElements(By.tagName("a"));
        Assert.assertTrue(links.size() > 0, "No links found");

        boolean clicked = false;

        for (WebElement link : links) {
            try {
                if (link.isDisplayed() && link.isEnabled()) {
                    String href = link.getAttribute("href");
                    if (href != null && !href.isEmpty()) {
                        link.click();
                        clicked = true;
                        break;
                    }
                }
            } catch (Exception e) {
                // ignore hidden links
            }
        }

        Assert.assertTrue(clicked, "No clickable link found");
    }

    // =========================
    // TC04 - Images
    // =========================
    @Test(priority = 4)
    public void verifyImagesDisplayed() {
        openHomePage();

        List<WebElement> images = driver.findElements(By.tagName("img"));
        Assert.assertTrue(images.size() > 0, "No images found");
    }

    // =========================
    // TC05 - Buttons
    // =========================
    @Test(priority = 5)
    public void verifyButtonsDisplayed() {
        openHomePage();

        List<WebElement> buttons = driver.findElements(By.tagName("button"));
        Assert.assertTrue(buttons.size() > 0, "No buttons found");
    }

    // =========================
    // TC06 - No 404
    // =========================
    @Test(priority = 6)
    public void verifyPageNot404() {
        openHomePage();

        String source = driver.getPageSource().toLowerCase();

        Assert.assertFalse(source.contains("404"), "Page shows 404 error");
        Assert.assertFalse(source.contains("not found"), "Page shows Not Found");
    }

    // =========================
    // TC07 - Tool Keywords
    // =========================
    @Test(priority = 7)
    public void verifyToolKeywordsVisible() {
        openHomePage();

        boolean found =
                isTextPresent("Rotate") ||
                isTextPresent("Flip") ||
                isTextPresent("Meme") ||
                isTextPresent("Color") ||
                isTextPresent("Text");

        Assert.assertTrue(found, "Tool keywords not found");
    }

    // =========================
    // TC08 - Page Source
    // =========================
    @Test(priority = 8)
    public void verifyPageSourceNotEmpty() {
        openHomePage();

        String source = driver.getPageSource();
        Assert.assertFalse(source.trim().isEmpty(), "Page source is empty");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}