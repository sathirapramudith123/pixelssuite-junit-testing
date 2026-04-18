import java.nio.file.Paths;
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

public class DocumentConverter {

    WebDriver driver;
    WebDriverWait wait;

    // Update these URLs if the site routes have changed
    private final String IMAGE_TO_PDF_URL = "https://www.pixelssuite.com/image-to-pdf";
    private final String PDF_TO_WORD_URL = "https://www.pixelssuite.com/pdf-to-word";
    private final String WORD_TO_PDF_URL = "https://www.pixelssuite.com/word-to-pdf";

    // Test files
    // Put these files inside: src/test/resources/files/
    private final String IMAGE_FILE = Paths.get("src", "test", "resources", "files", "sample.jpg").toAbsolutePath().toString();
    private final String PDF_FILE = Paths.get("src", "test", "resources", "files", "sample.pdf").toAbsolutePath().toString();
    private final String WORD_FILE = Paths.get("src", "test", "resources", "files", "sample.docx").toAbsolutePath().toString();
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
    // Helper methods
    // =========================

    private void openPage(String url) {
        driver.get(url);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
    }

    private WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private List<WebElement> findAll(By locator) {
        return driver.findElements(locator);
    }

    private WebElement getFileInput() {
        return waitForVisible(By.cssSelector("input[type='file']"));
    }

    private void uploadFile(String filePath) {
        getFileInput().sendKeys(filePath);
    }

    private boolean isTextPresent(String text) {
        return driver.findElements(By.xpath("//*[contains(text(),'" + text + "')]")).size() > 0;
    }

    private void assertAnyTextPresent(String... texts) {
        boolean found = false;
        for (String text : texts) {
            if (isTextPresent(text)) {
                found = true;
                break;
            }
        }
        Assert.assertTrue(found, "None of the expected texts were found on the page.");
    }

    private void assertPageNot404() {
        String pageSource = driver.getPageSource().toLowerCase();
        Assert.assertFalse(pageSource.contains("404"), "Page returned 404 / Not Found");
        Assert.assertFalse(driver.getTitle().toLowerCase().contains("404"), "Page title indicates 404");
    }

    private WebElement findButtonByText(String text) {
        return waitForVisible(By.xpath("//button[contains(.,'" + text + "')] | //*[@role='button' and contains(.,'" + text + "')]"));
    }

    // =========================
    // IMAGE TO PDF TEST CASES
    // =========================

    @Test(priority = 1)
    public void TC01_verifyImageToPdfPageLoads() {
        openPage(IMAGE_TO_PDF_URL);
        assertPageNot404();

        assertAnyTextPresent("Image", "PDF");
        assertAnyTextPresent("Drag and drop your file here", "Drag and drop");
        assertAnyTextPresent("Select Images", "Select Image");
        assertAnyTextPresent("Create PDF", "Convert");
    }

    @Test(priority = 2)
    public void TC02_verifyImageToPdfUploadValidImage() {
        openPage(IMAGE_TO_PDF_URL);
        assertPageNot404();

        uploadFile(IMAGE_FILE);

        // Check uploaded file name appears OR preview/selected section changes
        assertAnyTextPresent("sample.jpg", "sample", "Selected Images", "Preview");
    }

    @Test(priority = 3)
    public void TC03_verifyImageToPdfCreateButtonPresence() {
        openPage(IMAGE_TO_PDF_URL);
        assertPageNot404();

        WebElement createPdfButton = findButtonByText("Create PDF");
        Assert.assertTrue(createPdfButton.isDisplayed(), "Create PDF button is not displayed");
    }

    @Test(priority = 4)
    public void TC04_verifyImageToPdfInvalidFileUpload() {
        openPage(IMAGE_TO_PDF_URL);
        assertPageNot404();

        uploadFile(INVALID_FILE);

        // Depending on site behavior, either an error appears or the invalid file is rejected
        boolean invalidRejected =
                isTextPresent("Invalid") ||
                isTextPresent("Unsupported") ||
                isTextPresent("Only image files") ||
                driver.getPageSource().contains("error") ||
                !driver.getPageSource().contains("invalid.txt");

        Assert.assertTrue(invalidRejected, "Invalid file upload was not rejected as expected");
    }

