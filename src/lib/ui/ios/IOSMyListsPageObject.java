package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;

public class IOSMyListsPageObject extends MyListsPageObject {

    static {
        ARTICLE_BY_TITLE_TMP = "xpath://XCUIElementTypeStaticText[contains(@name, '{TITLE}')]";
        CLOSE_LOGIN_POPUP = "id:Close";
    }

    public IOSMyListsPageObject(AppiumDriver driver) {
        super(driver);
    }
}
