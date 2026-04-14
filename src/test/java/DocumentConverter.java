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

public class DocumentConverter {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Test(priority = 1)
    public void verifyImageToPdfPageUI() {
        driver.get("https://www.pixelssuite.com/image-to-pdf");

        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Image') and contains(text(),'PDF')]")));
        Assert.assertTrue(heading.isDisplayed(), "Image to PDF heading is not displayed");

        WebElement uploadText = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Drag and drop your file here')]")));
        Assert.assertTrue(uploadText.isDisplayed(), "Upload text is not displayed");

        WebElement selectImagesButton = driver.findElement(
                By.xpath("//*[contains(text(),'Select Images')]"));
        Assert.assertTrue(selectImagesButton.isDisplayed(), "Select Images button is not displayed");

        WebElement selectedImagesPanel = driver.findElement(
                By.xpath("//*[contains(text(),'Selected Images')]"));
        Assert.assertTrue(selectedImagesPanel.isDisplayed(), "Selected Images panel is not displayed");

        WebElement previewPanel = driver.findElement(
                By.xpath("//*[contains(text(),'Preview')]"));
        Assert.assertTrue(previewPanel.isDisplayed(), "Preview panel is not displayed");

        WebElement infoText = driver.findElement(
                By.xpath("//*[contains(text(),'Add images to combine into a PDF')]"));
        Assert.assertTrue(infoText.isDisplayed(), "Selected images info text is not displayed");

        WebElement pageLabel = driver.findElement(
                By.xpath("//*[contains(text(),'Page')]"));
        Assert.assertTrue(pageLabel.isDisplayed(), "Page label is not displayed");

        WebElement orientationLabel = driver.findElement(
                By.xpath("//*[contains(text(),'Orientation')]"));
        Assert.assertTrue(orientationLabel.isDisplayed(), "Orientation label is not displayed");

        WebElement arrangeLabel = driver.findElement(
                By.xpath("//*[contains(text(),'Arrange')]"));
        Assert.assertTrue(arrangeLabel.isDisplayed(), "Arrange label is not displayed");

        WebElement pagesLabel = driver.findElement(
                By.xpath("//*[contains(text(),'Pages')]"));
        Assert.assertTrue(pagesLabel.isDisplayed(), "Pages label is not displayed");

        WebElement createPdfButton = driver.findElement(
                By.xpath("//*[contains(text(),'Create PDF')]"));
        Assert.assertTrue(createPdfButton.isDisplayed(), "Create PDF button is not displayed");

        WebElement previewText = driver.findElement(
                By.xpath("//*[contains(text(),'Selected images will preview here')]"));
        Assert.assertTrue(previewText.isDisplayed(), "Preview placeholder text is not displayed");
    }

    @Test(priority = 2)
    public void verifyPdfToWordPageUI() {
        driver.get("https://www.pixelssuite.com/pdf-to-word");

        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'PDF') and contains(text(),'Word')]")));
        Assert.assertTrue(heading.isDisplayed(), "PDF to Word heading is not displayed");

        WebElement uploadText = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Drag and drop your file here')]")));
        Assert.assertTrue(uploadText.isDisplayed(), "Upload text is not displayed");

        WebElement selectPdfButton = driver.findElement(
                By.xpath("//*[contains(text(),'Select PDF')]"));
        Assert.assertTrue(selectPdfButton.isDisplayed(), "Select PDF button is not displayed");

        WebElement selectedPanel = driver.findElement(
                By.xpath("//*[contains(text(),'Selected')]"));
        Assert.assertTrue(selectedPanel.isDisplayed(), "Selected panel is not displayed");

        WebElement selectedText = driver.findElement(
                By.xpath("//*[contains(text(),'Choose a text-based PDF exported from Word for best results')]"));
        Assert.assertTrue(selectedText.isDisplayed(), "Selected panel helper text is not displayed");
    }

    @Test(priority = 3)
    public void verifyWordToPdfPageUI() {
        driver.get("https://www.pixelssuite.com/word-to-pdf");

        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Word') and contains(text(),'PDF')]")));
        Assert.assertTrue(heading.isDisplayed(), "Word to PDF heading is not displayed");

        WebElement uploadText = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Drag and drop your file here')]")));
        Assert.assertTrue(uploadText.isDisplayed(), "Upload text is not displayed");

        WebElement selectWordButton = driver.findElement(
                By.xpath("//*[contains(text(),'Select Word')]"));
        Assert.assertTrue(selectWordButton.isDisplayed(), "Select Word button is not displayed");

        WebElement selectedPanel = driver.findElement(
                By.xpath("//*[contains(text(),'Selected')]"));
        Assert.assertTrue(selectedPanel.isDisplayed(), "Selected panel is not displayed");

        WebElement selectedText = driver.findElement(
                By.xpath("//*[contains(text(),'Choose a .docx file for best compatibility')]"));
        Assert.assertTrue(selectedText.isDisplayed(), "Selected panel helper text is not displayed");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}