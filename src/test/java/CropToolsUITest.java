import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CropToolsUITest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @DataProvider(name = "cropPages")
    public Object[][] cropPages() {
        return new Object[][] {
            {"https://www.pixelssuite.com/crop-jpg", "Crop JPG"},
            {"https://www.pixelssuite.com/crop-png", "Crop PNG"},
            {"https://www.pixelssuite.com/crop-webp", "Crop WebP"}
        };
    }

    @Test(dataProvider = "cropPages", priority = 1)
    public void verifyCropPageLoadsSuccessfully(String url, String expectedHeading) {
        driver.get(url);

        WebElement body = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

        Assert.assertTrue(body.isDisplayed(), "Page body is not displayed");
        Assert.assertTrue(driver.getCurrentUrl().contains("crop"),
                "URL does not contain crop");

        WebElement heading = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'" + expectedHeading + "')]")));
        Assert.assertTrue(heading.isDisplayed(), "Page heading is not visible");
        Assert.assertEquals(heading.getText().trim(), expectedHeading,
                "Page heading text is incorrect");
    }

    @Test(dataProvider = "cropPages", priority = 2)
    public void verifyUploadSectionDisplayed(String url, String expectedHeading) {
        driver.get(url);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

        WebElement heading = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'" + expectedHeading + "')]")));
        Assert.assertEquals(heading.getText().trim(), expectedHeading,
                "Page heading text is incorrect");

        WebElement uploadText = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Drag and drop your file here')]")));
        Assert.assertTrue(uploadText.isDisplayed(),
                "Drag and drop text is not displayed");

        WebElement browseText = driver.findElement(
                By.xpath("//*[contains(text(),'click to browse from your device')]"));
        Assert.assertTrue(browseText.isDisplayed(),
                "Browse helper text is not displayed");

        WebElement selectFilesButton = driver.findElement(
                By.xpath("//*[contains(text(),'Select files')]"));
        Assert.assertTrue(selectFilesButton.isDisplayed(),
                "Select files button is not displayed");
        Assert.assertTrue(selectFilesButton.isEnabled(),
                "Select files button is not enabled");

        WebElement supportedText = driver.findElement(
                By.xpath("//*[contains(text(),'Supported:')]"));
        Assert.assertTrue(supportedText.isDisplayed(),
                "Supported formats text is not displayed");
    }

    @Test(dataProvider = "cropPages", priority = 3)
    public void verifyCropAndPreviewPanelsDisplayed(String url, String expectedHeading) {
        driver.get(url);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

        WebElement cropPanelTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Crop')]")));
        Assert.assertTrue(cropPanelTitle.isDisplayed(),
                "Crop panel title is not displayed");

        WebElement previewPanelTitle = driver.findElement(
                By.xpath("//*[contains(text(),'Preview')]"));
        Assert.assertTrue(previewPanelTitle.isDisplayed(),
                "Preview panel title is not displayed");

        WebElement cropPlaceholder = driver.findElement(
                By.xpath("//*[contains(text(),'Select an image to crop')]"));
        Assert.assertTrue(cropPlaceholder.isDisplayed(),
                "Crop placeholder text is not displayed");

        WebElement previewPlaceholder = driver.findElement(
                By.xpath("//*[contains(text(),'No image yet')]"));
        Assert.assertTrue(previewPlaceholder.isDisplayed(),
                "Preview placeholder text is not displayed");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}