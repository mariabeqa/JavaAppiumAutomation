package lib.ui;

import io.appium.java_client.AppiumDriver;

public class WelcomePageObject extends MainPageObject {

    private static final String
            STEP_LEARN_MORE_LINK = "xpath://*[@name='Learn more about Wikipedia']",
            STEP_NEW_WAYS_TO_EXPLORE_STEPS = "xpath://*[@name='New ways to explore']",
            STEP_SEARCH_IN_NEARLY_300_LANGUAGES = "xpath://*[@name='Search in nearly 300 languages']",
            STEP_LEARN_MORE_ABOUT_DATA_COLLECTED = "xpath://*[@name='Learn more about data collected']",
            NEXT_LINK = "xpath://*[@name='Next']",
            GET_STARTED_BUTTON = "xpath://*[@name='Get started']",
            SKIP_BUTTON = "xpath://XCUIElementTypeButton[@name='Skip']";

    public WelcomePageObject(AppiumDriver driver) {
        super(driver);
    }

    public void waitForLearnMoreLink() {
        this.waitForElementPresent(STEP_LEARN_MORE_LINK,
                "Cannot find 'Learn more about Wikipedia' link",
                10);
    }

    public void clickNextButton() {
        this.waitForElementAndClick(NEXT_LINK,
                "Cannot find 'Next' button",
                10);
    }

    public void waitForNewWayToExploreText() {
        this.waitForElementPresent(STEP_NEW_WAYS_TO_EXPLORE_STEPS,
                "Cannot find 'New ways to explore' text",
                10);
    }

    public void waitForLanguageText() {
        this.waitForElementPresent(STEP_SEARCH_IN_NEARLY_300_LANGUAGES,
                "Cannot find 'Search in nearly 300 languages' text",
                10);
    }

    public void waitForLearnMoreAboutDataCollectedLink() {
        this.waitForElementPresent(STEP_LEARN_MORE_ABOUT_DATA_COLLECTED,
                "Cannot find 'Learn more about data collected' link",
                10);
    }

    public void clickGetStartedButton() {
        this.waitForElementAndClick(GET_STARTED_BUTTON,
                "Cannot find 'Get started' button",
                10);
    }

    public void clickSkip() {
        this.waitForElementAndClick(SKIP_BUTTON,
                "Cannot find Skip button",
                10);
    }
}
