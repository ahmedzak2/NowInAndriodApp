package App.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import App.pages.BasePage;
import App.pages.Home;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;
import io.appium.java_client.android.nativekey.KeyEvent;





public class AppiumLoad {
    public AppiumDriverLocalService service;
    public static AppiumDriver driver;
    public static  AndroidDriver androidDriver;
    String  appPackage = "com.google.samples.apps.nowinandroid";
    UiAutomator2Options options = new UiAutomator2Options();

    BasePage basePage ;
  public   Home home;
    private final By  app = (AppiumBy.xpath("//android.widget.TextView[contains(@text, 'Now in Android')]"));


    public AppiumDriverLocalService startAppiumServer(String ipAddress, int port) {
        Map<String, String> environment = new HashMap<>();
        environment.put("NO_RESET", "true");

        service = new AppiumServiceBuilder()
                .withAppiumJS(new File("C:\\Users\\20112\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
                .withIPAddress(ipAddress)
                .usingPort(port)
                .withEnvironment(environment)
                .build();
        service.start();
        return service;
    }
    @BeforeSuite
    public void setup() throws IOException, InterruptedException {
    startAppiumServer("127.0.0.1",4723);
        if (driver == null) {

            Properties properties = new Properties();

            FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\data.properties");
            properties.load(file);
            options.setDeviceName("sdk_gphone64_x86_64");
            options.setPlatformVersion("14");
            options.setChromedriverExecutable("D:\\TESTING\\appium\\AppiumFrameworkDesign\\Frame\\src\\test\\resources\\chromedriver.exe");
          // options.setApp("D:\\TESTING\\appium\\AppiumFrameworkDesign\\Frame\\src\\main\\Application\\ApiDemos-debug.apk");
            driver = new AndroidDriver(new URL(properties.getProperty("url2")), options);
            basePage= new BasePage(driver);
            driver.manage().timeouts().implicitlyWait(ofSeconds(10));
          //  driver.findElement(AppiumBy.xpath("//android.widget.TextView[contains(@text, 'Now in Android')]")).click();
             androidDriver = (AndroidDriver) driver;

            androidDriver.pressKey(new KeyEvent(AndroidKey.HOME));
            verticalSwipeByPercentages(0.8, 0.4, 0.5);

            basePage.openApp();
            home = basePage.openHome();
            Thread.sleep(4000);
        //     appPackage = String.valueOf(options.getAppPackage());
            System.out.println("App Package: " + appPackage);
            home.acceptNotification();
        }
}
    //Press by coordinates
   // to open the menu of the app 
    public void verticalSwipeByPercentages(double startPercentage, double endPercentage, double anchorPercentage) {
        Dimension size = driver.manage().window().getSize();
        int anchor = (int) (size.width * anchorPercentage);
        int startPoint = (int) (size.height * startPercentage);
        int endPoint = (int) (size.height * endPercentage);
        new TouchAction((PerformsTouchActions) driver)
                .press(point(anchor, startPoint))
                .waitAction(waitOptions(ofMillis(1000)))
                .moveTo(point(anchor, endPoint))
                .release().perform();
    }
    @AfterTest
    public void closeAppnew() throws InterruptedException {
        androidDriver = (AndroidDriver) driver;
        System.out.println("why" + appPackage);

        long startTime = System.currentTimeMillis();
        long timeoutInSeconds = 20;
        long endTime = startTime + (timeoutInSeconds * 1000);

        while (System.currentTimeMillis() < endTime) {
            try {
                androidDriver.terminateApp(appPackage);
                System.out.println("App terminated successfully.");
                return; // Exit the loop if termination is successful
            } catch (Exception e) {
                // Handle the exception or log it if needed
                e.printStackTrace();
            }

        }
        clearAppCacheUsingADB(appPackage);
    }

        public void closeAppOld(){
        androidDriver = (AndroidDriver) driver;
        androidDriver.closeApp();

clearAppCacheUsingADB(appPackage);
    }
    private static void clearAppCacheUsingADB(String appPackage) {
        String adbCommand = String.format("adb shell pm clear %s", appPackage);
        System.out.println(appPackage);
        try {
            Process process = Runtime.getRuntime().exec(adbCommand);
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("App cache cleared successfully.");
            } else {
                System.out.println("Failed to clear app cache.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void scrollToEndAction() {
        boolean canScrollMore;
        do {
            ImmutableMap<String, Object> scrollParams = ImmutableMap.<String, Object>builder()
                    .put("left", 100)
                    .put("top", 100)
                    .put("width", 200)
                    .put("height", 200)
                    .put("direction", "down")
                    .put("percent", 0.3)
                    .build();

            canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", scrollParams);
        } while (canScrollMore);
    }

    public void swipeAction(WebElement ele, String direction) {
        ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) ele).getId(),

                "direction", direction,
                "percent", 0.75
        ));


    }


    /*
     * to scroll to specific element
     *
     * */
    public void getitemformTopicSelection(String element) throws InterruptedException {

        /*
         * beascue we have more than one element have same i use for loop git the text and index of
         * element to compare it with the required text if match get the index to pass it to click on it
         * */
        int productCount = (driver.findElements(home.getTopicSeection())).size();
        for (int i = 0; i < productCount; i++) {

            String product = driver.findElements(home.getTopicSeection()).get(i).getText();
            if (product.equals(element)) {
                driver.findElements(home.getTopicSeection()).get(i).click();
            }

        }
        Thread.sleep(2000);

    }


}
