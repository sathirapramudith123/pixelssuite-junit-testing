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

public class Compress {

    WebDriver driver;
    WebDriverWait wait;

    // Update these URLs if the website routes have changed
    private final String COMPRESS_IMAGE_URL = "https://www.pixelssuite.com/compress-image";
    private final String PNG_COMPRESSOR_URL = "https://www.pixelssuite.com/png-compressor";
    private final String GIF_COMPRESSOR_URL = "https://www.pixelssuite.com/gif-compressor";

    // Test files
    // Put these files inside src/test/resources/files/
    private final String JPG_FILE = Paths.get("src", "test", "resources", "files", "sample.jpg").toAbsolutePath().toString();
    private final String PNG_FILE = Paths.get("src", "test", "resources", "files", "sample.png").toAbsolutePath().toString();
    private final String GIF_FILE = Paths.get("src", "test", "resources", "files", "sample.gif").toAbsolutePath().toString();
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

    private WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
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

    private void assertPageLoadedWithout404() {
        String title = driver.getTitle().toLowerCase();
        String source = driver.getPageSource().toLowerCase();

        Assert.assertFalse(title.contains("404"), "Page title indicates 404");
        Assert.assertFalse(source.contains("404"), "Page source indicates 404");
        Assert.assertFalse(source.contains("not found"), "Page shows Not Found");
    }

    private WebElement getFileInput() {
        return waitForVisible(By.cssSelector("input[type='file']"));
    }

    private void uploadFile(String filePath) {
        getFileInput().sendKeys(filePath);
    }

