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
import org.testng.annotations.Test;

public class Compress {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test(priority = 1)
    public void verifyCompressImagePageUI() {
        driver.get("https://www.pixelssuite.com/compress-image");

        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Compress Image')]")));
        Assert.assertTrue(heading.isDisplayed(), "Compress Image heading is not displayed");
        Assert.assertEquals(heading.getText().trim(), "Compress Image", "Incorrect page heading");

        WebElement uploadText = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Drag and drop your file here')]")));
        Assert.assertTrue(uploadText.isDisplayed(), "Upload text is not displayed");

        WebElement selectFilesButton = driver.findElement(
                By.xpath("//*[contains(text(),'Select files')]"));
        Assert.assertTrue(selectFilesButton.isDisplayed(), "Select files button is not displayed");

        WebElement supportedText = driver.findElement(
                By.xpath("//*[contains(text(),'Supported:')]"));
        Assert.assertTrue(supportedText.isDisplayed(), "Supported text is not displayed");

        WebElement compressPanel = driver.findElement(
                By.xpath("//*[contains(text(),'Compress')]"));
        Assert.assertTrue(compressPanel.isDisplayed(), "Compress panel title is not displayed");

        WebElement previewPanel = driver.findElement(
                By.xpath("//*[contains(text(),'Preview')]"));
        Assert.assertTrue(previewPanel.isDisplayed(), "Preview panel title is not displayed");

        WebElement compressPlaceholder = driver.findElement(
                By.xpath("//*[contains(text(),'Select an image to compress')]"));
        Assert.assertTrue(compressPlaceholder.isDisplayed(), "Compress placeholder text is not displayed");

        WebElement previewPlaceholder = driver.findElement(
                By.xpath("//*[contains(text(),'No image yet')]"));
        Assert.assertTrue(previewPlaceholder.isDisplayed(), "Preview placeholder text is not displayed");
    }

    @Test(priority = 2)
    public void verifyPngCompressorPageUI() {
        driver.get("https://www.pixelssuite.com/png-compressor");

        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'PNG Compressor')]")));
        Assert.assertTrue(heading.isDisplayed(), "PNG Compressor heading is not displayed");
        Assert.assertEquals(heading.getText().trim(), "PNG Compressor", "Incorrect page heading");

        WebElement uploadText = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Drag and drop your file here')]")));
        Assert.assertTrue(uploadText.isDisplayed(), "Upload text is not displayed");

        WebElement selectFilesButton = driver.findElement(
                By.xpath("//*[contains(text(),'Select files')]"));
        Assert.assertTrue(selectFilesButton.isDisplayed(), "Select files button is not displayed");

        WebElement supportedText = driver.findElement(
                By.xpath("//*[contains(text(),'Supported:')]"));
        Assert.assertTrue(supportedText.isDisplayed(), "Supported text is not displayed");

        WebElement compressPanel = driver.findElement(
                By.xpath("//*[contains(text(),'Compress')]"));
        Assert.assertTrue(compressPanel.isDisplayed(), "Compress panel title is not displayed");

        WebElement previewPanel = driver.findElement(
                By.xpath("//*[contains(text(),'Preview')]"));
        Assert.assertTrue(previewPanel.isDisplayed(), "Preview panel title is not displayed");

        WebElement pngPlaceholder = driver.findElement(
                By.xpath("//*[contains(text(),'Select a PNG image to re-encode')]"));
        Assert.assertTrue(pngPlaceholder.isDisplayed(), "PNG placeholder text is not displayed");

        WebElement previewPlaceholder = driver.findElement(
                By.xpath("//*[contains(text(),'No image yet')]"));
        Assert.assertTrue(previewPlaceholder.isDisplayed(), "Preview placeholder text is not displayed");
    }

    @Test(priority = 3)
    public void verifyGifCompressorPageUI() {
        driver.get("https://www.pixelssuite.com/gif-compressor");

        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'GIF Compressor')]")));
        Assert.assertTrue(heading.isDisplayed(), "GIF Compressor heading is not displayed");
        Assert.assertEquals(heading.getText().trim(), "GIF Compressor", "Incorrect page heading");

        WebElement uploadText = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Drag and drop your file here')]")));
        Assert.assertTrue(uploadText.isDisplayed(), "Upload text is not displayed");

        WebElement selectGifButton = driver.findElement(
                By.xpath("//*[contains(text(),'Select GIF')]"));
        Assert.assertTrue(selectGifButton.isDisplayed(), "Select GIF button is not displayed");

        WebElement supportedText = driver.findElement(
                By.xpath("//*[contains(text(),'Supported:')]"));
        Assert.assertTrue(supportedText.isDisplayed(), "Supported text is not displayed");

        WebElement compressPanel = driver.findElement(
                By.xpath("//*[contains(text(),'Compress')]"));
        Assert.assertTrue(compressPanel.isDisplayed(), "Compress panel title is not displayed");

        WebElement previewPanel = driver.findElement(
                By.xpath("//*[contains(text(),'Preview')]"));
        Assert.assertTrue(previewPanel.isDisplayed(), "Preview panel title is not displayed");

        WebElement gifPlaceholder = driver.findElement(
                By.xpath("//*[contains(text(),'Select a GIF to compress')]"));
        Assert.assertTrue(gifPlaceholder.isDisplayed(), "GIF placeholder text is not displayed");

        WebElement previewPlaceholder = driver.findElement(
                By.xpath("//*[contains(text(),'No GIF yet')]"));
        Assert.assertTrue(previewPlaceholder.isDisplayed(), "GIF preview placeholder text is not displayed");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}