package App.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
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
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import io.appium.java_client.touch.ActionOptions;
import io.appium.java_client.touch.LongPressOptions;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;


public class AppiumLoad {
    public AppiumDriverLocalService service;
    public static AppiumDriver driver;
    public static  AndroidDriver driver2;
    String appPackage = "com.google.samples.apps.nowinandroid";
    UiAutomator2Options options = new UiAutomator2Options();
    BasePage basePage ;
  public   Home home;
    private  By app = (AppiumBy.xpath("//android.widget.TextView[contains(@text, 'Now in Android')]"));


    @Test
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
    @Test
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
            verticalSwipeByPercentages(0.8, 0.4, 0.5);
          //  driver.findElement(AppiumBy.xpath("//android.widget.TextView[contains(@text, 'Now in Android')]")).click();
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
    @Test

    public void closeAppnew(){
        driver2 = (AndroidDriver) driver;
        driver2.terminateApp(appPackage);
        System.out.println("why"+appPackage);
        clearAppCacheUsingADB(appPackage);

    }

    public void closeAppOld(){
        driver2 = (AndroidDriver) driver;
        driver2.closeApp();

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

}
