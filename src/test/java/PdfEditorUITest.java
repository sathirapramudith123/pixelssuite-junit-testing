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

public class PdfEditorUITest {

    WebDriver driver;
    WebDriverWait wait;

    private final String URL = "https://www.pixelssuite.com/pdf-editor";

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // =========================
    // TC01 - Page Load
    // =========================
    @Test(priority = 1)
    public void verifyPdfEditorPageLoadsSuccessfully() {
        driver.get(URL);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

        String title = driver.getTitle();
        String currentUrl = driver.getCurrentUrl();

        Assert.assertFalse(title.trim().isEmpty(), "Page title is empty");
        Assert.assertTrue(currentUrl.contains("pdf-editor"), "URL does not contain pdf-editor");
    }

    // =========================
    // TC02 - UI Elements
    // =========================
    @Test(priority = 2)
    public void verifyPdfEditorUIElementsDisplayed() {
        driver.get(URL);

        WebElement fileInput = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='file']")));

        Assert.assertTrue(fileInput.isDisplayed(), "File input is not displayed");

        List<WebElement> buttons = driver.findElements(By.tagName("button"));
        Assert.assertTrue(buttons.size() > 0, "No buttons found");
    }

    // =========================
    // TC03 - Toolbar
    // =========================
    @Test(priority = 3)
    public void verifyToolbarOptionsDisplayed() {
        driver.get(URL);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

        List<WebElement> selects = driver.findElements(By.tagName("select"));
        List<WebElement> ranges = driver.findElements(By.xpath("//input[@type='range']"));
        List<WebElement> buttons = driver.findElements(By.tagName("button"));

        Assert.assertTrue(
                selects.size() > 0 || ranges.size() > 0 || buttons.size() > 0,
                "Toolbar controls not displayed"
        );
    }

    // =========================
    // TC04 - Valid Upload
    // =========================
    @Test(priority = 4)
    public void verifyValidPdfUpload() {
        driver.get(URL);

        WebElement fileInput = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='file']")));

        // CHANGE THIS PATH
        fileInput.sendKeys("C:\\test-files\\sample.pdf");

        Assert.assertTrue(
                driver.getPageSource().contains("PDF") ||
                driver.getPageSource().contains("Preview"),
                "PDF upload not successful"
        );
    }

    // =========================
    // TC05 - Invalid Upload
    // =========================
    @Test(priority = 5)
    public void verifyInvalidFileUpload() {
        driver.get(URL);

        WebElement fileInput = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='file']")));

        // CHANGE THIS PATH
        fileInput.sendKeys("C:\\test-files\\invalid.txt");

        boolean handled =
                driver.getPageSource().toLowerCase().contains("invalid") ||
                driver.getPageSource().toLowerCase().contains("error") ||
                !driver.getPageSource().contains("invalid.txt");

        Assert.assertTrue(handled, "Invalid file was not rejected");
    }

    // =========================
    // TC06 - Buttons Clickable
    // =========================
    @Test(priority = 6)
    public void verifyToolbarButtonsClickable() {
        driver.get(URL);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

        List<WebElement> buttons = driver.findElements(By.tagName("button"));

        Assert.assertTrue(buttons.size() > 0, "No buttons found");

        for (WebElement btn : buttons) {
            try {
                if (btn.isDisplayed() && btn.isEnabled()) {
                    btn.click();
                    break;
                }
            } catch (Exception e) {
                // ignore hidden elements
            }
        }
    }

    // =========================
    // TC07 - Check No 404
    // =========================
    @Test(priority = 7)
    public void verifyPageNot404() {
        driver.get(URL);

        String source = driver.getPageSource().toLowerCase();

        Assert.assertFalse(source.contains("404"), "Page shows 404 error");
        Assert.assertFalse(source.contains("not found"), "Page shows Not Found");
    }

    // =========================
    // TC08 - File Input Exists
    // =========================
    @Test(priority = 8)
    public void verifyFileInputPresent() {
        driver.get(URL);

        List<WebElement> fileInputs = driver.findElements(By.cssSelector("input[type='file']"));

        Assert.assertTrue(fileInputs.size() > 0, "File input is not present");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}