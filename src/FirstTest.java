import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "emulator-5554");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "/Users/mariabelyaeva/Desktop/JavaAppiumAutomation/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void firstTest() {

        waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Wikipedia",
                5);

        waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5);

        waitForElementPresent(By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_container']//*[@text = 'Object-oriented programming language']"),
                "Cannot find OOP");
    }

    @Test
    public void testCancelSearch() {
        waitForElementAndClick(By.id("org.wikipedia:id/search_container"),
                "Cannot find search input",
                5);

        waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5);

        waitForElementAndClear(By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                5);

        waitForElementAndClick(By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find cancel icon",
                5);

        waitForElementNotPresent(By.id("org.wikipedia:id/search_close_btn"),
                "X is still present",
                5);
    }

    @Test
    public void testCompareArticleTitle() {
        waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Wikipedia",
                5);

        waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5);

        waitForElementAndClick(By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_container']//*[@text = 'Object-oriented programming language']"),
                "Cannot find Wikipedia",
                5);

        WebElement title = waitForElementPresent(By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15);

        Assert.assertEquals("We see unexpected title",
                "Java (programming language)",
                title.getAttribute("text"));
    }

    @Test
    public void testSearchFieldPlaceholderText() {
        waitForElementAndClick(By.id("org.wikipedia:id/search_container"),
                "Cannot find search input",
                5);

        assertElementHasText(By.id("org.wikipedia:id/search_src_text"),
                "Search…",
                "Cannot find search field");
    }

    @Test
    public void testSearchAndCancel() {
        waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Wikipedia",
                5);

        waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5);

        List<WebElement> searchResults = waitForElementsPresent(By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_title'][contains(@text, 'Java')]"),
                "Результаты поиска отсутствуют",
                15);

        Assert.assertTrue("Количество статей меньше одной",
                searchResults.size() > 1);

        waitForElementAndClick(By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find cancel icon",
                5);

        waitForElementNotPresent(By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_container']//*[@text = 'Object-oriented programming language']"),
                "Есть результаты поиска",
                15);
    }

    @Test
    public void testSearchResultsContainsText() {
        String searchTerm = "Java";

        waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Wikipedia",
                5);

        waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"),
                searchTerm,
                "Cannot find search input",
                5);

        List<WebElement> searchResults = waitForElementsPresent(By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_title']"),
                "Результаты поиска отсутствуют",
                15);

        List<WebElement> expectedSearchResults = searchResults
                .stream()
                .filter(result -> result.getText().contains(searchTerm))
                .collect(Collectors.toList());

        Assert.assertTrue(String.format("Не все результаты содержат %s", searchTerm),
                searchResults.size() == expectedSearchResults.size());

    }

    private WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);

        return wait
                .withMessage(errorMessage)
                .until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresent(By by, String errorMessage) {
        WebDriverWait wait = new WebDriverWait(driver, 5);

        return wait
                .withMessage(errorMessage)
                .until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        return wait
                .withMessage(errorMessage + "\n")
                .until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private WebElement waitForElementAndClear(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.clear();
        return element;
    }

    private WebElement assertElementHasText(By by, String expectedText, String errorMessage) {
        WebElement element = waitForElementPresent(by, errorMessage, 5);
        Assert.assertEquals("Текст элемента отличается от ожидаемого",
                expectedText,
                element.getAttribute("text"));

        return element;
    }

    private List<WebElement> waitForElementsPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);

        return wait
                .withMessage(errorMessage)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

}
