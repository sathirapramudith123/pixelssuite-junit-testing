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

public class MoreTools {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test(priority = 1)
    public void verifyMemeGeneratorPageUI() {
        driver.get("https://www.pixelssuite.com/meme-generator");

        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Meme Generator')]")));
        Assert.assertTrue(heading.isDisplayed(), "Meme Generator heading is not displayed");
        Assert.assertEquals(heading.getText().trim(), "Meme Generator", "Incorrect page heading");

        WebElement uploadText = driver.findElement(By.xpath("//*[contains(text(),'Drag and drop your file here')]"));
        Assert.assertTrue(uploadText.isDisplayed(), "Upload text is not displayed");

        WebElement selectFilesButton = driver.findElement(By.xpath("//*[contains(text(),'Select files')]"));
        Assert.assertTrue(selectFilesButton.isDisplayed(), "Select files button is not displayed");

        WebElement textStylePanel = driver.findElement(By.xpath("//*[contains(text(),'Text & Style')]"));
        Assert.assertTrue(textStylePanel.isDisplayed(), "Text & Style panel is not displayed");

        WebElement previewPanel = driver.findElement(By.xpath("//*[contains(text(),'Preview')]"));
        Assert.assertTrue(previewPanel.isDisplayed(), "Preview panel is not displayed");

        WebElement memePlaceholder = driver.findElement(By.xpath("//*[contains(text(),'Select an image to add meme text')]"));
        Assert.assertTrue(memePlaceholder.isDisplayed(), "Meme placeholder text is not displayed");

        WebElement previewPlaceholder = driver.findElement(By.xpath("//*[contains(text(),'No image yet')]"));
        Assert.assertTrue(previewPlaceholder.isDisplayed(), "Preview placeholder is not displayed");
    }

    @Test(priority = 2)
    public void verifyColorPickerPageUI() {
        driver.get("https://www.pixelssuite.com/color-picker");

        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Color Picker')]")));
        Assert.assertTrue(heading.isDisplayed(), "Color Picker heading is not displayed");
        Assert.assertEquals(heading.getText().trim(), "Color Picker", "Incorrect page heading");

        WebElement leftPanel = driver.findElement(By.xpath("//*[text()='Color Picker']"));
        Assert.assertTrue(leftPanel.isDisplayed(), "Color Picker panel is not displayed");

        WebElement pickPanel = driver.findElement(By.xpath("//*[text()='Pick']"));
        Assert.assertTrue(pickPanel.isDisplayed(), "Pick panel is not displayed");

        WebElement selectedColorText = driver.findElement(By.xpath("//*[contains(text(),'Selected Color')]"));
        Assert.assertTrue(selectedColorText.isDisplayed(), "Selected Color text is not displayed");

        WebElement hexCodeText = driver.findElement(By.xpath("//*[contains(text(),'Hex Code')]"));
        Assert.assertTrue(hexCodeText.isDisplayed(), "Hex Code text is not displayed");

        WebElement otherFormatsText = driver.findElement(By.xpath("//*[contains(text(),'Other Color Formats')]"));
        Assert.assertTrue(otherFormatsText.isDisplayed(), "Other Color Formats text is not displayed");

        WebElement rgbButton = driver.findElement(By.xpath("//*[text()='RGB']"));
        Assert.assertTrue(rgbButton.isDisplayed(), "RGB button is not displayed");

        WebElement hsvButton = driver.findElement(By.xpath("//*[text()='HSV']"));
        Assert.assertTrue(hsvButton.isDisplayed(), "HSV button is not displayed");

        WebElement hslButton = driver.findElement(By.xpath("//*[text()='HSL']"));
        Assert.assertTrue(hslButton.isDisplayed(), "HSL button is not displayed");

        WebElement cmykButton = driver.findElement(By.xpath("//*[text()='CMYK']"));
        Assert.assertTrue(cmykButton.isDisplayed(), "CMYK button is not displayed");

        WebElement copyButtons = driver.findElement(By.xpath("(//*[text()='Copy'])[1]"));
        Assert.assertTrue(copyButtons.isDisplayed(), "Copy button is not displayed");
    }

