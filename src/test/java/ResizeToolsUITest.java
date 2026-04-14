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

public class ResizeToolsUITest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test(priority = 1)
    public void verifyResizeImagePageUI() {
        driver.get("https://www.pixelssuite.com/resize-image");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Resize Image')]")));
        Assert.assertTrue(heading.isDisplayed(), "Resize Image heading is not displayed");
        Assert.assertEquals(heading.getText().trim(), "Resize Image", "Incorrect page heading");

        WebElement uploadText = driver.findElement(
                By.xpath("//*[contains(text(),'Drag and drop your file here')]"));
        Assert.assertTrue(uploadText.isDisplayed(), "Upload drag-and-drop text is not displayed");

        WebElement selectFilesButton = driver.findElement(
                By.xpath("//*[contains(text(),'Select files')]"));
        Assert.assertTrue(selectFilesButton.isDisplayed(), "Select files button is not displayed");

        WebElement supportedText = driver.findElement(
                By.xpath("//*[contains(text(),'Supported:')]"));
        Assert.assertTrue(supportedText.isDisplayed(), "Supported formats text is not displayed");

        WebElement resizePanel = driver.findElement(
                By.xpath("//*[contains(text(),'Resize')]"));
        Assert.assertTrue(resizePanel.isDisplayed(), "Resize panel title is not displayed");

        WebElement previewPanel = driver.findElement(
                By.xpath("//*[contains(text(),'Preview')]"));
        Assert.assertTrue(previewPanel.isDisplayed(), "Preview panel title is not displayed");

        WebElement resizePlaceholder = driver.findElement(
                By.xpath("//*[contains(text(),'Select an image to configure size')]"));
        Assert.assertTrue(resizePlaceholder.isDisplayed(), "Resize placeholder text is not displayed");

        WebElement previewPlaceholder = driver.findElement(
                By.xpath("//*[contains(text(),'No image yet')]"));
        Assert.assertTrue(previewPlaceholder.isDisplayed(), "Preview placeholder text is not displayed");
    }

    @Test(priority = 2)
    public void verifyImageEnlargerPageUI() {
        driver.get("https://www.pixelssuite.com/image-enlarger");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Image Enlarger')]")));
        Assert.assertTrue(heading.isDisplayed(), "Image Enlarger heading is not displayed");
        Assert.assertEquals(heading.getText().trim(), "Image Enlarger", "Incorrect page heading");

        WebElement uploadText = driver.findElement(
                By.xpath("//*[contains(text(),'Drag and drop your file here')]"));
        Assert.assertTrue(uploadText.isDisplayed(), "Upload drag-and-drop text is not displayed");

        WebElement selectFilesButton = driver.findElement(
                By.xpath("//*[contains(text(),'Select files')]"));
        Assert.assertTrue(selectFilesButton.isDisplayed(), "Select files button is not displayed");

        WebElement supportedText = driver.findElement(
                By.xpath("//*[contains(text(),'Supported:')]"));
        Assert.assertTrue(supportedText.isDisplayed(), "Supported formats text is not displayed");

        WebElement enlargePanel = driver.findElement(
                By.xpath("//*[contains(text(),'Enlarge')]"));
        Assert.assertTrue(enlargePanel.isDisplayed(), "Enlarge panel title is not displayed");

        WebElement previewPanel = driver.findElement(
                By.xpath("//*[contains(text(),'Preview')]"));
        Assert.assertTrue(previewPanel.isDisplayed(), "Preview panel title is not displayed");

        WebElement enlargePlaceholder = driver.findElement(
                By.xpath("//*[contains(text(),'Select an image to enlarge')]"));
        Assert.assertTrue(enlargePlaceholder.isDisplayed(), "Enlarge placeholder text is not displayed");

        WebElement previewPlaceholder = driver.findElement(
                By.xpath("//*[contains(text(),'No image yet')]"));
        Assert.assertTrue(previewPlaceholder.isDisplayed(), "Preview placeholder text is not displayed");
    }

    @Test(priority = 3)
    public void verifyBulkResizePageUI() {
        driver.get("https://www.pixelssuite.com/bulk-resize");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Bulk Resize')]")));
        Assert.assertTrue(heading.isDisplayed(), "Bulk Resize heading is not displayed");
        Assert.assertEquals(heading.getText().trim(), "Bulk Resize", "Incorrect page heading");

        WebElement filesSection = driver.findElement(
                By.xpath("//*[contains(text(),'Files')]"));
        Assert.assertTrue(filesSection.isDisplayed(), "Files section title is not displayed");

        WebElement uploadText = driver.findElement(
                By.xpath("//*[contains(text(),'Drag and drop your file here')]"));
        Assert.assertTrue(uploadText.isDisplayed(), "Upload drag-and-drop text is not displayed");

        WebElement selectImagesButton = driver.findElement(
                By.xpath("//*[contains(text(),'Select images')]"));
        Assert.assertTrue(selectImagesButton.isDisplayed(), "Select images button is not displayed");

        WebElement supportedText = driver.findElement(
                By.xpath("//*[contains(text(),'Supported:')]"));
        Assert.assertTrue(supportedText.isDisplayed(), "Supported formats text is not displayed");

        WebElement optionsSection = driver.findElement(
                By.xpath("//*[contains(text(),'Options')]"));
        Assert.assertTrue(optionsSection.isDisplayed(), "Options section title is not displayed");

        WebElement widthInput = driver.findElement(
                By.xpath("//input[@placeholder='Width']"));
        Assert.assertTrue(widthInput.isDisplayed(), "Width input is not displayed");

        WebElement heightInput = driver.findElement(
                By.xpath("//input[@placeholder='Height']"));
        Assert.assertTrue(heightInput.isDisplayed(), "Height input is not displayed");

        WebElement keepAspectCheckbox = driver.findElement(
                By.xpath("//input[@type='checkbox']"));
        Assert.assertTrue(keepAspectCheckbox.isDisplayed(), "Keep aspect checkbox is not displayed");

        WebElement processButton = driver.findElement(
                By.xpath("//*[contains(text(),'Process & Download')]"));
        Assert.assertTrue(processButton.isDisplayed(), "Process & Download button is not displayed");

        WebElement helperText = driver.findElement(
                By.xpath("//*[contains(text(),'Select multiple images to resize')]"));
        Assert.assertTrue(helperText.isDisplayed(), "Bulk resize helper text is not displayed");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}