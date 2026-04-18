import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
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

public class ResizeToolsUITest {

    WebDriver driver;
    WebDriverWait wait;

    private final String RESIZE_IMAGE_URL = "https://www.pixelssuite.com/resize-image";
    private final String IMAGE_ENLARGER_URL = "https://www.pixelssuite.com/image-enlarger";
    private final String BULK_RESIZE_URL = "https://www.pixelssuite.com/bulk-resize";

    // Put these files inside: src/test/resources/files/
    private final String JPG_FILE = Paths.get("src", "test", "resources", "files", "sample.jpg").toAbsolutePath().toString();
    private final String PNG_FILE = Paths.get("src", "test", "resources", "files", "sample.png").toAbsolutePath().toString();
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

    private boolean isElementPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
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

    private boolean pageContainsAny(String... texts) {
        for (String text : texts) {
            if (isTextPresent(text)) {
                return true;
            }
        }
        return false;
    }

    // =========================
    // RESIZE IMAGE TESTS
    // =========================

    @Test(priority = 1)
    public void TC01_verifyResizeImagePageUI() {
        openPage(RESIZE_IMAGE_URL);

        assertTextPresent("Resize Image", "Resize Image heading is not displayed");
        assertAnyTextPresent("Upload text is not displayed", "Drag and drop your file here", "Drag and drop");
        assertAnyTextPresent("Select files button is not displayed", "Select files", "Select Files");
        assertTextPresent("Supported:", "Supported formats text is not displayed");
        assertTextPresent("Resize", "Resize panel title is not displayed");
        assertTextPresent("Preview", "Preview panel title is not displayed");
        assertTextPresent("Select an image to configure size", "Resize placeholder text is not displayed");
        assertTextPresent("No image yet", "Preview placeholder text is not displayed");
    }

    @Test(priority = 2)
    public void TC02_verifyResizeImageValidUpload() {
        openPage(RESIZE_IMAGE_URL);

        uploadFile(JPG_FILE);

        Assert.assertTrue(
            pageContainsAny("Preview", "Resize", "sample", "Width", "Height"),
            "Valid image upload did not appear to work on Resize Image page"
        );
    }

    @Test(priority = 3)
    public void TC03_verifyResizeImageInvalidUpload() {
        openPage(RESIZE_IMAGE_URL);

        uploadFile(INVALID_FILE);

        boolean invalidHandled =
            pageContainsAny("Invalid", "Unsupported", "Error", "Only image") ||
            !driver.getPageSource().contains("invalid.txt");

        Assert.assertTrue(invalidHandled, "Invalid file was not rejected on Resize Image page");
    }

    @Test(priority = 4)
    public void TC04_verifyResizeImageFileInputPresent() {
        openPage(RESIZE_IMAGE_URL);

        Assert.assertTrue(isElementPresent(By.cssSelector("input[type='file']")),
                "File input is not present on Resize Image page");
    }

    // =========================
    // IMAGE ENLARGER TESTS
    // =========================

    @Test(priority = 5)
    public void TC05_verifyImageEnlargerPageUI() {
        openPage(IMAGE_ENLARGER_URL);

        assertTextPresent("Image Enlarger", "Image Enlarger heading is not displayed");
        assertAnyTextPresent("Upload text is not displayed", "Drag and drop your file here", "Drag and drop");
        assertAnyTextPresent("Select files button is not displayed", "Select files", "Select Files", "Select Image");
        assertTextPresent("Supported:", "Supported formats text is not displayed");
        assertTextPresent("Enlarge", "Enlarge panel title is not displayed");
        assertTextPresent("Preview", "Preview panel title is not displayed");
        assertTextPresent("Select an image to enlarge", "Enlarge placeholder text is not displayed");
        assertTextPresent("No image yet", "Preview placeholder text is not displayed");
    }

    @Test(priority = 6)
    public void TC06_verifyImageEnlargerValidUpload() {
        openPage(IMAGE_ENLARGER_URL);

        uploadFile(PNG_FILE);

        Assert.assertTrue(
            pageContainsAny("Preview", "Enlarge", "sample", "Width", "Height"),
            "Valid image upload did not appear to work on Image Enlarger page"
        );
    }

    @Test(priority = 7)
    public void TC07_verifyImageEnlargerInvalidUpload() {
        openPage(IMAGE_ENLARGER_URL);

        uploadFile(INVALID_FILE);

        boolean invalidHandled =
            pageContainsAny("Invalid", "Unsupported", "Error", "Only image") ||
            !driver.getPageSource().contains("invalid.txt");

        Assert.assertTrue(invalidHandled, "Invalid file was not rejected on Image Enlarger page");
    }

    @Test(priority = 8)
    public void TC08_verifyImageEnlargerActionControlsPresent() {
        openPage(IMAGE_ENLARGER_URL);

        Assert.assertTrue(isElementPresent(By.cssSelector("input[type='file']")),
                "File input is not present on Image Enlarger page");
        Assert.assertTrue(isTextPresent("Enlarge"), "Enlarge section is not visible");
    }

    // =========================
    // BULK RESIZE TESTS
    // =========================

    @Test(priority = 9)
    public void TC09_verifyBulkResizePageUI() {
        openPage(BULK_RESIZE_URL);

        assertTextPresent("Bulk Resize", "Bulk Resize heading is not displayed");
        assertTextPresent("Files", "Files section title is not displayed");
        assertAnyTextPresent("Upload text is not displayed", "Drag and drop your file here", "Drag and drop");
        assertAnyTextPresent("Select images button is not displayed", "Select images", "Select Images");
        assertTextPresent("Supported:", "Supported formats text is not displayed");
        assertTextPresent("Options", "Options section title is not displayed");
        assertTextPresent("Process & Download", "Process & Download button is not displayed");
        assertTextPresent("Select multiple images to resize", "Bulk resize helper text is not displayed");
    }

    @Test(priority = 10)
    public void TC10_verifyBulkResizeWidthAndHeightInputs() {
        openPage(BULK_RESIZE_URL);

        WebElement widthInput = waitForVisible(By.xpath("//input[@placeholder='Width']"));
        WebElement heightInput = waitForVisible(By.xpath("//input[@placeholder='Height']"));

        widthInput.clear();
        widthInput.sendKeys("800");

        heightInput.clear();
        heightInput.sendKeys("600");

        Assert.assertEquals(widthInput.getAttribute("value"), "800", "Width input did not accept value");
        Assert.assertEquals(heightInput.getAttribute("value"), "600", "Height input did not accept value");
    }

    @Test(priority = 11)
    public void TC11_verifyBulkResizeAspectRatioCheckboxPresent() {
        openPage(BULK_RESIZE_URL);

        WebElement checkbox = waitForVisible(By.xpath("//input[@type='checkbox']"));
        Assert.assertTrue(checkbox.isDisplayed(), "Keep aspect ratio checkbox is not displayed");
    }

    @Test(priority = 12)
    public void TC12_verifyBulkResizeProcessButtonVisible() {
        openPage(BULK_RESIZE_URL);

        WebElement processButton = findButtonByPossibleTexts("Process & Download", "Process", "Download");
        Assert.assertTrue(processButton.isDisplayed(), "Process button is not displayed");
        Assert.assertTrue(processButton.isEnabled(), "Process button is not enabled");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}