    @Test(priority = 3)
    public void verifyRotateImagePageUI() {
        driver.get("https://www.pixelssuite.com/rotate-image");

        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Rotate Image')]")));
        Assert.assertTrue(heading.isDisplayed(), "Rotate Image heading is not displayed");
        Assert.assertEquals(heading.getText().trim(), "Rotate Image", "Incorrect page heading");

        WebElement uploadText = driver.findElement(By.xpath("//*[contains(text(),'Drag and drop your file here')]"));
        Assert.assertTrue(uploadText.isDisplayed(), "Upload text is not displayed");

        WebElement selectFilesButton = driver.findElement(By.xpath("//*[contains(text(),'Select files')]"));
        Assert.assertTrue(selectFilesButton.isDisplayed(), "Select files button is not displayed");

        WebElement rotatePanel = driver.findElement(By.xpath("//*[text()='Rotate']"));
        Assert.assertTrue(rotatePanel.isDisplayed(), "Rotate panel is not displayed");

        WebElement previewPanel = driver.findElement(By.xpath("//*[text()='Preview']"));
        Assert.assertTrue(previewPanel.isDisplayed(), "Preview panel is not displayed");

        WebElement rotatePlaceholder = driver.findElement(By.xpath("//*[contains(text(),'Select an image to rotate')]"));
        Assert.assertTrue(rotatePlaceholder.isDisplayed(), "Rotate placeholder text is not displayed");

        WebElement previewPlaceholder = driver.findElement(By.xpath("//*[contains(text(),'No image yet')]"));
        Assert.assertTrue(previewPlaceholder.isDisplayed(), "Preview placeholder is not displayed");
    }

    @Test(priority = 4)
    public void verifyFlipImagePageUI() {
        driver.get("https://www.pixelssuite.com/flip-image");

        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Flip Image')]")));
        Assert.assertTrue(heading.isDisplayed(), "Flip Image heading is not displayed");
        Assert.assertEquals(heading.getText().trim(), "Flip Image", "Incorrect page heading");

        WebElement uploadText = driver.findElement(By.xpath("//*[contains(text(),'Drag and drop your file here')]"));
        Assert.assertTrue(uploadText.isDisplayed(), "Upload text is not displayed");

        WebElement selectFilesButton = driver.findElement(By.xpath("//*[contains(text(),'Select files')]"));
        Assert.assertTrue(selectFilesButton.isDisplayed(), "Select files button is not displayed");

        WebElement flipPanel = driver.findElement(By.xpath("//*[text()='Flip']"));
        Assert.assertTrue(flipPanel.isDisplayed(), "Flip panel is not displayed");

        WebElement previewPanel = driver.findElement(By.xpath("//*[text()='Preview']"));
        Assert.assertTrue(previewPanel.isDisplayed(), "Preview panel is not displayed");

        WebElement flipPlaceholder = driver.findElement(By.xpath("//*[contains(text(),'Select an image to flip')]"));
        Assert.assertTrue(flipPlaceholder.isDisplayed(), "Flip placeholder text is not displayed");

        WebElement previewPlaceholder = driver.findElement(By.xpath("//*[contains(text(),'No image yet')]"));
        Assert.assertTrue(previewPlaceholder.isDisplayed(), "Preview placeholder is not displayed");
    }

    @Test(priority = 5)
    public void verifyImageToTextPageUI() {
        driver.get("https://www.pixelssuite.com/image-to-text");

        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Image') and contains(text(),'Text')]")));
        Assert.assertTrue(heading.isDisplayed(), "Image to Text heading is not displayed");

        WebElement uploadText = driver.findElement(By.xpath("//*[contains(text(),'Drag and drop your file here')]"));
        Assert.assertTrue(uploadText.isDisplayed(), "Upload text is not displayed");

        WebElement selectImageButton = driver.findElement(By.xpath("//*[contains(text(),'Select image')]"));
        Assert.assertTrue(selectImageButton.isDisplayed(), "Select image button is not displayed");

        WebElement optionsPanel = driver.findElement(By.xpath("//*[text()='Options']"));
        Assert.assertTrue(optionsPanel.isDisplayed(), "Options panel is not displayed");

        WebElement previewPanel = driver.findElement(By.xpath("//*[text()='Preview']"));
        Assert.assertTrue(previewPanel.isDisplayed(), "Preview panel is not displayed");

        WebElement resultPanel = driver.findElement(By.xpath("//*[text()='Result']"));
        Assert.assertTrue(resultPanel.isDisplayed(), "Result panel is not displayed");

        WebElement optionsPlaceholder = driver.findElement(By.xpath("//*[contains(text(),'Upload an image to extract text')]"));
        Assert.assertTrue(optionsPlaceholder.isDisplayed(), "Options placeholder is not displayed");

        WebElement previewPlaceholder = driver.findElement(By.xpath("//*[contains(text(),'No image yet')]"));
        Assert.assertTrue(previewPlaceholder.isDisplayed(), "Preview placeholder is not displayed");

        WebElement resultPlaceholder = driver.findElement(By.xpath("//*[contains(text(),'Recognized text will appear here')]"));
        Assert.assertTrue(resultPlaceholder.isDisplayed(), "Result placeholder is not displayed");

        WebElement copyButton = driver.findElement(By.xpath("//*[text()='Copy']"));
        Assert.assertTrue(copyButton.isDisplayed(), "Copy button is not displayed");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}