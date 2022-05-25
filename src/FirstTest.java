import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class FirstTest extends CoreTestCase {

    private MainPageObject mainPageObject;

    protected void setUp() throws Exception {
        super.setUp();
        mainPageObject = new MainPageObject(driver);
    }

    @Test
    public void testSearchFieldPlaceholderText() {
        mainPageObject.waitForElementAndClick(By.id("org.wikipedia:id/search_container"),
                "Cannot find search input",
                5);

        mainPageObject.assertElementHasText(By.id("org.wikipedia:id/search_src_text"),
                "Search…",
                "Cannot find search field");
    }

    @Test
    public void testSearchAndCancel() {
        mainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Wikipedia",
                5);

        mainPageObject.waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5);

        List<WebElement> searchResults = mainPageObject.waitForElementsPresent(By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_title'][contains(@text, 'Java')]"),
                "Результаты поиска отсутствуют",
                15);

        assertTrue("Количество статей меньше одной",
                searchResults.size() > 1);

        mainPageObject.waitForElementAndClick(By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find cancel icon",
                5);

        mainPageObject.waitForElementNotPresent(By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_container']//*[@text = 'Object-oriented programming language']"),
                "Есть результаты поиска",
                15);
    }

    @Test
    public void testSearchResultsContainsText() {
        String searchTerm = "Java";

        mainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Wikipedia",
                5);

        mainPageObject.waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"),
                searchTerm,
                "Cannot find search input",
                5);

        List<WebElement> searchResults = mainPageObject.waitForElementsPresent(By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_title']"),
                "Результаты поиска отсутствуют",
                15);

        List<WebElement> expectedSearchResults = searchResults
                .stream()
                .filter(result -> result.getText().contains(searchTerm))
                .collect(Collectors.toList());

        assertTrue(String.format("Не все результаты содержат %s", searchTerm),
                searchResults.size() == expectedSearchResults.size());

    }

    @Test
    public void testArticleTitleAfterDeletion() {
        String searchWord = "Selenium";
        String nameOfFolder = "Learning Selenium";
        String firstArticleTitle = "Selenium in biology";
        String secondArticleTitle = "Selenium (software)";

        mainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Wikipedia",
                5);

        mainPageObject.waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"),
                searchWord,
                "Cannot find search input",
                5);

        mainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_container']//*[@text = '" + firstArticleTitle + "']"),
                "Cannot find Wikipedia",
                5);

        mainPageObject.waitForElementPresent(By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15);

        mainPageObject.waitForElementAndClick(By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options",
                5);

        mainPageObject.waitForElementAndClick(By.xpath("//*[@text = 'Add to reading list']"),
                "Cannot find option to add article to reading list",
                5);

        mainPageObject.waitForElementAndClick(By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                10);

        mainPageObject.waitForElementAndClear(By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of articles folder",
                5);

        mainPageObject.waitForElementAndSendKeys(By.id("org.wikipedia:id/text_input"),
                nameOfFolder,
                "Cannot put text into articles folder input",
                5);

        mainPageObject.waitForElementAndClick(By.xpath("//*[@text='OK']"),
                "Cannot press OK button",
                5);

        mainPageObject.waitForElementAndClick(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5);

        mainPageObject.waitForElementAndClick(By.xpath("//*[contains(@content-desc, 'Search Wikipedia')]"),
                "Cannot find search input",
                10);

        mainPageObject.waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"),
                searchWord,
                "Cannot find search input",
                5);

        mainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_container']//*[@text = '" + secondArticleTitle + "']"),
                "Cannot find Wikipedia",
                10);

        mainPageObject.waitForElementPresent(By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15);

        mainPageObject.waitForElementAndClick(By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options",
                5);

        mainPageObject.waitForElementAndClick(By.xpath("//*[@text = 'Add to reading list']"),
                "Cannot find option to add article to reading list",
                5);

        mainPageObject.waitForElementAndClick(By.xpath("//*[@text = '" + nameOfFolder + "']"),
                "Cannot select folder named " + nameOfFolder,
                5);

        mainPageObject.waitForElementAndClick(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot find Back button",
                5);

        mainPageObject.waitForElementAndClick(By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find nav button to My lists",
                5);

        mainPageObject.waitForElementAndClick(By.xpath("//*[@text = '" + nameOfFolder + "']"),
                "Cannot select folder named " + nameOfFolder,
                5);

        mainPageObject.swipeElementToLeft(By.xpath("//*[@text='" + firstArticleTitle + "']"),
                "Cannot find saved article with title '" + firstArticleTitle + "'");

        mainPageObject.waitForElementNotPresent(By.xpath("//*[@text='" + firstArticleTitle + "']"),
                "Article with title '" + firstArticleTitle + "' is present",
                5);

        mainPageObject.waitForElementPresent(By.xpath("//*[@text='" + secondArticleTitle + "']"),
                "Article with title '" + secondArticleTitle + "' is not present",
                5);

        mainPageObject.waitForElementAndClick(By.xpath("//*[@text='" + secondArticleTitle + "']"),
                "Cannot find nav button to My lists",
                5);

        WebElement actualTitle = mainPageObject.waitForElementPresent(By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15);

        assertEquals(secondArticleTitle, actualTitle.getText());
    }

    @Test
    public void testArticleTitle() {
        String searchWord = "Selenium";
        String articleTitle = "Selenium (software)";

        mainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Wikipedia",
                5);

        mainPageObject.waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"),
                searchWord,
                "Cannot find search input",
                5);

        mainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_container']//*[@text = '" + articleTitle + "']"),
                "Cannot find Wikipedia",
                5);

        mainPageObject.assertElementPresent(By.id("org.wikipedia:id/view_page_title_text"),
                "Title of the article is not present.");
    }

}
