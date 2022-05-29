package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchTests extends CoreTestCase {

    @Test
    public void testSearch() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        String searchLine = "Linkin Park Discography";
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);

        int amountOfSearchResults = searchPageObject.getAmountOfArticles();

        assertTrue("We found 0 results",
                amountOfSearchResults > 0);
    }

    @Test
    public void testAmountOfEmptySearch() {
        String searchLine = "jkjnkjnkjnkj";
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.waitForEmptyResultLabel();
        searchPageObject.assertThereIsNoSearchResults();
    }

    @Test
    public void testSearchFieldPlaceholderText() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.assertThereIsSearchPlaceholder();
    }

    @Test
    public void testSearchAndCancel() {
        String searchTerm = "Java";
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchTerm);
        int amountOfSearchResults = searchPageObject.getAmountOfArticles();

        assertTrue("There are no articles found in search results",
                amountOfSearchResults > 1);

        searchPageObject.clickCancelSearch();
        searchPageObject.assertThereIsNoSearchResults();
    }

    @Test
    public void testSearchResultsContainsText() {
        String searchTerm = "Java";
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchTerm);

        int amountOfSearchResults = searchPageObject.getAmountOfArticles();
        List<WebElement> amountOfArticlesWithSearchTerm = searchPageObject.getArticlesThatContains(searchTerm);

        assertTrue(String.format("Не все результаты содержат %s", searchTerm),
                amountOfSearchResults == amountOfArticlesWithSearchTerm.size());
    }

}