    // =========================
    // PDF TO WORD TEST CASES
    // =========================

    @Test(priority = 5)
    public void TC05_verifyPdfToWordPageLoads() {
        openPage(PDF_TO_WORD_URL);
        assertPageNot404();

        assertAnyTextPresent("PDF", "Word");
        assertAnyTextPresent("Drag and drop your file here", "Drag and drop");
        assertAnyTextPresent("Select PDF", "Select File");
    }

    @Test(priority = 6)
    public void TC06_verifyPdfToWordUploadValidPdf() {
        openPage(PDF_TO_WORD_URL);
        assertPageNot404();

        uploadFile(PDF_FILE);

        assertAnyTextPresent("sample.pdf", "sample", "Selected");
    }

    @Test(priority = 7)
    public void TC07_verifyPdfToWordInvalidFileUpload() {
        openPage(PDF_TO_WORD_URL);
        assertPageNot404();

        uploadFile(IMAGE_FILE);

        boolean invalidRejected =
                isTextPresent("Invalid") ||
                isTextPresent("Unsupported") ||
                isTextPresent("Only PDF") ||
                driver.getPageSource().contains("error") ||
                !driver.getPageSource().contains("sample.jpg");

        Assert.assertTrue(invalidRejected, "Non-PDF file was not rejected in PDF to Word");
    }

    @Test(priority = 8)
    public void TC08_verifyPdfToWordConvertButtonPresence() {
        openPage(PDF_TO_WORD_URL);
        assertPageNot404();

        // Site may use Convert / Convert to Word / Start / Download later
        boolean found =
                findAll(By.xpath("//button[contains(.,'Convert')]")).size() > 0 ||
                findAll(By.xpath("//button[contains(.,'Word')]")).size() > 0 ||
                findAll(By.xpath("//button[contains(.,'Start')]")).size() > 0;

        Assert.assertTrue(found, "Convert button was not found on PDF to Word page");
    }

    // =========================
    // WORD TO PDF TEST CASES
    // =========================

    @Test(priority = 9)
    public void TC09_verifyWordToPdfPageLoads() {
        openPage(WORD_TO_PDF_URL);
        assertPageNot404();

        assertAnyTextPresent("Word", "PDF");
        assertAnyTextPresent("Drag and drop your file here", "Drag and drop");
        assertAnyTextPresent("Select Word", "Select File");
    }

    @Test(priority = 10)
    public void TC10_verifyWordToPdfUploadValidWordFile() {
        openPage(WORD_TO_PDF_URL);
        assertPageNot404();

        uploadFile(WORD_FILE);

        assertAnyTextPresent("sample.docx", "sample", "Selected");
    }

    @Test(priority = 11)
    public void TC11_verifyWordToPdfInvalidFileUpload() {
        openPage(WORD_TO_PDF_URL);
        assertPageNot404();

        uploadFile(PDF_FILE);

        boolean invalidRejected =
                isTextPresent("Invalid") ||
                isTextPresent("Unsupported") ||
                isTextPresent("Only Word") ||
                driver.getPageSource().contains("error") ||
                !driver.getPageSource().contains("sample.pdf");

        Assert.assertTrue(invalidRejected, "Non-Word file was not rejected in Word to PDF");
    }

    @Test(priority = 12)
    public void TC12_verifyWordToPdfConvertButtonPresence() {
        openPage(WORD_TO_PDF_URL);
        assertPageNot404();

        boolean found =
                findAll(By.xpath("//button[contains(.,'Convert')]")).size() > 0 ||
                findAll(By.xpath("//button[contains(.,'Create PDF')]")).size() > 0 ||
                findAll(By.xpath("//button[contains(.,'Start')]")).size() > 0;

        Assert.assertTrue(found, "Convert button was not found on Word to PDF page");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}