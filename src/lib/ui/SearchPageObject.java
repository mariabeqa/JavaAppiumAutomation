package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject {

    private static final String
            SEARCH_INIT_ELEMENT = "//*[contains(@text, 'Search Wikipedia')]",
            SEARCH_INPUT = "//*[contains(@text, 'Search…')]",
            SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id = 'org.wikipedia:id/page_list_item_container']//*[@text = '{SUBSTRING}']",
            SEARCH_RESULT_ELEMENT = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
            SEARCH_EMPTY_RESULT_ELEMENT = "//*[@text='No results found']";


    /* TEMPLATES METHODS */
    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }
    /* TEMPLATES METHODS */

    private static String getSearchResultElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    public void initSearchInput() {
        this. waitForElementPresent(By.xpath(SEARCH_INIT_ELEMENT),
                "Cannot find search input after clicking search init element",
                5);

        this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT),
                "Cannot find and click search init element",
                5);
    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(By.id(SEARCH_CANCEL_BUTTON),
                "Cannot find search cancel button",
                5);
    }

    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(By.id(SEARCH_CANCEL_BUTTON),
                "Search cancel button is still present",
                5);
    }

    public void clickCancelSearch() {
        this.waitForElementAndClick(By.id(SEARCH_CANCEL_BUTTON),
                "Cannot find and click search cancel button",
                5);
    }

    public void typeSearchLine(String searchLine) {
        this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT),
                searchLine,
                "Cannot find and type into search input",
                5);
    }

    public void waitForSearchResult(String substring) {
        String searchResultXpath = getSearchResultElement(substring);
        this.waitForElementPresent(By.xpath(searchResultXpath),
                "Cannot find search result with substring " + substring,
                5);
    }

    public void clickByArticleWithSubstring(String substring) {
        String searchResultXpath = getSearchResultElement(substring);
        this.waitForElementAndClick(By.xpath(searchResultXpath),
                "Cannot find search result with substring " + substring,
                5);
    }

    public int getAmountOfArticles() {
        this.waitForElementPresent(By.xpath(SEARCH_RESULT_ELEMENT),
                "Cannot find anything by the request ",
                15);

        return this.getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT));
    }

    public void waitForEmptyResultLabel() {
        this.waitForElementPresent(By.xpath(SEARCH_EMPTY_RESULT_ELEMENT),
                "Cannot find empty result element",
                15);
    }

    public void assertThereIsNoSearchResults() {
        this.assertElementNotPresent(By.xpath(SEARCH_RESULT_ELEMENT),
                "There are some search results found");
    }

}
