package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class ArticleTests extends CoreTestCase {

    @Test
    public void testCompareArticleTitle() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();
        String articleTitle = articlePageObject.getArticleTitle();

        assertEquals("We see unexpected title",
                "Java (programming language)",
                articleTitle);
    }

    @Test
    public void testSwipeArticleTitle() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Java (programming language)");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();
        articlePageObject.swipeToFooter();
    }

    @Test
    public void testArticleTitleAfterDeletion() {
        String searchWord = "Selenium";
        String nameOfFolder = "Learning Selenium";
        String firstArticleTitle = "Selenium in biology";
        String secondArticleTitle = "Selenium (software)";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchWord);
        searchPageObject.clickByArticleWithSubstring(firstArticleTitle);

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();
        articlePageObject.addArticleToMyList(nameOfFolder);
        articlePageObject.closeArticle();

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchWord);
        searchPageObject.clickByArticleWithSubstring(secondArticleTitle);
        articlePageObject.waitForTitleElement();
        articlePageObject.addArticleToAnExistingFolder(nameOfFolder);
        articlePageObject.closeArticle();

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
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

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchWord);
        searchPageObject.clickByArticleWithSubstring(articleTitle);

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        String actualTitle = articlePageObject.getArticleTitle();

        assertEquals("Actual title is different from expected title: " + articleTitle,
                articleTitle, actualTitle);
    }

}
