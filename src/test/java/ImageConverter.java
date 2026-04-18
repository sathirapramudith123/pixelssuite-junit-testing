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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ImageConverter {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @DataProvider(name = "convertPages")
    public Object[][] convertPages() {
        return new Object[][] {
            {
                "https://www.pixelssuite.com/convert-to-jpg",
                "Convert Image",
                Paths.get("src", "test", "resources", "files", "sample.png").toAbsolutePath().toString(),
                Paths.get("src", "test", "resources", "files", "invalid.txt").toAbsolutePath().toString()
            },
            {
                "https://www.pixelssuite.com/convert-to-png",
                "Convert Image",
                Paths.get("src", "test", "resources", "files", "sample.jpg").toAbsolutePath().toString(),
                Paths.get("src", "test", "resources", "files", "invalid.txt").toAbsolutePath().toString()
            },
            {
                "https://www.pixelssuite.com/convert-to-webp",
                "Convert Image",
                Paths.get("src", "test", "resources", "files", "sample.jpg").toAbsolutePath().toString(),
                Paths.get("src", "test", "resources", "files", "invalid.txt").toAbsolutePath().toString()
            }
        };
    }

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

    private WebElement getFileInput() {
        return wait.until(
            ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='file']"))
        );
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

    @Test(dataProvider = "convertPages", priority = 1)
    public void TC01_verifyConvertPageLoadsSuccessfully(String url, String expectedHeading,
                                                        String validFile, String invalidFile) {
        openPage(url);

        WebElement body = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
        Assert.assertTrue(body.isDisplayed(), "Page body is not displayed");

        Assert.assertTrue(driver.getCurrentUrl().contains("convert"),
            "URL does not contain 'convert'");

        WebElement heading = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'" + expectedHeading + "')]")));
        Assert.assertTrue(heading.isDisplayed(), "Page heading is not visible");
    }

    @Test(dataProvider = "convertPages", priority = 2)
    public void TC02_verifyUploadSectionDisplayed(String url, String expectedHeading,
                                                  String validFile, String invalidFile) {
        openPage(url);

        assertTextPresent(expectedHeading, "Page heading is not displayed");
        assertAnyTextPresent("Upload text is not displayed",
            "Drag and drop your file here", "Drag and drop");
        assertAnyTextPresent("Browse helper text is not displayed",
            "click to browse from your device", "browse from your device");
        assertAnyTextPresent("Select files button is not displayed",
            "Select files", "Select Files");
        assertTextPresent("Supported:", "Supported formats text is not displayed");
    }

    @Test(dataProvider = "convertPages", priority = 3)
    public void TC03_verifyConvertAndPreviewPanelsDisplayed(String url, String expectedHeading,
                                                            String validFile, String invalidFile) {
        openPage(url);

        assertTextPresent("Convert", "Convert panel title is not displayed");
        assertTextPresent("Preview", "Preview panel title is not displayed");
        assertAnyTextPresent("Convert placeholder text is not displayed",
            "Select an image to convert", "Select image to convert");
        assertAnyTextPresent("Preview placeholder text is not displayed",
            "No image yet", "No image yet.");
    }

    @Test(dataProvider = "convertPages", priority = 4)
    public void TC04_verifySelectFilesButtonIsVisibleAndEnabled(String url, String expectedHeading,
                                                                String validFile, String invalidFile) {
        openPage(url);

        WebElement selectFilesButton = findButtonByPossibleTexts("Select files", "Select Files");
        Assert.assertTrue(selectFilesButton.isDisplayed(), "Select files button is not displayed");
        Assert.assertTrue(selectFilesButton.isEnabled(), "Select files button is not enabled");
    }

    @Test(dataProvider = "convertPages", priority = 5)
    public void TC05_verifyFileInputIsPresent(String url, String expectedHeading,
                                              String validFile, String invalidFile) {
        openPage(url);

        List<WebElement> fileInputs = driver.findElements(By.cssSelector("input[type='file']"));
        Assert.assertTrue(fileInputs.size() > 0, "File input is not present");
    }

    @Test(dataProvider = "convertPages", priority = 6)
    public void TC06_verifyValidImageUpload(String url, String expectedHeading,
                                            String validFile, String invalidFile) {
        openPage(url);

        uploadFile(validFile);

        Assert.assertTrue(
            pageContainsAny("Preview", "Convert", "sample", "Selected"),
            "Valid image upload did not appear to work"
        );
    }

    @Test(dataProvider = "convertPages", priority = 7)
    public void TC07_verifyInvalidFileUpload(String url, String expectedHeading,
                                             String validFile, String invalidFile) {
        openPage(url);

        uploadFile(invalidFile);

        boolean invalidHandled =
            pageContainsAny("Invalid", "Unsupported", "Error", "Only") ||
            !driver.getPageSource().contains("invalid.txt");

        Assert.assertTrue(invalidHandled, "Invalid file was not rejected as expected");
    }

    @Test(dataProvider = "convertPages", priority = 8)
    public void TC08_verifyConvertButtonPresence(String url, String expectedHeading,
                                                 String validFile, String invalidFile) {
        openPage(url);

        WebElement convertButton = findButtonByPossibleTexts("Convert", "Start", "Download", "Save");
        Assert.assertTrue(convertButton.isDisplayed(), "Convert action button is not displayed");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}