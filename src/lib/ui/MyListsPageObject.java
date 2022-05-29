package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListsPageObject extends MainPageObject {

    private static final String
        FOLDER_BY_NAME_TMP = "//*[@text='{FOLDER_NAME}']",
        ARTICLE_BY_TITLE_TMP = "//*[@text='{TITLE}']";

    public MyListsPageObject(AppiumDriver driver) {
        super(driver);
    }

    private static String getFolderXpathByName(String folderName) {
        return FOLDER_BY_NAME_TMP.replace("{FOLDER_NAME}", folderName);
    }

    private static String getSavedArticleXpathByName(String articleTitle) {
        return ARTICLE_BY_TITLE_TMP.replace("{TITLE}", articleTitle);
    }

    public void openFolderByName(String folderName) {
        String folderNameXpath = getFolderXpathByName(folderName);
        this.waitForElementAndClick(By.xpath(folderNameXpath),
                "Cannot find folder by " + folderName,
                5);
    }

    public void swipeByArticleToDelete(String articleTitle) {
        this.waitForArticleAppearByTitle(articleTitle);
        String articleTitleXpath = getSavedArticleXpathByName(articleTitle);

        this.swipeElementToLeft(By.xpath(articleTitleXpath),
                "Cannot find saved article");
        this.waitForArticleDisappearByTitle(articleTitle);
    }

    public void waitForArticleDisappearByTitle(String articleTitle) {
        String articleTitleXpath = getSavedArticleXpathByName(articleTitle);

        this.waitForElementNotPresent(By.xpath(articleTitleXpath),
                "Saved article still present by title " + articleTitle,
                15);
    }

    public void waitForArticleAppearByTitle(String articleTitle) {
        String articleTitleXpath = getSavedArticleXpathByName(articleTitle);

        this.waitForElementPresent(By.xpath(articleTitleXpath),
                "Cannot find saved title by title " + articleTitle,
                15);
    }

    public void openArticleByTitle(String articleTitle) {
        this.waitForArticleAppearByTitle(articleTitle);
        String articleTitleXpath = getSavedArticleXpathByName(articleTitle);

        this.waitForElementAndClick(By.xpath(articleTitleXpath),
                "Cannot find nav button to My lists",
                5);
    }

}
