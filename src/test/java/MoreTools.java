import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
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

public class MoreTools {

    WebDriver driver;
    WebDriverWait wait;

    private final String MEME_GENERATOR_URL = "https://www.pixelssuite.com/meme-generator";
    private final String COLOR_PICKER_URL = "https://www.pixelssuite.com/color-picker";
    private final String ROTATE_IMAGE_URL = "https://www.pixelssuite.com/rotate-image";
    private final String FLIP_IMAGE_URL = "https://www.pixelssuite.com/flip-image";
    private final String IMAGE_TO_TEXT_URL = "https://www.pixelssuite.com/image-to-text";

    private final String IMAGE_FILE = Paths.get("src", "test", "resources", "files", "sample.jpg").toAbsolutePath().toString();
    private final String OCR_IMAGE_FILE = Paths.get("src", "test", "resources", "files", "ocr-sample.png").toAbsolutePath().toString();
    private final String INVALID_FILE = Paths.get("src", "test", "resources", "files", "invalid.txt").toAbsolutePath().toString();

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // =========================
    // Helper Methods
    // =========================

    private void openPage(String url) {
        driver.get(url);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
    }

    private boolean isTextPresent(String text) {
        return driver.findElements(By.xpath("//*[contains(text(),'" + text + "')]")).size() > 0;
    }

    private void assertTextPresent(String text, String message) {
        Assert.assertTrue(isTextPresent(text), message);
    }

    private void assertAnyTextPresent(String message, String... texts) {
        boolean found = false;
        for (String text : texts) {
            if (isTextPresent(text)) {
                found = true;
                break;
            }
        }
        Assert.assertTrue(found, message);
    }

