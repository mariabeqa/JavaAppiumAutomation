package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.time.Duration;

public class CoreTestCase extends TestCase {
    private static final String PLATFORM_IOS = "ios";
    private static final String PLATFORM_ANDROID = "android";

    protected AppiumDriver driver;
    private static String APPIUM_URL = "http://127.0.0.1:4723/wd/hub";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        DesiredCapabilities capabilities = this.getsCapsByPlatformEnv();

        driver = getDriverByPlatformEnv(capabilities);
    }

    @Override
    protected void tearDown() {
        driver.quit();
    }

    protected void rotateScreenPortrait() {
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    protected void rotateScreenLandscape() {
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    protected void backgroundUp(int seconds) {
        driver.runAppInBackground(Duration.ofSeconds(seconds));
    }

    private DesiredCapabilities getsCapsByPlatformEnv() throws Exception {
        String platform = System.getenv("PLATFORM");
        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (platform.equals(PLATFORM_ANDROID)) {
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("deviceName", "emulator-5554");
            capabilities.setCapability("platformVersion", "8.0");
            capabilities.setCapability("automationName", "Appium");
            capabilities.setCapability("appPackage", "org.wikipedia");
            capabilities.setCapability("appActivity", ".main.MainActivity");
            capabilities.setCapability("app", "/Users/mariabelyaeva/Desktop/JavaAppiumAutomation/apks/org.wikipedia.apk");
        } else if (platform.equals(PLATFORM_IOS)) {
            capabilities.setCapability("platformName", "iOS");
            capabilities.setCapability("deviceName", "iPhone 8");
            capabilities.setCapability("platformVersion", "13.5");
            capabilities.setCapability("app", "/Users/mariabelyaeva/Documents/mobile_auto_course/Wikipedia.app");
        } else {
            throw new Exception("Cannot get run platform from env variable. Platform value " + platform);
        }

        return capabilities;
    }

    private AppiumDriver getDriverByPlatformEnv(DesiredCapabilities caps) throws Exception {
        String platform = System.getenv("PLATFORM");

        if (platform.equals(PLATFORM_ANDROID)) {

            return new AndroidDriver(new URL(APPIUM_URL), caps);

        } else if (platform.equals(PLATFORM_IOS)) {

            return new IOSDriver(new URL(APPIUM_URL), caps);

        } else {
            throw new Exception("Cannot get run platform from env variable. Platform value " + platform);
        }
    }

}