    private WebElement findButtonByPossibleTexts(String... texts) {
        for (String text : texts) {
            List<WebElement> buttons = driver.findElements(
                By.xpath("//button[contains(.,'" + text + "')] | //*[@role='button' and contains(.,'" + text + "')]")
            );
            if (!buttons.isEmpty()) {
                return buttons.get(0);
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
    // COMPRESS IMAGE TEST CASES
    // =========================

    @Test(priority = 1)
    public void TC01_verifyCompressImagePageUI() {
        openPage(COMPRESS_IMAGE_URL);
        assertPageLoadedWithout404();

        assertTextPresent("Compress Image", "Compress Image heading is not displayed");
        assertAnyTextPresent("Upload area text is not displayed", "Drag and drop your file here", "Drag and drop");
        assertAnyTextPresent("Select files button is not displayed", "Select files", "Select Files");
        assertTextPresent("Supported:", "Supported text is not displayed");
        assertTextPresent("Compress", "Compress section title is not displayed");
        assertTextPresent("Preview", "Preview section title is not displayed");
        assertTextPresent("Select an image to compress", "Compress placeholder text is not displayed");
        assertTextPresent("No image yet", "Preview placeholder is not displayed");
    }

    @Test(priority = 2)
    public void TC02_verifyCompressImageValidUpload() {
        openPage(COMPRESS_IMAGE_URL);
        assertPageLoadedWithout404();

        uploadFile(JPG_FILE);

        Assert.assertTrue(
            pageContainsAny("sample.jpg", "sample", "Preview", "Compress"),
            "Valid JPG image upload did not appear to work"
        );
    }

    @Test(priority = 3)
    public void TC03_verifyCompressImageInvalidUpload() {
        openPage(COMPRESS_IMAGE_URL);
        assertPageLoadedWithout404();

        uploadFile(INVALID_FILE);

        boolean invalidHandled =
                pageContainsAny("Invalid", "Unsupported", "Only image files", "Error") ||
                !driver.getPageSource().contains("invalid.txt");

        Assert.assertTrue(invalidHandled, "Invalid file was not rejected on Compress Image page");
    }

    @Test(priority = 4)
    public void TC04_verifyCompressImageCompressButton() {
        openPage(COMPRESS_IMAGE_URL);
        assertPageLoadedWithout404();

        WebElement compressButton = findButtonByPossibleTexts("Compress", "Start", "Download");
        Assert.assertTrue(compressButton.isDisplayed(), "Compress action button is not displayed");
    }

    // =========================
    // PNG COMPRESSOR TEST CASES
    // =========================

    @Test(priority = 5)
    public void TC05_verifyPngCompressorPageUI() {
        openPage(PNG_COMPRESSOR_URL);
        assertPageLoadedWithout404();

        assertTextPresent("PNG Compressor", "PNG Compressor heading is not displayed");
        assertAnyTextPresent("Upload area text is not displayed", "Drag and drop your file here", "Drag and drop");
        assertAnyTextPresent("Select files button is not displayed", "Select files", "Select Files");
        assertTextPresent("Supported:", "Supported text is not displayed");
        assertTextPresent("Compress", "Compress section title is not displayed");
        assertTextPresent("Preview", "Preview section title is not displayed");
        assertTextPresent("Select a PNG image to re-encode", "PNG placeholder text is not displayed");
        assertTextPresent("No image yet", "Preview placeholder is not displayed");
    }

    @Test(priority = 6)
    public void TC06_verifyPngCompressorValidUpload() {
        openPage(PNG_COMPRESSOR_URL);
        assertPageLoadedWithout404();

        uploadFile(PNG_FILE);

        Assert.assertTrue(
            pageContainsAny("sample.png", "sample", "Preview", "Compress"),
            "Valid PNG upload did not appear to work"
        );
    }

    @Test(priority = 7)
    public void TC07_verifyPngCompressorInvalidUpload() {
        openPage(PNG_COMPRESSOR_URL);
        assertPageLoadedWithout404();

        uploadFile(JPG_FILE);

        boolean invalidHandled =
                pageContainsAny("Invalid", "Unsupported", "Only PNG", "Error") ||
                !driver.getPageSource().contains("sample.jpg");

        Assert.assertTrue(invalidHandled, "Non-PNG file was not rejected on PNG Compressor page");
    }

    @Test(priority = 8)
    public void TC08_verifyPngCompressorCompressButton() {
        openPage(PNG_COMPRESSOR_URL);
        assertPageLoadedWithout404();

        WebElement compressButton = findButtonByPossibleTexts("Compress", "Start", "Download");
        Assert.assertTrue(compressButton.isDisplayed(), "Compress action button is not displayed on PNG Compressor page");
    }

    // =========================
    // GIF COMPRESSOR TEST CASES
    // =========================

    @Test(priority = 9)
    public void TC09_verifyGifCompressorPageUI() {
        openPage(GIF_COMPRESSOR_URL);
        assertPageLoadedWithout404();

        assertTextPresent("GIF Compressor", "GIF Compressor heading is not displayed");
        assertAnyTextPresent("Upload area text is not displayed", "Drag and drop your file here", "Drag and drop");
        assertAnyTextPresent("Select GIF button is not displayed", "Select GIF", "Select Gif", "Select files", "Select Files");
        assertTextPresent("Supported:", "Supported text is not displayed");
        assertTextPresent("Compress", "Compress section title is not displayed");
        assertTextPresent("Preview", "Preview section title is not displayed");
        assertTextPresent("Select a GIF to compress", "GIF placeholder text is not displayed");
        assertTextPresent("No GIF yet", "GIF preview placeholder is not displayed");
    }

    @Test(priority = 10)
    public void TC10_verifyGifCompressorValidUpload() {
        openPage(GIF_COMPRESSOR_URL);
        assertPageLoadedWithout404();

        uploadFile(GIF_FILE);

        Assert.assertTrue(
            pageContainsAny("sample.gif", "sample", "Preview", "Compress"),
            "Valid GIF upload did not appear to work"
        );
    }

    @Test(priority = 11)
    public void TC11_verifyGifCompressorInvalidUpload() {
        openPage(GIF_COMPRESSOR_URL);
        assertPageLoadedWithout404();

        uploadFile(PNG_FILE);

        boolean invalidHandled =
                pageContainsAny("Invalid", "Unsupported", "Only GIF", "Error") ||
                !driver.getPageSource().contains("sample.png");

        Assert.assertTrue(invalidHandled, "Non-GIF file was not rejected on GIF Compressor page");
    }

    @Test(priority = 12)
    public void TC12_verifyGifCompressorCompressButton() {
        openPage(GIF_COMPRESSOR_URL);
        assertPageLoadedWithout404();

        WebElement compressButton = findButtonByPossibleTexts("Compress", "Start", "Download");
        Assert.assertTrue(compressButton.isDisplayed(), "Compress action button is not displayed on GIF Compressor page");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}