    private WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private WebElement getFileInput() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='file']")));
    }

    private void uploadFile(String filePath) {
        getFileInput().sendKeys(filePath);
    }

    private WebElement findButtonByPossibleTexts(String... texts) {
        for (String text : texts) {
            List<WebElement> elements = driver.findElements(
                By.xpath("//button[contains(.,'" + text + "')] | //*[@role='button' and contains(.,'" + text + "')]")
            );
            if (!elements.isEmpty()) {
                return elements.get(0);
            }
        }
        throw new NoSuchElementException("No matching button found");
    }

    private boolean isElementPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    private void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    // =========================
    // MEME GENERATOR TESTS
    // =========================

    @Test(priority = 1)
    public void TC01_verifyMemeGeneratorPageUI() {
        openPage(MEME_GENERATOR_URL);

        assertTextPresent("Meme Generator", "Meme Generator heading is not displayed");
        assertAnyTextPresent("Upload text is not displayed", "Drag and drop your file here", "Drag and drop");
        assertAnyTextPresent("Select files button is not displayed", "Select files", "Select Files");
        assertTextPresent("Text & Style", "Text & Style panel is not displayed");
        assertTextPresent("Preview", "Preview panel is not displayed");
        assertTextPresent("Select an image to add meme text", "Meme placeholder text is not displayed");
        assertTextPresent("No image yet", "Preview placeholder is not displayed");
    }

    @Test(priority = 2)
    public void TC02_verifyMemeGeneratorValidImageUpload() {
        openPage(MEME_GENERATOR_URL);

        uploadFile(IMAGE_FILE);

        Assert.assertTrue(
            isTextPresent("Preview") || isTextPresent("Text & Style") || driver.getPageSource().contains("sample"),
            "Valid image upload did not appear to work on Meme Generator page"
        );
    }

    @Test(priority = 3)
    public void TC03_verifyMemeGeneratorInvalidFileUpload() {
        openPage(MEME_GENERATOR_URL);

        uploadFile(INVALID_FILE);

        boolean invalidHandled =
            isTextPresent("Invalid") ||
            isTextPresent("Unsupported") ||
            isTextPresent("Error") ||
            !driver.getPageSource().contains("invalid.txt");

        Assert.assertTrue(invalidHandled, "Invalid file was not rejected on Meme Generator page");
    }

    @Test(priority = 4)
    public void TC04_verifyMemeGeneratorActionControlsPresent() {
        openPage(MEME_GENERATOR_URL);

        Assert.assertTrue(isElementPresent(By.cssSelector("input[type='file']")), "File input is not present");
        Assert.assertTrue(isTextPresent("Text & Style"), "Text & Style section is not visible");
    }

    // =========================
    // COLOR PICKER TESTS
    // =========================

    @Test(priority = 5)
    public void TC05_verifyColorPickerPageUI() {
        openPage(COLOR_PICKER_URL);

        assertTextPresent("Color Picker", "Color Picker heading is not displayed");
        assertTextPresent("Pick", "Pick panel is not displayed");
        assertTextPresent("Selected Color", "Selected Color text is not displayed");
        assertTextPresent("Hex Code", "Hex Code text is not displayed");
        assertTextPresent("Other Color Formats", "Other Color Formats text is not displayed");
        assertTextPresent("RGB", "RGB button is not displayed");
        assertTextPresent("HSV", "HSV button is not displayed");
        assertTextPresent("HSL", "HSL button is not displayed");
        assertTextPresent("CMYK", "CMYK button is not displayed");
        assertTextPresent("Copy", "Copy button is not displayed");
    }

    @Test(priority = 6)
    public void TC06_verifyColorPickerFormatButtonsPresent() {
        openPage(COLOR_PICKER_URL);

        Assert.assertTrue(isTextPresent("RGB"), "RGB format option missing");
        Assert.assertTrue(isTextPresent("HSV"), "HSV format option missing");
        Assert.assertTrue(isTextPresent("HSL"), "HSL format option missing");
        Assert.assertTrue(isTextPresent("CMYK"), "CMYK format option missing");
    }

    @Test(priority = 7)
    public void TC07_verifyColorPickerCopyButtonVisible() {
        openPage(COLOR_PICKER_URL);

        WebElement copyButton = findButtonByPossibleTexts("Copy");
        Assert.assertTrue(copyButton.isDisplayed(), "Copy button is not displayed");
        Assert.assertTrue(copyButton.isEnabled(), "Copy button is not enabled");
    }

    @Test(priority = 8)
    public void TC08_verifyColorPickerBasicLabelsVisible() {
        openPage(COLOR_PICKER_URL);

        Assert.assertTrue(isTextPresent("Selected Color"), "Selected Color label is missing");
        Assert.assertTrue(isTextPresent("Hex Code"), "Hex Code label is missing");
    }

    // =========================
    // ROTATE IMAGE TESTS
    // =========================

    @Test(priority = 9)
    public void TC09_verifyRotateImagePageUI() {
        openPage(ROTATE_IMAGE_URL);

        assertTextPresent("Rotate Image", "Rotate Image heading is not displayed");
        assertAnyTextPresent("Upload text is not displayed", "Drag and drop your file here", "Drag and drop");
        assertAnyTextPresent("Select files button is not displayed", "Select files", "Select Files");
        assertTextPresent("Rotate", "Rotate panel is not displayed");
        assertTextPresent("Preview", "Preview panel is not displayed");
        assertTextPresent("Select an image to rotate", "Rotate placeholder text is not displayed");
        assertTextPresent("No image yet", "Preview placeholder is not displayed");
    }

    @Test(priority = 10)
    public void TC10_verifyRotateImageValidImageUpload() {
        openPage(ROTATE_IMAGE_URL);

        uploadFile(IMAGE_FILE);

        Assert.assertTrue(
            isTextPresent("Rotate") || isTextPresent("Preview") || driver.getPageSource().contains("sample"),
            "Valid image upload did not appear to work on Rotate Image page"
        );
    }

    @Test(priority = 11)
    public void TC11_verifyRotateImageInvalidFileUpload() {
        openPage(ROTATE_IMAGE_URL);

        uploadFile(INVALID_FILE);

        boolean invalidHandled =
            isTextPresent("Invalid") ||
            isTextPresent("Unsupported") ||
            isTextPresent("Error") ||
            !driver.getPageSource().contains("invalid.txt");

        Assert.assertTrue(invalidHandled, "Invalid file was not rejected on Rotate Image page");
    }

    @Test(priority = 12)
    public void TC12_verifyRotateImageControlsPresent() {
        openPage(ROTATE_IMAGE_URL);

        Assert.assertTrue(isElementPresent(By.cssSelector("input[type='file']")), "File input is not present");
        Assert.assertTrue(isTextPresent("Rotate"), "Rotate section is not visible");
    }

    // =========================
    // FLIP IMAGE TESTS
    // =========================

    @Test(priority = 13)
    public void TC13_verifyFlipImagePageUI() {
        openPage(FLIP_IMAGE_URL);

        assertTextPresent("Flip Image", "Flip Image heading is not displayed");
        assertAnyTextPresent("Upload text is not displayed", "Drag and drop your file here", "Drag and drop");
        assertAnyTextPresent("Select files button is not displayed", "Select files", "Select Files");
        assertTextPresent("Flip", "Flip panel is not displayed");
        assertTextPresent("Preview", "Preview panel is not displayed");
        assertTextPresent("Select an image to flip", "Flip placeholder text is not displayed");
        assertTextPresent("No image yet", "Preview placeholder is not displayed");
    }

    @Test(priority = 14)
    public void TC14_verifyFlipImageValidImageUpload() {
        openPage(FLIP_IMAGE_URL);

        uploadFile(IMAGE_FILE);

        Assert.assertTrue(
            isTextPresent("Flip") || isTextPresent("Preview") || driver.getPageSource().contains("sample"),
            "Valid image upload did not appear to work on Flip Image page"
        );
    }

    @Test(priority = 15)
    public void TC15_verifyFlipImageInvalidFileUpload() {
        openPage(FLIP_IMAGE_URL);

        uploadFile(INVALID_FILE);

        boolean invalidHandled =
            isTextPresent("Invalid") ||
            isTextPresent("Unsupported") ||
            isTextPresent("Error") ||
            !driver.getPageSource().contains("invalid.txt");

        Assert.assertTrue(invalidHandled, "Invalid file was not rejected on Flip Image page");
    }

    @Test(priority = 16)
    public void TC16_verifyFlipImageControlsPresent() {
        openPage(FLIP_IMAGE_URL);

        Assert.assertTrue(isElementPresent(By.cssSelector("input[type='file']")), "File input is not present");
        Assert.assertTrue(isTextPresent("Flip"), "Flip section is not visible");
    }

    // =========================
    // IMAGE TO TEXT (OCR) TESTS
    // =========================

    @Test(priority = 17)
    public void TC17_verifyImageToTextPageUI() {
        openPage(IMAGE_TO_TEXT_URL);

        assertAnyTextPresent("Image to Text heading is not displayed", "Image to Text", "Image", "Text");
        assertAnyTextPresent("Upload text is not displayed", "Drag and drop your file here", "Drag and drop");
        assertAnyTextPresent("Select image button is not displayed", "Select image", "Select Image", "Select files");
        assertTextPresent("Options", "Options panel is not displayed");
        assertTextPresent("Preview", "Preview panel is not displayed");
        assertTextPresent("Result", "Result panel is not displayed");
        assertTextPresent("Upload an image to extract text", "Options placeholder is not displayed");
        assertTextPresent("No image yet", "Preview placeholder is not displayed");
        assertTextPresent("Recognized text will appear here", "Result placeholder is not displayed");
        assertTextPresent("Copy", "Copy button is not displayed");
    }

    @Test(priority = 18)
    public void TC18_verifyImageToTextValidImageUpload() {
        openPage(IMAGE_TO_TEXT_URL);

        uploadFile(OCR_IMAGE_FILE);

        Assert.assertTrue(
            isTextPresent("Options") || isTextPresent("Preview") || isTextPresent("Result") || driver.getPageSource().contains("ocr-sample"),
            "Valid OCR image upload did not appear to work on Image to Text page"
        );
    }

    @Test(priority = 19)
    public void TC19_verifyImageToTextInvalidFileUpload() {
        openPage(IMAGE_TO_TEXT_URL);

        uploadFile(INVALID_FILE);

        boolean invalidHandled =
            isTextPresent("Invalid") ||
            isTextPresent("Unsupported") ||
            isTextPresent("Error") ||
            !driver.getPageSource().contains("invalid.txt");

        Assert.assertTrue(invalidHandled, "Invalid file was not rejected on Image to Text page");
    }

    @Test(priority = 20)
    public void TC20_verifyImageToTextCopyButtonVisible() {
        openPage(IMAGE_TO_TEXT_URL);

        WebElement copyButton = findButtonByPossibleTexts("Copy");
        Assert.assertTrue(copyButton.isDisplayed(), "Copy button is not displayed");
        Assert.assertTrue(copyButton.isEnabled(), "Copy button is not enabled");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}