package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ArticleTests extends CoreTestCase {

    @Test
    public void testCompareArticleTitle() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.waitForTitleElement();
        String articleTitle = articlePageObject.getArticleTitle();

        assertEquals("We see unexpected title",
                "Java (programming language)",
                articleTitle);
    }

    @Test
    public void testSwipeArticleTitle() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Appium");
        searchPageObject.clickByArticleWithSubstring("Appium");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.waitForTitleElement();
        articlePageObject.swipeToFooter();
    }

    @Test
    public void testArticleTitleAfterDeletion() {
        String searchWord = "Selenium";
        String nameOfFolder = "Learning Selenium";
        String firstArticleTitle = "Selenium in biology";
        String secondArticleTitle = "Selenium (software)";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchWord);
        searchPageObject.clickByArticleWithSubstring(firstArticleTitle);

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.waitForTitleElement();
        articlePageObject.addArticleToMyList(nameOfFolder);
        articlePageObject.closeArticle();

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchWord);
        searchPageObject.clickByArticleWithSubstring(secondArticleTitle);
        articlePageObject.waitForTitleElement();
        articlePageObject.addArticleToAnExistingFolder(nameOfFolder);
        articlePageObject.closeArticle();

        NavigationUI navigationUI = new NavigationUI(driver);
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
        myListsPageObject.openFolderByName(nameOfFolder);
        myListsPageObject.swipeByArticleToDelete(firstArticleTitle);
        myListsPageObject.openArticleByTitle(secondArticleTitle);

        String actualTitle = articlePageObject.getArticleTitle();

        assertEquals(secondArticleTitle, actualTitle);
    }

    @Test
    public void testArticleTitle() {
        String searchWord = "Selenium";
        String articleTitle = "Selenium (software)";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchWord);
        searchPageObject.clickByArticleWithSubstring(articleTitle);

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        String actualTitle = articlePageObject.getArticleTitle();

        assertEquals("Actual title is different from expected title: " + articleTitle,
                articleTitle, actualTitle);
    }

}
